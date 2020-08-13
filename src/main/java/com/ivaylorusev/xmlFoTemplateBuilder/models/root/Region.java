package com.ivaylorusev.xmlFoTemplateBuilder.models.root;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlAttribute;

@Data
public class Region {

    @JsonProperty("region-name")
    private String regionName;

    @XmlAttribute(name = "region-name")
    public String getRegionName() {
        return regionName;
    }
}
