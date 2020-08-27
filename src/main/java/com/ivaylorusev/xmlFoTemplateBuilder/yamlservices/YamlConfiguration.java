package com.ivaylorusev.xmlFoTemplateBuilder.yamlservices;

import lombok.Data;

import java.util.HashMap;

@Data
public class YamlConfiguration {

    private HashMap<String, Object> templateLayout;
    private HashMap<String, Object> templateFlow;

}
