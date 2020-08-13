package com.ivaylorusev.xmlFoTemplateBuilder.models.root;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@Data
public class SimplePageMaster {

    @JsonProperty("master-name")
    private String masterName;

    @JsonProperty("region-body")
    private Region regionBody;

    @JsonProperty("region-before")
    private Region regionBefore;

    @JsonProperty("region-after")
    private Region regionAfter;

    @XmlAttribute(name = "master-name")
    public String getMasterName() {
        return masterName;
    }

    @XmlElement(name = "region-body")
    public Region getRegionBody() {
        return regionBody;
    }

    @XmlElement(name = "region-before")
    public Region getRegionBefore() {
        return regionBefore;
    }

    @XmlElement(name = "region-after")
    public Region getRegionAfter() {
        return regionAfter;
    }
}
