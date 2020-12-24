package com.cognitree.sangeet.contactServer.Enums;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public interface ContactServerProperties {
    java.util.Properties properties = readPropertiesFile("config.properties");
    int port = Integer.parseInt(properties.getProperty("port"));
    String jdbcUrl = properties.getProperty("jdbcUrl");
    String jdbcUser = properties.getProperty("jdbcUser");
    String jdbcPass = properties.getProperty("jdbcPass");

    static java.util.Properties readPropertiesFile(String fileName) {
        try (InputStream input = new FileInputStream("config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            return prop;
        }
        catch(IOException fileError) {
            fileError.printStackTrace();
        }

        return null;
    }

}