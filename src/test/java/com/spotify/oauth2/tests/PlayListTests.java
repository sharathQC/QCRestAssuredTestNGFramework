package com.spotify.oauth2.tests;

import com.spotify.oauth2.api.Application.api.PlayListApi;
import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.PlayList;

import com.spotify.oauth2.util.DataLoader;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
@Epic("Spotify OAuth 2.0")
@Feature("Playlist API")
public class PlayListTests {
    @Story("Create Playlist Story")
    @Link("https://example.org")
    @Link(name="allure", type="myLink")
    @TmsLink("QC_TC_Create_playlist") //link with Test case
    @Issue("QC_123") //link with issue
    @Description("Should create playlist with valid data")
    @Test(description = "Create Playlist")
    public void create_playlist(){
        PlayList req_playList = playlistBuilder("Rest Auto PlayList 3.1","Rest Auto PlayList Description 3.1",false);

        Response response = PlayListApi.post(req_playList);
        assertStatusCode(response.statusCode(), 201);

        PlayList deserialized_playlist = response.as(PlayList.class);
        assert_playlist(deserialized_playlist, req_playList);
    }
    @Story("Create and Get Playlist Story")
    @Test
    public void create_and_get_playlist(){
        //create playlist
        PlayList req_playList = playlistBuilder("Rest Auto PlayList 3.2.1","Rest Auto PlayList Description 3.2.1",false);
        Response response = PlayListApi.post(req_playList);
        assertStatusCode(response.statusCode(), 201);

        PlayList deserialize_playlist = response.as(PlayList.class);
        assert_playlist(deserialize_playlist, req_playList);
        //get playlist id
       String playlistID = deserialize_playlist.getId();
       System.out.println("playlist id: "+playlistID);

       //get playlist
       Response res = PlayListApi.get(playlistID);
       assertStatusCode(res.statusCode(), 200);
       PlayList response_playlist = res.as(PlayList.class);
       assert_playlist(response_playlist, req_playList);
    }

    @Story("Get Playlist Story")
    @Test
    public void get_playlist(){
        PlayList req_playList = playlistBuilder("Rest Auto PlayList 3.2","Rest Auto PlayList Description 3.2",false);

        Response response = PlayListApi.get(DataLoader.getInstance().get_playlist_id());
        assertStatusCode(response.statusCode(), 200);

        PlayList response_playlist = response.as(PlayList.class);
        assert_playlist(response_playlist, req_playList);
    }
    @Story("Update Playlist Story")
    @Test
    public void update_playlist(){
        PlayList req_playList = playlistBuilder("Rest Auto PlayList 3.4","Rest Auto PlayList Description 3.4",false);

        Response response = PlayListApi.update(req_playList, DataLoader.getInstance().update_playlist_id());
        assertStatusCode(response.statusCode(), 200);
    }

    //negative test case, create playlist with empty name
    @Story("Create Playlist Story with empty name")
    @Test
    public void create_playlist_without_name(){
        PlayList req_playList = playlistBuilder("","Rest Auto PlayList Description 3.5",false);

        Response response = PlayListApi.post(req_playList);
        assertStatusCode(response.statusCode(), 400);

        Error error_response = response.as(Error.class);
        assertError(error_response, 400, "Missing required field: name");
    }
    //create playlist with expired token
    @Story("Create Playlist Story with expired token")
    @Test
    public void create_playlist_with_expired_token(){
        String invalid_acc_token = "ABD";
        PlayList req_playList = playlistBuilder("Rest Auto PlayList 3.6","Rest Auto PlayList Description 3.6",false);

        Response response = PlayListApi.post(invalid_acc_token, req_playList);
        assertStatusCode(response.statusCode(),401);

        Error error_response = response.as(Error.class);
        assertError(error_response, 401, "Invalid access token");
    }
    //will show following method along with arguments on Overview>Execution>Test Body  on RHS of Each Test Case
    @Step
    public PlayList playlistBuilder(String name, String description, Boolean _public){
        return PlayList.builder().
                name(name).
                description(description).
                _public(_public).build();
    }
    @Step
    public void assert_playlist(PlayList deserialized_playlist, PlayList req_playList){
        assertThat(deserialized_playlist.getName(), equalTo(req_playList.getName()));
        assertThat(deserialized_playlist.getDescription(), equalTo(req_playList.getDescription()));
        assertThat(deserialized_playlist.get_public(), equalTo(req_playList.get_public()));
    }
    @Step
    public void assertStatusCode(int actual_status_code, int expected_status_code){
        assertThat(actual_status_code, equalTo(expected_status_code));
    }
    @Step
    public void assertError(Error error_response, int expected_status_code, String error_msg){
        assertThat(error_response.getError().getStatus(), equalTo(expected_status_code));
        assertThat(error_response.getError().getMessage(), equalTo(error_msg));
    }


}
