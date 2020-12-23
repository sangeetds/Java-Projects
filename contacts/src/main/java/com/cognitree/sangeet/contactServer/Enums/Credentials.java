package com.cognitree.sangeet.contactServer.Enums;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public interface Credentials {
    Properties properties = readPropertiesFile("config.properties");
    String user = properties.getProperty("user");
    String pass = properties.getProperty("pass");
    int port = Integer.parseInt(properties.getProperty("port"));
    String jdbcUrl = properties.getProperty("jdbcUrl");
    String jdbcUser = properties.getProperty("jdbcUser");
    String jdbcPass = properties.getProperty("jdbcPass");

    static Properties readPropertiesFile(String fileName) {
        FileInputStream file = null;
        Properties prop = null;

        try {
            file = new FileInputStream(fileName);
            prop = new Properties();
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