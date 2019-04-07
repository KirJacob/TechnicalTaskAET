package api.explorers;

import common.models.OAuthData;
import common.users.BaseUser;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONObject;

public class BaseExplorer {

    private OAuthData oAuthData;

    public BaseExplorer(BaseUser user) {
        this.oAuthData = user.getoAuthData();
    }

    protected RequestSpecification baseRequest(){
        RestAssured.baseURI = "https://api.twitter.com";
        RestAssured.basePath = "/1.1/";
        return RestAssured
                .given()
                .auth()
                .oauth(oAuthData.getConsumerKey(),
                        oAuthData.getConsumerSecret(),
                        oAuthData.getAccessToken(),
                        oAuthData.getAccessTokenSecret())
                .log()
                .uri()
                .header("Content-Type", "application/json");
    }

    public JSONArray getArrayFromResponse(Response response){
        return new JSONArray(response.asString());
    }

    public JSONObject getObjectFromResponseByIndex(Response response, int index){
        return (JSONObject)getArrayFromResponse(response).get(index);
    }
}