package com.spotify.oauth2.api;

import io.restassured.response.Response;

import java.util.HashMap;

import static com.spotify.oauth2.api.Route.API;
import static com.spotify.oauth2.api.Route.TOKEN;
import static com.spotify.oauth2.api.SpecBuilder.getRequestSpec;
import static com.spotify.oauth2.api.SpecBuilder.getResponseSpec;
import static io.restassured.RestAssured.given;


public class RestResource {

    public static Response post(Object req_playList, String access_token, String path){
        return given(getRequestSpec()).body(req_playList).
                header("Authorization", "Bearer "+access_token).
                when().post(path).
                then().spec(getResponseSpec()).
                extract().response();
    }

    public static Response post_renew_token(HashMap<String,String> form_params){
        return given(SpecBuilder.accessTokenRequestSpec()).
                formParams(form_params).
                when().post(API+TOKEN).
                then().spec(getResponseSpec()).extract().response();
    }

    public static Response get(String access_token, String path){
        return  given(getRequestSpec()).
                header("Authorization", "Bearer "+access_token).
                when().get(path).
                then().spec(getResponseSpec()).
                extract().response();
    }

    public static Response update(Object req_playList, String access_token, String path){
        return given(getRequestSpec()).body(req_playList).
                header("Authorization", "Bearer "+access_token).
                when().put(path).
                then().spec(getResponseSpec()).extract().response();
    }

}
