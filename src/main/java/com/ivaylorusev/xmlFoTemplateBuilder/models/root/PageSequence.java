package com.ivaylorusev.xmlFoTemplateBuilder.models.root;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@Data
public class PageSequence {

    @JsonProperty("master-reference")
    private String masterReference;

    @JsonProperty("static-content")
    private List<PageSequenceContent> staticContent;

    @JsonProperty("flow")
    private PageSequenceContent flow;

    @XmlAttribute(name = "master-reference")
    public String getMasterReference() {
        return masterReference;
    }

    @XmlElement(name = "static-content")
    public List<PageSequenceContent> getStaticContent() {
        return staticContent;
    }

    @XmlElement(name = "flow")
    public PageSequenceContent getFlow() {
        return flow;
    }
}
