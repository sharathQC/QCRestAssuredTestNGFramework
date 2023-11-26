package com.spotify.oauth2.util;

import java.util.Properties;

public class DataLoader {
    private static DataLoader dataLoader;
    private static Properties properties;

    private DataLoader(){
        properties = PropertyUtils.propertyRead("src/test/resources/data.properties");
    }

    public static DataLoader getInstance(){
        if(dataLoader == null){
            dataLoader = new DataLoader();
        }
        return dataLoader;
    }
    public String get_playlist_id(){
        String prop = properties.getProperty("get_playlist_id");
        if(prop != null) return prop;
        else throw new RuntimeException("property client_id is not specified in the config.properties file");
    }

    public String update_playlist_id(){
        String prop = properties.getProperty("update_playlist_id");
        if(prop != null) return prop;
        else throw new RuntimeException("property grant_type is not specified in the config.properties file");
    }


}
