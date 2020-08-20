package com.ivaylorusev.xmlFoTemplateBuilder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.ivaylorusev.xmlFoTemplateBuilder.models.*;
import com.samskivert.mustache.Mustache;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BasicApplicationTests {

    @Autowired
    private ResourceService resourceService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void hashmaps() throws Exception {
        Brand b = Brand.VW;
        MailTypeVariant mtv = MailTypeVariant.CREDIT_NOTE;
        Salutation s = Salutation.MR;
        CustomerType c = CustomerType.COMPANY;
        MasterRequest masterRequest = new MasterRequest();
        masterRequest.setBrand(b);
        masterRequest.setMailType(mtv);
        masterRequest.setCustomerInformation(new CustomerInformation(s, c));

        HashMap<String, Object> templateContentKeys = (HashMap<String, Object>) getTemplateConfigHashMap().get("templateContentKeys");
        HashMap<String, Object> masterRequestHashMap = getHashMap(masterRequest);
        System.out.println(masterRequestHashMap);
        System.out.println("==================");
        getConcreteHashMap(masterRequestHashMap, templateContentKeys);
        System.out.println(templateContentKeys);

    }

    @Ignore
    @Test
    public void parse() throws Exception {
        Brand b = Brand.VW;
        MailTypeVariant mtv = MailTypeVariant.CREDIT_NOTE;

        Mustache.TemplateLoader templateLoader = new Mustache.TemplateLoader() {
            public Reader getTemplate(String name) throws FileNotFoundException {
                return new InputStreamReader(resourceService.getComponentIS("/templateLayouts", name));
            }
        };

        //----------------------------------
        //building root layout
        //----------------------------------
        String rootComponent = resourceService.getComponent("/templateLayouts","root");
        HashMap<String, Object> templateLayoutsByBrandAndMailType = getTemplateComponent("templateLayout", b, mtv);
        Mustache.Compiler templateLayoutCompiler = Mustache.compiler().withDelims("%{ }").escapeHTML(false);
        String templateLayout = templateLayoutCompiler.compile(rootComponent).execute(templateLayoutsByBrandAndMailType);




        //----------------------------------
        //building layout flows
        //----------------------------------
        HashMap<String, Object> templateFlowsByBrandAndMailType = getTemplateComponent("templateFlow", b, mtv);
        Mustache.Compiler templateFlowCompiler = Mustache.compiler().withDelims("${ }").escapeHTML(false).withLoader(templateLoader);
        String templateFlow = templateFlowCompiler.compile(templateLayout).execute(templateFlowsByBrandAndMailType);

        Files.writeString(Paths.get("format.xsl"), templateFlow);

        //----------------------------------
        //resolve content keys & request data fields
        //----------------------------------
    /*
        HashMap<String, Object> templateRequestData = getTemplateRequestData(b, mtv);
        HashMap<String, Object> templateContentKeys = getTemplateContentKeys(b, mtv);
        HashMap<String, Object> templateData = new HashMap<>();
        templateData.putAll(templateRequestData);
        templateData.putAll(templateContentKeys);
        Mustache.Compiler templateDataCompiler = Mustache.compiler().withDelims("&{ }").escapeHTML(false);
        Files.writeString(Paths.get("format.xsl"), templateDataCompiler.compile(templateFlow).execute(templateData));
    */


    }

    private HashMap getTemplateConfigHashMap() throws Exception {
        InputStream templateConfig = resourceService.getTemplateConfig();
        TypeReference<HashMap> typeRef = new TypeReference<>() {
        };
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        HashMap templateConfigHashMap = objectMapper.readValue(templateConfig, typeRef);

        return templateConfigHashMap;
    }

    private HashMap<String, Object> getHashMap(MasterRequest request) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> masterRequestHashMap = objectMapper.convertValue(request, HashMap.class);
        return masterRequestHashMap;
    }

    private void getConcreteHashMap(HashMap<String, Object> masterRequestHashMap, HashMap<String, Object> configHashMap) {
        for (Map.Entry<String, Object> entry : masterRequestHashMap.entrySet()) {
            String fieldName = entry.getKey();
            Object fieldValue = entry.getValue();

            if (fieldValue instanceof HashMap) {
                if (configHashMap.keySet().contains(fieldName)) {
                    HashMap<String, Object> valueByFieldName = (HashMap) configHashMap.get(fieldName);
                    getConcreteHashMap((HashMap<String, Object>) fieldValue, valueByFieldName);
                }
            }
            else if (fieldValue instanceof List) {

            }
            else {
                if (configHashMap.keySet().contains(fieldName)) {
                    HashMap<String, Object> valueByFieldName = (HashMap) configHashMap.get(fieldName);
                    removeKeyValueReverse((String) fieldValue, valueByFieldName);

                    Object byFieldValue = valueByFieldName.get(fieldValue);
                    if ((byFieldValue instanceof HashMap)) {
                        getConcreteHashMap(masterRequestHashMap, (HashMap<String, Object>) byFieldValue);
                    }

                }
            }
        }
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


    private HashMap getTemplateComponent(String component, Brand b, MailTypeVariant mtv) throws Exception {
        HashMap templateConfigHashMap = getTemplateConfigHashMap();
        HashMap templateComponents = (HashMap) templateConfigHashMap.get(component);

        List<Object> matchedKeys = ExtendedHashMap.findMatchedKeys(templateComponents, b.name());
        if (matchedKeys != null) {
            for (Object key : matchedKeys) {
                HashMap templateComponentsByBrand = (HashMap) templateComponents.get(key);
                HashMap templateComponentsByBrandAndMailType = (HashMap) ExtendedHashMap.get(templateComponentsByBrand, mtv.getType());
                if (templateComponentsByBrandAndMailType != null) {
                    return templateComponentsByBrandAndMailType;
                }
            }
        }

        return null;
    }

    private HashMap getTemplateRequestData(Brand b, MailTypeVariant mtv) throws Exception {
        HashMap templateConfigHashMap = getTemplateConfigHashMap();
        return templateConfigHashMap;
    }

    private HashMap getTemplateContentKeys(Brand b, MailTypeVariant mtv, Salutation s) throws Exception {
        HashMap templateConfigHashMap = getTemplateConfigHashMap();
        HashMap templateContentKeys = (HashMap) templateConfigHashMap.get("templateContentKeys");

        HashMap templateContentKeysGeneral = (HashMap) templateContentKeys.get("general"); //templateContentKeys -> general
        HashMap templateContentKeysByBrand = (HashMap) templateContentKeys.get(b.name()); //templateContentKeys -> brand
        HashMap templateContentKeysGeneralByBrand = (HashMap) templateContentKeysByBrand.get("general"); //templateContentKeys -> brand -> general
        HashMap templateContentKeysByBrandAndMailType = getTemplateComponent("templateContentKeys", b, mtv); //templateContentKeys -> brand -> mailtype

        if (templateContentKeysByBrandAndMailType != null) {
            templateContentKeysGeneralByBrand.putAll(templateContentKeysByBrandAndMailType);
        }

        templateContentKeysGeneral.putAll(templateContentKeysGeneralByBrand);

        HashMap templateContentKeysSum = new HashMap();
        templateContentKeysSum.put("templateContentKeys", templateContentKeysGeneral);

        return templateContentKeysSum;

    }


}
