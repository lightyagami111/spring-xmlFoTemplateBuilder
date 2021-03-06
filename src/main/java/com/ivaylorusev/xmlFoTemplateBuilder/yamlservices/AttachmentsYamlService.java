package com.ivaylorusev.xmlFoTemplateBuilder.yamlservices;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.ivaylorusev.xmlFoTemplateBuilder.ResourceService;
import com.ivaylorusev.xmlFoTemplateBuilder.models.InvoiceVoucherData;
import com.ivaylorusev.xmlFoTemplateBuilder.models.MailTypeVariant;
import com.ivaylorusev.xmlFoTemplateBuilder.models.MasterRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.*;

@Service
public class AttachmentsYamlService {

    private final ResourceService resourceService;
    private HashMap templateConfigHashMap;

    public AttachmentsYamlService(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    public YamlConfiguration getYamlConfiguration(MasterRequest masterRequest) throws Exception {
        YamlControlProperties yamlControlProperties = getYamlControlProperties(masterRequest);
        templateConfigHashMap = getTemplateConfigHashMap();

        HashMap<String, Object> templateFlow = getYamlMap(yamlControlProperties, "templateFlow");

        HashMap<String, Object> templateFlowComponents = getYamlMaps(yamlControlProperties, "templateFlowComponents");
        HashMap<String, Object> templateContentKeys = getYamlMap(yamlControlProperties, "templateContentKeys");

        insertContentKeys(templateFlowComponents, templateContentKeys);
        insertContentKeys(templateFlow, templateContentKeys);


        insertFlowComponents(templateFlowComponents, templateFlowComponents);
        insertFlowComponents(templateFlow, templateFlowComponents);


        YamlConfiguration yamlConfiguration = new YamlConfiguration();
        yamlConfiguration.setTemplateLayout(getYamlMap(yamlControlProperties, "templateLayout"));
        yamlConfiguration.setTemplateFlow(templateFlow);

        return yamlConfiguration;
    }

    private YamlControlProperties getYamlControlProperties(MasterRequest masterRequest) {
        InvoiceVoucherData invoiceVoucherData;
        Optional<String> voucherReferenceNumber = Optional.ofNullable(masterRequest.getVoucherReferenceNumber());
        if (voucherReferenceNumber.isPresent() &&
                StringUtils.hasText(voucherReferenceNumber.get())) {
            invoiceVoucherData = InvoiceVoucherData.HAVE_VOUCHER_DATA;
        }
        else {
            invoiceVoucherData = InvoiceVoucherData.NO_VOUCHER_DATA;
        }

        return new YamlControlProperties(
                masterRequest.getBrand(),
                masterRequest.getMailTypeVariant(),
                masterRequest.getCustomerInformation().getSalutation(),
                masterRequest.getCustomerInformation().getCustomerType(),
                masterRequest.getPaymentData().getPaymentType(),
                invoiceVoucherData
        );
    }

    private HashMap getTemplateConfigHashMap() throws Exception {
        InputStream templateConfig = resourceService.getTemplateConfig();
        Yaml yaml = new Yaml();
        HashMap templateConfigHashMap = yaml.load(templateConfig);

        return templateConfigHashMap;
    }

    private HashMap<String, Object> getYamlControlPropertiesAsMap(YamlControlProperties yamlControlProperties) {
        ObjectMapper objectMapper = new ObjectMapper();

        SimpleModule module = new SimpleModule();
        module.addSerializer(new MailTypeVariantSerializer(MailTypeVariant.class));
        objectMapper.registerModule(module);

        HashMap<String, Object> yamlControlPropertiesMap = objectMapper.convertValue(yamlControlProperties, HashMap.class);
        return yamlControlPropertiesMap;
    }



    private HashMap getYamlMaps(YamlControlProperties yamlControlProperties, String yamlMapName) throws Exception {
        HashMap<String, Object> yamlMaps = (HashMap<String, Object>) templateConfigHashMap.get(yamlMapName);
        yamlMaps.replaceAll((k, v) ->
                processYamlMap(yamlControlProperties, (HashMap) v)
        );
        return yamlMaps;
    }

    private HashMap getYamlMap(YamlControlProperties yamlControlProperties, String yamlMapName) throws Exception {
        HashMap<String, Object> yamlMap = (HashMap<String, Object>) templateConfigHashMap.get(yamlMapName);
        return processYamlMap(yamlControlProperties, yamlMap);
    }

    private HashMap processYamlMap(YamlControlProperties yamlControlProperties, HashMap<String, Object> yamlMap) {
        HashMap<String, Object> yamlControlPropertiesMap = getYamlControlPropertiesAsMap(yamlControlProperties);
        filterYamlMap(yamlControlPropertiesMap, yamlMap);
        HashMap<String, Object> output = new HashMap<>();
        flatYamlMap(yamlMap, output);

        return output;
    }


    /**
     *
     * @param yamlControlPropertiesMap
     *      example : {brand=...., mailTypeVariant=...., customerInf={salutation=....}}
     * @param yamlMap example :
     *  field[brand]:
     *
     *     value[VW]:
     *      root : .....
     *      field[mailTypeVariant]:
     *       value[activation]:
     *          root: .....
     *     value[SKODA]:
     *      root : .....
     *
     * @return filtered yamlMap by yamlControlProperties
     */
    private void filterYamlMap(HashMap<String, Object> yamlControlPropertiesMap, HashMap<String, Object> yamlMap) {
        for (Map.Entry<String, Object> entry : yamlControlPropertiesMap.entrySet()) {
            String yamlFieldName = getYamlFieldName(entry.getKey());
            Object fieldValue = entry.getValue();

            if (fieldValue == null) {
                yamlMap.remove(yamlFieldName);
                return;
            }
            if (fieldValue instanceof HashMap) { //embedded object in yamlControlProperties
                if (yamlMap.keySet().contains(yamlFieldName)) {
                    HashMap<String, Object> valueByFieldName = (HashMap) yamlMap.get(yamlFieldName);
                    filterYamlMap((HashMap<String, Object>) fieldValue, valueByFieldName);
                }
            }
            else {
                if (yamlMap.keySet().contains(yamlFieldName)) {
                    HashMap<String, Object> valueByFieldName = (HashMap) yamlMap.get(yamlFieldName);
                    removeKeyValueReverse((String) fieldValue, valueByFieldName);

                    Object byFieldValues = get(valueByFieldName, fieldValue);
                    if ((byFieldValues instanceof Collection)) {
                        for (Object byFieldValue : (Collection) byFieldValues) {
                            if (byFieldValue instanceof HashMap) {
                                filterYamlMap(yamlControlPropertiesMap, (HashMap<String, Object>) byFieldValue);
                            }
                        }
                    }
                    else if (byFieldValues == null) {
                        yamlMap.remove(yamlFieldName);
                    }

                }
            }
        }
    }

    /**
     * remove field[]/value[] mapping
     * @param filteredYamlMap example :
     * templateContentKeys:
     *   field[customerInformation]:
     *     field[salutation]:
     *       value[MR]:
     *         salutation: customerSalutation-MR
     * @param output example :
     *  templateContentKeys:
     *    salutation: customerSalutation-MR
     */
    private void flatYamlMap(HashMap<String, Object> filteredYamlMap, HashMap<String, Object> output) {
        for (Map.Entry<String, Object> entry : filteredYamlMap.entrySet()) {
            String fieldName = entry.getKey();
            Object fieldValue = entry.getValue();

            if (fieldName.startsWith("field[") || fieldName.startsWith("value[")) {
                flatYamlMap((HashMap<String, Object>) fieldValue, output);
            }
            else {
                output.put(fieldName, fieldValue);
            }
        }
    }

    private String getYamlFieldName(String fieldName) {
        return "field[" + fieldName + "]";
    }

    public static <K,V> List<V> get(HashMap<K,V> linkedHashMap, Object key) {
        List<V> result = new ArrayList<>();
        Set<K> keySet = linkedHashMap.keySet();
        for (K k : keySet) {
            if (k.toString().contains(key.toString())) {
                result.add(linkedHashMap.get(k));
            }
        }
        if (result.isEmpty())
            return null;
        else
            return result;
    }

    private void removeKeyValueReverse(String key, HashMap<String, Object> yamlMap) {
        Iterator<Map.Entry<String,Object>> iter = yamlMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String,Object> entry = iter.next();
            if(!entry.getKey().contains(key)){
                iter.remove();
            }
        }
    }

    private void insertContentKeys(HashMap<String, Object> templateFlowComponents, HashMap<String, Object> templateContentKeys) {
        for (Map.Entry<String, Object> entry : templateFlowComponents.entrySet()) {
            String fieldName = entry.getKey();
            Object fieldValue = entry.getValue();

            if (fieldName.equalsIgnoreCase("contentKey")){
                if (fieldValue instanceof List) {
                    List c = (List) fieldValue;
                    for (int i=0; i < c.size(); i++) {
                        Object contentKey = templateContentKeys.get(c.get(i));
                        if (contentKey != null)
                            c.set(i, contentKey);
                    }
                }
                else {
                    templateFlowComponents.put(fieldName, templateContentKeys.get(fieldValue));
                }
            }

            if (fieldValue instanceof HashMap) {
                insertContentKeys((HashMap) fieldValue, templateContentKeys);
            }
            else if (fieldValue instanceof Collection) {
                Collection c = (Collection) fieldValue;
                for (Object o : c) {
                    if (o instanceof HashMap) {
                        insertContentKeys((HashMap) o, templateContentKeys);
                    }
                }
            }

        }
    }


    private void insertFlowComponents(HashMap<String, Object> templateFlow, HashMap<String, Object> templateFlowComponents) {
        Iterator<Map.Entry<String,Object>> iter = templateFlow.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String,Object> entry = iter.next();

            String fieldName = entry.getKey();
            Object fieldValue = entry.getValue();

            if (fieldName.equalsIgnoreCase("flowComponents")){
                if (fieldValue instanceof List) {
                    List listComponents = (List) fieldValue;
                    Iterator iterListComponents = listComponents.iterator();
                    while (iterListComponents.hasNext()) {
                        putOrRemove(iterListComponents.next(), iterListComponents, listComponents, templateFlowComponents);
                    }
                }

                else {
                    putOrRemove(entry, iter, templateFlow, templateFlowComponents);
                }
            }

            else {
                if (fieldValue instanceof HashMap) {
                    insertFlowComponents((HashMap) fieldValue, templateFlowComponents);
                    removeIfEmpty(fieldValue, iter);
                }
                else if (fieldValue instanceof List) {
                    List listComponents = (List) fieldValue;
                    Iterator iterListComponents = listComponents.iterator();
                    while (iterListComponents.hasNext()) {
                        Object o = iterListComponents.next();
                        removeIfEmpty(o, iterListComponents);
                        if (o instanceof HashMap) {
                            insertFlowComponents((HashMap) o, templateFlowComponents);
                            removeIfEmpty(o, iterListComponents);
                        }
                    }
                    removeIfEmpty(fieldValue, iter);
                }
            }


        }

    }

    private void removeIfEmpty(Object fieldValue, Iterator iter) {
        if (isEmpty(fieldValue)) {
            iter.remove();
        }
    }


    private void putOrRemove(Object fieldValue, Iterator iter, List listComponents, HashMap<String, Object> templateFlowComponents) {
        Object component = templateFlowComponents.get(fieldValue);

        if (isEmpty(component)) {
            iter.remove();
        }
        else {
            int i = listComponents.indexOf(fieldValue);
            listComponents.set(i, component);
        }


    }

    private void putOrRemove(Map.Entry<String,Object> entry, Iterator iter, HashMap<String, Object> templateFlow, HashMap<String, Object> templateFlowComponents) {
        String fieldName = entry.getKey();
        Object fieldValue = entry.getValue();

        Object component = templateFlowComponents.get(fieldValue);
        if (isEmpty(component)) {
            iter.remove();
        }
        else {
            templateFlow.put(fieldName, component);
        }

    }

    private boolean isEmpty(Object component) {
        boolean b = false;

        if (component == null)
            b = true;
        if (component instanceof Collection)
            if (((Collection) component).size() == 0)
                b = true;
        if (component instanceof HashMap)
            if (((HashMap) component).size() == 0)
                b = true;

        return b;
    }




}
