package com.ivaylorusev.xmlFoTemplateBuilder.yamlservices;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.ivaylorusev.xmlFoTemplateBuilder.models.MailTypeVariant;

import java.io.IOException;

public class MailTypeVariantSerializer extends StdSerializer<MailTypeVariant> {

    protected MailTypeVariantSerializer(Class<MailTypeVariant> t) {
        super(t);
    }

    @Override
    public void serialize(MailTypeVariant mailTypeVariant, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(mailTypeVariant.toString());
    }
}
