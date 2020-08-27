package com.ivaylorusev.xmlFoTemplateBuilder;

import com.ivaylorusev.xmlFoTemplateBuilder.models.*;
import com.ivaylorusev.xmlFoTemplateBuilder.yamlservices.YamlControlProperties;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReadCSV {

    @Autowired
    private ResourceService resourceService;

    public List<MasterRequest> readYamlPropCombinations(int stop) {
        List<MasterRequest> masterRequests = new ArrayList<>();

        CsvParserSettings settings = new CsvParserSettings();
        settings.getFormat().setLineSeparator("\n");
        settings.setHeaderExtractionEnabled(false);
        CsvParser parser = new CsvParser(settings);

        List<Record> allRecords = parser.parseAllRecords(
                                    resourceService.getInputStream(
                                                Paths.get("test-combinations.csv")));

        int i = 0;
        for (Record r : allRecords) {

            if (stop == i) {
                break;
            }

            Brand b = Brand.valueOf(r.getString(0));
            MailTypeVariant m = MailTypeVariant.valueOf(r.getString(1));
            Salutation s = Salutation.valueOf(r.getString(2));
            CustomerType c = CustomerType.valueOf(r.getString(3));
            PaymentType p = PaymentType.valueOf(r.getString(4));
            String voucherRef = r.getString(5);

            masterRequests.add(
                    new MasterRequest(b, m, new CustomerInformation(s, c), new PaymentData(p), voucherRef)
            );

            i++;

        }

        return masterRequests;

    }

}
