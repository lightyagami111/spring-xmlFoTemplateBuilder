package com.ivaylorusev.xmlFoTemplateBuilder.models.root;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement
public class Root {


    @JsonProperty("layout-master-set")
    private LayoutMasterSet layoutMasterSet;

    @JsonProperty("page-sequence")
    private PageSequence pageSequence;

    @XmlElement(name = "layout-master-set")
    public LayoutMasterSet getLayoutMasterSet() {
        return layoutMasterSet;
    }

    @XmlElement(name = "page-sequence")
    public PageSequence getPageSequence() {
        return pageSequence;
    }
}
