package com.ivaylorusev.xmlFoTemplateBuilder.models.root;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@Data
public class PageSequenceMaster {

    @JsonProperty("master-name")
    private String masterName;

    @JsonProperty("single-page-master-reference")
    private MasterReference singlePage;

    @JsonProperty("repeatable-page-master-reference")
    private MasterReference repeatablePage;

    @XmlAttribute(name = "master-name")
    public String getMasterName() {
        return masterName;
    }

    @XmlElement(name = "single-page-master-reference")
    public MasterReference getSinglePage() {
        return singlePage;
    }

    @XmlElement(name = "repeatable-page-master-reference")
    public MasterReference getRepeatablePage() {
        return repeatablePage;
    }
}
