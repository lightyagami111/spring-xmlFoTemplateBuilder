package com.ivaylorusev.xmlFoTemplateBuilder.services.model;

import lombok.Data;

import java.util.HashMap;

@Data
public class YamlModel {

    private HashMap<String, Field> templateContentKeys;
    private HashMap<String, Object> templateLayout;
    private HashMap<String, Object> templateFlow;
    private HashMap<String, Object> templateFlowComponents;


    private static class Field {
        private HashMap<String, Value> field;
    }

    private static class Value {
        private HashMap<String, Object> value;
    }

}
