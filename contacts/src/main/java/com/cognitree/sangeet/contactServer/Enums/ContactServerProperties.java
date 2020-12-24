package com.cognitree.sangeet.contactServer.Enums;

import java.io.FileInputStream;
import java.io.IOException;

public interface ContactServerProperties {
    java.util.Properties properties = readPropertiesFile("config.properties");
    int port = Integer.parseInt(properties.getProperty("port"));
    String jdbcUrl = properties.getProperty("jdbcUrl");
    String jdbcUser = properties.getProperty("jdbcUser");
    String jdbcPass = properties.getProperty("jdbcPass");

    static java.util.Properties readPropertiesFile(String fileName) {
        FileInputStream file = null;
        java.util.Properties prop = null;

        try {
            file = new FileInputStream(fileName);
            prop = new java.util.Properties();
            prop.load(file);
        } catch(IOException fileError) {
            fileError.printStackTrace();
        } finally {
            if (file != null) {
                try {
                    file.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return prop;
    }

}