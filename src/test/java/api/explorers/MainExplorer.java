package api.explorers;

import common.users.BaseUser;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MainExplorer extends BaseExplorer {

    public MainExplorer(BaseUser user) {
        super(user);
    }

    private final static String getHomeStatusesEndpoint = "statuses/home_timeline.json";
    private final static String updateStatusEndpoint = "statuses/update.json";
    private final static String showStatusEndpoint = "statuses/show.json";
    private final static String removeStatusEndpoint = "statuses/destroy/%s.json";
    private final static String getUserStatusesEndpoint = "statuses/user_timeline.json";

    public Response getHomeStatuses(){
        Response response =  baseRequest()
                .get(getHomeStatusesEndpoint);
        response.then().statusCode(200);
        log.info("response={}", response.asString());
        return response;
    }

    public Response createStatus(String text, int statusCode){
        Response response = baseRequest()
                .queryParam("status", text)
                .post(updateStatusEndpoint);
        response.then().statusCode(statusCode);
        log.info("response={}", response.asString());
        return response;
    }

    public Response removeStatus(String idStr){
        String endpoint = String.format(removeStatusEndpoint, idStr);
        Response response = baseRequest()
                .post(endpoint);
        response.then().statusCode(200);
        log.info("response={}", response.asString());
        return response;
    }

    public Response getSpecificStatus(String idStr, int statusCode){
        Response response = baseRequest()
                .queryParam("id", idStr)
                .get(showStatusEndpoint);
        response.then().statusCode(statusCode);
        log.info("response={}", response.asString());
        return response;
    }

    public Response getAllUserStatuses(){
        Response response = baseRequest()
                .get(getUserStatusesEndpoint);
        response.then().statusCode(200);
        log.info("response={}", response.asString());
        return response;
    }

    public List<String> getAllUserStatusesIds(){
        Response response = getAllUserStatuses();
        JSONArray jsonArray = new JSONArray(response.asString());
        List<String>result = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i ++)
            result.add(jsonArray.getJSONObject(i).getString("id_str"));
        return result;
    }

    public String getIdStrFromResponse(Response response){
        JSONObject jsonObject = new JSONObject(response.asString());
        return jsonObject.getString("id_str");
    }
}