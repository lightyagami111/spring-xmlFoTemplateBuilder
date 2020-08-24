package com.ivaylorusev.xmlFoTemplateBuilder.services;

import com.ivaylorusev.xmlFoTemplateBuilder.ResourceService;
import com.ivaylorusev.xmlFoTemplateBuilder.services.model.YamlModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Service
public class AttachmentsYamlService {

    @Autowired
    private ResourceService resourceService;

    public YamlConfiguration getYamlConfiguration(YamlControlProperties yamlControlProperties) throws Exception {
        YamlModel templateConfigHashMap = getTemplateConfigHashMap();

        YamlConfiguration yamlConfiguration = new YamlConfiguration();
        yamlConfiguration.setTemplateLayout(templateConfigHashMap.getTemplateLayout());
        yamlConfiguration.setTemplateFlow(templateConfigHashMap.getTemplateFlow());

        return yamlConfiguration;
    }

    private YamlModel getTemplateConfigHashMap() throws Exception {
        InputStream templateConfig = resourceService.getTemplateConfig();
        Yaml yaml = new Yaml();
        YamlModel templateConfigHashMap = yaml.loadAs(templateConfig, YamlModel.class);

        return templateConfigHashMap;
    }

    private HashMap processYamlMap(YamlControlProperties yamlControlProperties, HashMap<String, Object> yamlMap) {
        filterYamlMap(yamlControlProperties, yamlMap);
        HashMap<String, Object> output = new HashMap<>();
        flatYamlMap(yamlMap, output);

        return output;
    }


    /**
     * @param yamlControlProperties example : {brand=...., mailTypeVariant=...., salutation=....}
     * @param yamlMap               example :
     *                              field[brand]:
     *                              value[VW]:
     *                              root : .....
     *                              field[mailTypeVariant]:
     *                              value[activation]:
     *                              root: .....
     *                              value[SKODA]:
     *                              root : .....
     * @return filtered yamlMap by yamlControlProperties
     */
    private void filterYamlMap(YamlControlProperties yamlControlProperties, HashMap<String, Object> yamlMap) {

    }

    /**
     * remove field[]/value[] mapping
     *
     * @param filteredYamlMap example :
     *                        templateContentKeys:
     *                        field[customerInformation]:
     *                        field[salutation]:
     *                        value[MR]:
     *                        salutation: customerSalutation-MR
     * @param output          example :
     *                        templateContentKeys:
     *                        salutation: customerSalutation-MR
     */
    private void flatYamlMap(HashMap<String, Object> filteredYamlMap, HashMap<String, Object> output) {
        for (Map.Entry<String, Object> entry : filteredYamlMap.entrySet()) {
            String fieldName = entry.getKey();
            Object fieldValue = entry.getValue();

            if (fieldName.startsWith("field[") || fieldName.startsWith("value[")) {
                flatYamlMap((HashMap<String, Object>) fieldValue, output);
            } else {
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
        Iterator<Map.Entry<String, Object>> iter = testMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Object> entry = iter.next();
            if (!key.equalsIgnoreCase(entry.getKey())) {
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
            } else if (fieldValue instanceof Collection) {
                Collection c = (Collection) fieldValue;
                for (Object o : c) {
                    insertContentKeys((HashMap) o, templateContentKeys);
                }
            } else if (fieldName.equalsIgnoreCase("contentKey")) {
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
            } else if (fieldValue instanceof Collection) {
                Collection c = (Collection) fieldValue;
                for (Object o : c) {
                    insertFlowComponents((HashMap) o, templateFlowComponents);
                }
            } else if (fieldName.equalsIgnoreCase("flowComponents")) {
                templateFlow.put(fieldName, templateFlowComponents.get(fieldValue));
            }

        }
    }


}
