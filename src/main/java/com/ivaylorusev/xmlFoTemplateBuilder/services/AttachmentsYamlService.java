package com.ivaylorusev.xmlFoTemplateBuilder.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.ivaylorusev.xmlFoTemplateBuilder.models.MailTypeVariant;
import com.ivaylorusev.xmlFoTemplateBuilder.models.MasterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Service
public class AttachmentsYamlService {

    @Autowired
    private ResourceService resourceService;

    public YamlConfiguration getYamlConfiguration(MasterRequest masterRequest) throws Exception {
        YamlConfiguration yamlConfiguration = new YamlConfiguration();

        yamlConfiguration.setTemplateContentKeys(getYamlMapWithParentComponentName(masterRequest, "templateContentKeys"));
        yamlConfiguration.setTemplateLayout(getYamlMap(masterRequest, "templateLayout"));

        yamlConfiguration.setTemplateFlow(getYamlMap(masterRequest, "templateFlow"));

        HashMap<String, Object> templateFlowComponents = (HashMap<String, Object>) getTemplateConfigHashMap().get("templateFlowComponents");
        for (Map.Entry<String, Object> entry: templateFlowComponents.entrySet()) {

        }


        return yamlConfiguration;
    }

    private HashMap getTemplateConfigHashMap() throws Exception {
        InputStream templateConfig = resourceService.getTemplateConfig();
        Yaml yaml = new Yaml();
        HashMap templateConfigHashMap = yaml.load(templateConfig);

        return templateConfigHashMap;
    }

    private HashMap<String, Object> getMasterRequestHashMap(MasterRequest request) {
        ObjectMapper objectMapper = new ObjectMapper();

        SimpleModule module = new SimpleModule();
        module.addSerializer(new MailTypeVariantSerializer(MailTypeVariant.class));
        objectMapper.registerModule(module);

        HashMap<String, Object> masterRequestHashMap = objectMapper.convertValue(request, HashMap.class);
        return masterRequestHashMap;
    }



    private HashMap getYamlMapWithParentComponentName(MasterRequest masterRequest, String yamlComponent) throws Exception {
        HashMap<String, Object> outputChild = getYamlMap(masterRequest, yamlComponent);

        HashMap<String, Object> outputParent = new HashMap<>();
        outputParent.put(yamlComponent, outputChild);

        return outputParent;
    }

    private HashMap getYamlMap(MasterRequest masterRequest, String yamlComponent) throws Exception {
        HashMap<String, Object> templateContentKeysYaml = (HashMap<String, Object>) getTemplateConfigHashMap().get(yamlComponent);
        HashMap<String, Object> masterRequestHashMap = getMasterRequestHashMap(masterRequest);
        filterConfigMap(masterRequestHashMap, templateContentKeysYaml);
        HashMap<String, Object> output = new HashMap<>();
        flatteringMap(templateContentKeysYaml, output);

        return output;
    }


    /**
     *
     * @param masterRequestHashMap
     *      example : {brand=...., mailTypeVariant=...., customerInf={salutation=....}}
     * @param configHashMap example :
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
     * @return filtered configHashMap by masterRequest
     */
    private void filterConfigMap(HashMap<String, Object> masterRequestHashMap, HashMap<String, Object> configHashMap) {
        for (Map.Entry<String, Object> entry : masterRequestHashMap.entrySet()) {
            String yamlFieldName = getYamlFieldName(entry.getKey());
            Object fieldValue = entry.getValue();

            if (fieldValue instanceof HashMap) { //embedded object in masterRequest
                if (configHashMap.keySet().contains(yamlFieldName)) {
                    HashMap<String, Object> valueByFieldName = (HashMap) configHashMap.get(yamlFieldName);
                    filterConfigMap((HashMap<String, Object>) fieldValue, valueByFieldName);
                }
            }
            else {
                if (configHashMap.keySet().contains(yamlFieldName)) {
                    HashMap<String, Object> valueByFieldName = (HashMap) configHashMap.get(yamlFieldName);
                    String yamlValueName = getYamlValueName((String) fieldValue);
                    removeKeyValueReverse(yamlValueName, valueByFieldName);

                    Object byFieldValue = valueByFieldName.get(yamlValueName);
                    if ((byFieldValue instanceof HashMap)) {
                        filterConfigMap(masterRequestHashMap, (HashMap<String, Object>) byFieldValue);
                    }
                    else if (byFieldValue == null) { //config map filtered by field and value is empty, so remove it
                        configHashMap.remove(yamlFieldName);
                    }

                }
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

    private void flatteringMap(HashMap<String, Object> filteredConfigHashMap, HashMap<String, Object> output) {
        for (Map.Entry<String, Object> entry : filteredConfigHashMap.entrySet()) {
            String fieldName = entry.getKey();
            Object fieldValue = entry.getValue();

            if (fieldName.startsWith("field[") || fieldName.startsWith("value[")) {
                flatteringMap((HashMap<String, Object>) fieldValue, output);
            }
            else {
                output.put(fieldName, fieldValue);
            }
        }
    }

}
