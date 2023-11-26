package com.spotify.oauth2.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtils {

    public static Properties propertyRead(String filePath){
        Properties properties = new Properties();
        BufferedReader reader;
        try{
            reader = new BufferedReader(new FileReader(filePath));
            try {
                properties.load(reader);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Unable to load file: "+filePath);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("File not found: "+filePath);
        }
    return properties;
    }
}
