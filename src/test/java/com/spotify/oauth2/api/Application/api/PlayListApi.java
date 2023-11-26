package com.spotify.oauth2.api.Application.api;

import com.spotify.oauth2.api.RestResource;
import com.spotify.oauth2.pojo.PlayList;
import com.spotify.oauth2.util.ConfigLoader;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static com.spotify.oauth2.api.Route.PLAYLISTS;
import static com.spotify.oauth2.api.Route.USERS;
import static com.spotify.oauth2.api.TokenManager.getToken;


public class PlayListApi {
    @Step
    public static Response post(PlayList req_playList){
        return RestResource.post(req_playList, getToken(),USERS+"/"+ ConfigLoader.getInstance().user_id() +PLAYLISTS);
    }
    @Step
    public static Response get(String playlistID){
        return RestResource.get(getToken(), PLAYLISTS+"/"+playlistID);
    }

    @Step
    public static Response update(PlayList req_playList, String playlistID){
        return RestResource.update(req_playList, getToken(), PLAYLISTS+"/"+playlistID);
    }

    public static Response post(String token, PlayList req_playList){
        return RestResource.post(req_playList, token, USERS+"/"+ConfigLoader.getInstance().user_id()+PLAYLISTS);
    }
}
