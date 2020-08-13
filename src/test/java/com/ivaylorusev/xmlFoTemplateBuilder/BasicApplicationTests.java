package com.ivaylorusev.xmlFoTemplateBuilder;

import com.ivaylorusev.xmlFoTemplateBuilder.ResourceService;
import com.ivaylorusev.xmlFoTemplateBuilder.models.Brand;
import com.ivaylorusev.xmlFoTemplateBuilder.models.ExtendedHashMap;
import com.ivaylorusev.xmlFoTemplateBuilder.models.MailTypeVariant;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.ivaylorusev.xmlFoTemplateBuilder.XmlUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.ivaylorusev.xmlFoTemplateBuilder.models.root.Root;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

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
        MailTypeVariant mtv = MailTypeVariant.VOUCHER_DELIVERY;


        HashMap templateComponentsByBrandAndMailType = getTemplateComponents(b, mtv);

        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<Root> typeRef = new TypeReference<>() {};
        Root r = objectMapper.convertValue(templateComponentsByBrandAndMailType.get("root"), typeRef);

        String rootXml = XmlUtil.convertToXml(r, r.getClass());
        Document rootDocument = parseXmlString(rootXml);

        HashMap<String, HashMap<String,String>> templateAttributesByBrandAndMailType = getTemplateAttributes(b, mtv);
        for(Map.Entry<String, HashMap<String, String>> entry : templateAttributesByBrandAndMailType.entrySet()) {
            XmlUtil.applyAttributes(rootDocument, entry.getKey(), entry.getValue());
        }

        System.out.println(XmlUtil.getDocumentAsString(rootDocument));


    }
    
    private HashMap getTemplateConfigHashMap() throws Exception {
        InputStream templateConfig = resourceService.getTemplateConfig();
        TypeReference<HashMap> typeRef = new TypeReference<>() {};
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        HashMap templateConfigHashMap = objectMapper.readValue(templateConfig, typeRef);
        
        return templateConfigHashMap;
    }

    private HashMap getTemplateComponents(Brand b, MailTypeVariant mtv) throws Exception {
        HashMap templateConfigHashMap = getTemplateConfigHashMap();
        HashMap templateComponents = (HashMap)templateConfigHashMap.get("templateLayouts");

        List<Object> matchedKeys = ExtendedHashMap.findMatchedKeys(templateComponents, b.name());
        for(Object key: matchedKeys) {
            HashMap templateComponentsByBrand = (HashMap)templateComponents.get(key);
            HashMap templateComponentsByBrandAndMailType = (HashMap) ExtendedHashMap.get(templateComponentsByBrand, mtv.getType());
            if (templateComponentsByBrandAndMailType != null) {
                return templateComponentsByBrandAndMailType;
            }
        }
        return null;
    }

    private HashMap getTemplateAttributes(Brand b, MailTypeVariant mtv) throws Exception {
        HashMap templateConfigHashMap = getTemplateConfigHashMap();
        HashMap templateStyleAttributes = (HashMap)templateConfigHashMap.get("templateStyleAttributes");

        List<Object> matchedKeys = ExtendedHashMap.findMatchedKeys(templateStyleAttributes, b.name());
        for(Object key: matchedKeys) {
            HashMap templateAttributesByBrand = (HashMap)templateStyleAttributes.get(key);
            HashMap templateAttributesByBrandAndMailType = (HashMap) ExtendedHashMap.get(templateAttributesByBrand, mtv.getType());
            if (templateAttributesByBrandAndMailType != null) {
                return templateAttributesByBrandAndMailType;
            }
        }
        return null;
    }

    private Document parseXmlString(String xml) throws Exception {
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputSource is = new InputSource();
        is.setCharacterStream(new StringReader(xml));

        Document document = db.parse(is);

        return document;
    }
    
    private Document parseXmlComponent(String componentName) throws Exception {
        InputStream templateComponent = resourceService.getTemplateComponent(componentName);
        DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document document = db.parse(templateComponent);
        
        return document;
    }
    


}
