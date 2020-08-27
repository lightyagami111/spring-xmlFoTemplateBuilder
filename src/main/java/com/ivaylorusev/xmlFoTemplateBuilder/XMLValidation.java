package com.ivaylorusev.xmlFoTemplateBuilder;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

public class XMLValidation {

    public static boolean validateXMLSchema(String xmlPath){

        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

            URL[] urls = { XMLValidation.class.getResource("/schema-xslt.xsd"), XMLValidation.class.getResource("/schema-fop.xsd") };

            Schema schema = factory.newSchema(XMLValidation.class.getResource("/schema-fop.xsd"));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(Paths.get(xmlPath).toString())));
        } catch (IOException | SAXException e) {
            System.out.println("Exception: "+e.getMessage());
            return false;
        }
        return true;
    }

}
