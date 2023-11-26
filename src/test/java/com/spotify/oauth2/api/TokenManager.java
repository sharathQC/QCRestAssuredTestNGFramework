package com.spotify.oauth2.api;

import com.spotify.oauth2.util.ConfigLoader;
import io.restassured.response.Response;

import java.time.Instant;
import java.util.HashMap;


public class TokenManager {
    private static String access_token;
    private static Instant expiry_time;

    public static String getToken(){
        try{
            if(access_token == null || Instant.now().isAfter(expiry_time)){
                System.out.println("Renewing the token...");
                Response response =  renew_token();
                access_token= response.path("access_token");
                int expires_in = response.path("expires_in");
                expiry_time = Instant.now().plusSeconds(expires_in-300);
            }else {
                System.out.println("Access token still active, Good to go");
            }
        }catch (Exception e){
            throw new RuntimeException("!!!Failed to get token!!!");
        }
        return access_token;
    }

    private static Response renew_token(){
        HashMap<String, String> form_params = new HashMap<String, String>();
        form_params.put("grant_type", ConfigLoader.getInstance().grant_type());
        form_params.put("refresh_token",ConfigLoader.getInstance().refresh_token());
        form_params.put("client_id",ConfigLoader.getInstance().getClientID());
        form_params.put("client_secret",ConfigLoader.getInstance().client_secret());

        Response response = RestResource.post_renew_token(form_params);

        if(response.statusCode() != 200){
            throw new RuntimeException("!!!ABORT ACCESS TOKEN EXPIRED!!!");
        }
        return response;
    }
}
