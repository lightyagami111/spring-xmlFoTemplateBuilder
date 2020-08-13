package com.ivaylorusev.xmlFoTemplateBuilder.models.root;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;

@Data
public class PageSequenceContent {

    @JsonProperty("flow-name")
    private String flowName;

    @XmlAttribute(name = "flow-name")
    public String getFlowName() {
        return flowName;
    }
}
