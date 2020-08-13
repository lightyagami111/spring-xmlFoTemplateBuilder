/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivaylorusev.xmlFoTemplateBuilder;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.*;

/**
 *
 * @author Ivaylo Rusev
 */
public final class XmlUtil {
  private XmlUtil(){}
  
  public static void applyAttributes(Document document, String xpathExpression, List<String> attributes) throws Exception {
        // Create XPathFactory object
        XPathFactory xpathFactory = XPathFactory.newInstance();
         
        // Create XPath object
        XPath xpath = xpathFactory.newXPath();
 
        try
        {
            // Create XPathExpression object
            XPathExpression expr = xpath.compile(xpathExpression);
             
            // Evaluate expression result on XML document
            List<Node> nodes = asList((NodeList) expr.evaluate(document, XPathConstants.NODESET));
            for (Node node : nodes) {
                for (String attribute : attributes) {
                    String[] attrNV = attribute.split("=");
                    ((Element)node).setAttribute(attrNV[0],attrNV[1]);                    
                }
            }
                 
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
   }
  
  public static String getDocumentAsString(Document doc) throws IOException, TransformerException {
        TransformerFactory tf = TransformerFactory.newDefaultInstance().newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(writer));
        return new String(writer.toString().getBytes());
    }

  public static List<Node> asList(NodeList n) {
    return n.getLength()==0?
      Collections.<Node>emptyList(): new NodeListWrapper(n);
  }
  
  
  static final class NodeListWrapper extends AbstractList<Node> implements RandomAccess {
    private final NodeList list;
    NodeListWrapper(NodeList l) {
      list=l;
    }
    public Node get(int index) {
      return list.item(index);
    }
    public int size() {
      return list.getLength();
    }
  }
}
