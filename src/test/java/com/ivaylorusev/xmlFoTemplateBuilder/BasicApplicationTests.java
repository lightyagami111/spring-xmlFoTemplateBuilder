package com.ivaylorusev.xmlFoTemplateBuilder;

import com.ivaylorusev.xmlFoTemplateBuilder.ResourceService;
import com.ivaylorusev.xmlFoTemplateBuilder.models.Brand;
import com.ivaylorusev.xmlFoTemplateBuilder.models.ExtendedHashMap;
import com.ivaylorusev.xmlFoTemplateBuilder.models.MailTypeVariant;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.ivaylorusev.xmlFoTemplateBuilder.XmlUtil;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BasicApplicationTests {
    
    @Autowired
    private ResourceService resourceService;

    @Test
    public void contextLoads() {
    }
    
    @Test
    public void parse() throws Exception {
        Brand b = Brand.VW;
        MailTypeVariant mtv = MailTypeVariant.ACTIVATION;
        
        HashMap templateConfigHashMap = getTemplateConfigHashMap();   

        HashMap templateComponentsByBrand = (HashMap)ExtendedHashMap.get((HashMap)templateConfigHashMap.get("templateComponents"), b.name());
        List<HashMap> templateComponentsByBrandAndMailType = (List)ExtendedHashMap.get(templateComponentsByBrand, mtv.getType());
        buildDocument(ExtendedHashMap.get(templateComponentsByBrand, mtv.getType()));
                       
        
    }
    
    private HashMap getTemplateConfigHashMap() throws Exception {
        InputStream templateConfig = resourceService.getTemplateConfig();
        TypeReference<HashMap> typeRef = new TypeReference<>() {};
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        HashMap templateConfigHashMap = objectMapper.readValue(templateConfig, typeRef);
        
        return templateConfigHashMap;
    }
    
    private Document getTemplateComponent(String componentName) throws Exception {
        InputStream templateComponent = resourceService.getTemplateComponent(componentName);
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = db.parse(templateComponent);
        
        return document;
    }
    
    private HashMap getTemplateAttributes(HashMap templateConfigHashMap, Brand b, MailTypeVariant mtv) {
        HashMap templateAttributesByBrand = (HashMap)((HashMap)templateConfigHashMap.get("templateStyleAttributes")).get(b.name());
        HashMap templateAttributesByBrandAndMailType = (HashMap)ExtendedHashMap.get(templateAttributesByBrand, mtv.getType());
        return templateAttributesByBrandAndMailType;
    }
    
    private void buildDocument(Object components) {
        if (components instanceof List) {
            List<HashMap> templates = (List) components;
            for (HashMap<String, Object> template : templates) {
                for (Map.Entry<String, Object> object : template.entrySet()) {
                    String key = object.getKey();
                    Object value = object.getValue();
                    System.out.println("templateName: " + key);
                    buildDocument(value);
                }
            }
        }
        else if (components instanceof HashMap) {
            HashMap<String, Object> componentsAsMap = (HashMap) components;
            for (Map.Entry<String, Object> object : componentsAsMap.entrySet()) {
                String key = object.getKey();
                Object value = object.getValue();
                if (key.startsWith("@")) {
                    System.out.println("xpath_attr: " + key);
                }
                else {
                    System.out.println("xpath: " + key);
                }
                
                buildDocument(value);
            }
        }
    }

}
