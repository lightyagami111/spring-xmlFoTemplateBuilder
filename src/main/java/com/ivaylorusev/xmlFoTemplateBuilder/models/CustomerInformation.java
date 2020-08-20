package com.ivaylorusev.xmlFoTemplateBuilder.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerInformation {
    private Salutation salutation;
    private CustomerType customerType;
}
