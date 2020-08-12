package com.example;

import com.ivaylorusev.xmlFoTemplateBuilder.ResourceService;
import com.ivaylorusev.xmlFoTemplateBuilder.models.Brand;
import com.ivaylorusev.xmlFoTemplateBuilder.models.ExtendedHashMap;
import com.ivaylorusev.xmlFoTemplateBuilder.models.MailTypeVariant;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.w3c.dom.Document;

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

        HashMap templateComponentsByBrand = (HashMap)((HashMap)templateConfigHashMap.get("templateComponents")).get(b.name());
        HashMap<String,String> templateComponentsByBrandAndMailType = (HashMap)ExtendedHashMap.get(templateComponentsByBrand, mtv.getType());
        
        for (Map.Entry<String, String> entry : templateComponentsByBrandAndMailType.entrySet()) {
            String key = entry.getKey();
            String componentName = entry.getValue();
            Document templateComponent = getTemplateComponent(templateConfigHashMap, componentName);
            
            System.out.println(getTemplateAttributes(templateConfigHashMap, b, mtv, componentName));;
        }
                       
        
    }
    
    private HashMap getTemplateConfigHashMap() throws Exception {
        InputStream templateConfig = resourceService.getTemplateConfig();
        TypeReference<HashMap> typeRef = new TypeReference<>() {};
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        HashMap templateConfigHashMap = objectMapper.readValue(templateConfig, typeRef);
        
        return templateConfigHashMap;
    }
    
    private Document getTemplateComponent(HashMap templateConfigHashMap, String componentName) throws Exception {
        InputStream templateComponent = resourceService.getTemplateComponent(componentName);
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = db.parse(templateComponent);
        
        return document;
    }
    
    private HashMap getTemplateAttributes(HashMap templateConfigHashMap, Brand b, MailTypeVariant mtv, String componentName) {
        HashMap templateAttributesByBrand = (HashMap)((HashMap)templateConfigHashMap.get("templateAttributes")).get(b.name());
        HashMap templateAttributesByBrandAndMailType = (HashMap)ExtendedHashMap.get(templateAttributesByBrand, mtv.getType());
        HashMap<String,String> templateAttributesByComponentName = (HashMap) templateAttributesByBrandAndMailType.get(componentName);
        return templateAttributesByComponentName;
    }

}
