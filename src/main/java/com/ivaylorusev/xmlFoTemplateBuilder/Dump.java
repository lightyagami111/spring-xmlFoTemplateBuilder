package com.ivaylorusev.xmlFoTemplateBuilder;

import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Dump {

    public static void dumpToYamlFile(String name, Object data) throws IOException {
        Yaml yaml = new Yaml();
        StringWriter writer = new StringWriter();
        yaml.dump(data, writer);

        Files.writeString(Paths.get(name+".yaml"), writer.toString());
    }

    public static void dumpToXlsFile(String name, String template) throws IOException {
        Files.writeString(Paths.get(name + ".xsl"), template);
    }

    public static void dumpException(String name, Exception ex) throws FileNotFoundException {
        File file = new File(Paths.get(name + ".log").toString());
        PrintStream ps = new PrintStream(file);
        ex.printStackTrace(ps);
        ps.close();
    }

}
