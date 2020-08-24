package com.ivaylorusev.xmlFoTemplateBuilder.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.ivaylorusev.xmlFoTemplateBuilder.ResourceService;
import com.ivaylorusev.xmlFoTemplateBuilder.models.MailTypeVariant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.*;

@Service
public class AttachmentsYamlService {

    @Autowired
    private ResourceService resourceService;

    public YamlConfiguration getYamlConfiguration(YamlControlProperties yamlControlProperties) throws Exception {
        HashMap<String, Object> templateFlow = getYamlMap(yamlControlProperties, "templateFlow");

        HashMap<String, Object> templateFlowComponents = getYamlMaps(yamlControlProperties, "templateFlowComponents");
        HashMap<String, Object> templateContentKeys = getYamlMap(yamlControlProperties, "templateContentKeys");
        insertContentKeys(templateFlowComponents, templateContentKeys);
        insertContentKeys(templateFlow, templateContentKeys);
        insertFlowComponents(templateFlow, templateFlowComponents);


        YamlConfiguration yamlConfiguration = new YamlConfiguration();
        yamlConfiguration.setTemplateLayout(getYamlMap(yamlControlProperties, "templateLayout"));
        yamlConfiguration.setTemplateFlow(templateFlow);

        return yamlConfiguration;
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
        HashMap<String, Object> yamlMaps = (HashMap<String, Object>) getTemplateConfigHashMap().get(yamlMapName);
        for (Map.Entry<String, Object> entry: yamlMaps.entrySet()) {
            yamlMaps.put(entry.getKey(), processYamlMap(yamlControlProperties, (HashMap) entry.getValue()));
        }
        return yamlMaps;
    }

    private HashMap getYamlMap(YamlControlProperties yamlControlProperties, String yamlMapName) throws Exception {
        HashMap<String, Object> yamlMap = (HashMap<String, Object>) getTemplateConfigHashMap().get(yamlMapName);
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

            if (fieldValue instanceof HashMap) { //embedded object in yamlControlProperties
                if (yamlMap.keySet().contains(yamlFieldName)) {
                    HashMap<String, Object> valueByFieldName = (HashMap) yamlMap.get(yamlFieldName);
                    filterYamlMap((HashMap<String, Object>) fieldValue, valueByFieldName);
                }
            }
            else {
                if (yamlMap.keySet().contains(yamlFieldName)) {
                    HashMap<String, Object> valueByFieldName = (HashMap) yamlMap.get(yamlFieldName);
                    String yamlValueName = getYamlValueName((String) fieldValue);
                    removeKeyValueReverse(yamlValueName, valueByFieldName);

                    Object byFieldValue = valueByFieldName.get(yamlValueName);
                    if ((byFieldValue instanceof HashMap)) {
                        filterYamlMap(yamlControlPropertiesMap, (HashMap<String, Object>) byFieldValue);
                    }
                    else if (byFieldValue == null) {
                        yamlMap.remove(yamlFieldName); //config map filtered by field and value is empty, so remove it
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

    private String getYamlValueName(String valueName) {
        return "value[" + valueName + "]";
    }

    private void removeKeyValueReverse(String key, HashMap<String, Object> testMap) {
        Iterator<Map.Entry<String,Object>> iter = testMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String,Object> entry = iter.next();
            if(!key.equalsIgnoreCase(entry.getKey())){
                iter.remove();
            }
        }
    }

    private void insertContentKeys(HashMap<String, Object> templateFlowComponents, HashMap<String, Object> templateContentKeys) {
        for (Map.Entry<String, Object> entry : templateFlowComponents.entrySet()) {
            String fieldName = entry.getKey();
            Object fieldValue = entry.getValue();
            if (fieldValue instanceof HashMap) {
                insertContentKeys((HashMap) fieldValue, templateContentKeys);
            }
            else if (fieldValue instanceof Collection) {
                Collection c = (Collection) fieldValue;
                for (Object o : c) {
                    insertContentKeys((HashMap) o, templateContentKeys);
                }
            }
            else if (fieldName.equalsIgnoreCase("contentKey")){
                templateFlowComponents.put(fieldName, templateContentKeys.get(fieldValue));
            }

        }
    }


    private void insertFlowComponents(HashMap<String, Object> templateFlow, HashMap<String, Object> templateFlowComponents) {
        for (Map.Entry<String, Object> entry : templateFlow.entrySet()) {
            String fieldName = entry.getKey();
            Object fieldValue = entry.getValue();
            if (fieldValue instanceof HashMap) {
                insertFlowComponents((HashMap) fieldValue, templateFlowComponents);
            }
            else if (fieldValue instanceof Collection) {
                Collection c = (Collection) fieldValue;
                for (Object o : c) {
                    insertFlowComponents((HashMap) o, templateFlowComponents);
                }
            }
            else if (fieldName.equalsIgnoreCase("flowComponents")){
                templateFlow.put(fieldName, templateFlowComponents.get(fieldValue));
            }

        }
    }




}
