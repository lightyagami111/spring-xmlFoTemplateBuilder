package com.ivaylorusev.xmlFoTemplateBuilder.models.root;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@Data
public class LayoutMasterSet {

    @JsonProperty("simple-page-master")
    private List<SimplePageMaster> simplePageMasters;

    @JsonProperty("page-sequence-master")
    private PageSequenceMaster pageSequenceMaster;

    @XmlElement(name = "simple-page-master")
    public List<SimplePageMaster> getSimplePageMasters() {
        return simplePageMasters;
    }

    @XmlElement(name = "page-sequence-master")
    public PageSequenceMaster getPageSequenceMaster() {
        return pageSequenceMaster;
    }
}
