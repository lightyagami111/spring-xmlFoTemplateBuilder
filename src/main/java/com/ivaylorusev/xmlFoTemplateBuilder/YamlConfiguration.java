package com.ivaylorusev.xmlFoTemplateBuilder;

import lombok.Data;

import java.util.HashMap;

@Data
public class YamlConfiguration {

    private HashMap<String, Object> templateContentKeys;

    private HashMap<String, Object> templateLayout;
    private HashMap<String, Object> templateFlow;

}
