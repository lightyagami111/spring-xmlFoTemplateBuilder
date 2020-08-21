package com.ivaylorusev.xmlFoTemplateBuilder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.ivaylorusev.xmlFoTemplateBuilder.models.MasterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class AttachmentsYamlService {

    @Autowired
    private ResourceService resourceService;

    public YamlConfiguration getYamlConfiguration(MasterRequest masterRequest) throws Exception {
        YamlConfiguration yamlConfiguration = new YamlConfiguration();

        yamlConfiguration.setTemplateContentKeys(getYamlMapWithParentComponentName(masterRequest, "templateContentKeys"));
        Files.writeString(Paths.get("templateContentKeys.txt"), yamlConfiguration.getTemplateContentKeys()+"");

        HashMap<String, Object> templateContentKeysYaml = (HashMap<String, Object>) getTemplateConfigHashMap().get("templateLayout");
        Files.writeString(Paths.get("templateLayout.txt"), templateContentKeysYaml+"");

//        yamlConfiguration.setTemplateLayout(getYamlMap(masterRequest, "templateLayout"));
//        yamlConfiguration.setTemplateFlow(getYamlMap(masterRequest, "templateFlow"));


        return yamlConfiguration;
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
        getConcreteHashMap(masterRequestHashMap, templateContentKeysYaml);

        HashMap<String, Object> output = new HashMap<>();
        flatteringMap(templateContentKeysYaml, output);

        return output;
    }

    private HashMap getTemplateConfigHashMap() throws Exception {
        InputStream templateConfig = resourceService.getTemplateConfig();
        Yaml yaml = new Yaml();
        HashMap templateConfigHashMap = yaml.load(templateConfig);

        return templateConfigHashMap;
    }

    private HashMap<String, Object> getMasterRequestHashMap(MasterRequest request) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> masterRequestHashMap = objectMapper.convertValue(request, HashMap.class);
        return masterRequestHashMap;
    }

    private void getConcreteHashMap(HashMap<String, Object> masterRequestHashMap, HashMap<String, Object> configHashMap) {
        for (Map.Entry<String, Object> entry : masterRequestHashMap.entrySet()) {
            String yamlFieldName = getYamlFieldName(entry.getKey());
            Object fieldValue = entry.getValue();

            if (fieldValue instanceof HashMap) {
                if (configHashMap.keySet().contains(yamlFieldName)) {
                    HashMap<String, Object> valueByFieldName = (HashMap) configHashMap.get(yamlFieldName);
                    getConcreteHashMap((HashMap<String, Object>) fieldValue, valueByFieldName);
                }
            }
            else if (fieldValue instanceof List) {

            }
            else {
                if (configHashMap.keySet().contains(yamlFieldName)) {
                    HashMap<String, Object> valueByFieldName = (HashMap) configHashMap.get(yamlFieldName);
                    String yamlValueName = getYamlValueName((String) fieldValue);
                    removeKeyValueReverse(yamlValueName, valueByFieldName);

                    Object byFieldValue = valueByFieldName.get(yamlValueName);
                    if ((byFieldValue instanceof HashMap)) {
                        getConcreteHashMap(masterRequestHashMap, (HashMap<String, Object>) byFieldValue);
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

    private void flatteringMap(HashMap<String, Object> masterRequestHashMap, HashMap<String, Object> output) {
        for (Map.Entry<String, Object> entry : masterRequestHashMap.entrySet()) {
            String fieldName = entry.getKey();
            Object fieldValue = entry.getValue();

            if (fieldValue instanceof HashMap) {
                flatteringMap((HashMap<String, Object>) fieldValue, output);
            }
            else {
                if (!output.containsKey(fieldName)) {
                    output.put(fieldName, fieldValue);
                }
            }
        }
    }

}
