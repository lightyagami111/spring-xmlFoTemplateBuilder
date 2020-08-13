package com.ivaylorusev.xmlFoTemplateBuilder.models.root;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;

@Data
public class MasterReference {

    @JsonProperty("master-reference")
    private String masterReference;

    @XmlAttribute(name = "master-reference")
    public String getMasterReference() {
        return masterReference;
    }
}
