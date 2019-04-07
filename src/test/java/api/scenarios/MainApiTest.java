package api.scenarios;

import api.explorers.MainExplorer;
import common.categories.SmokeTestCategory;
import common.users.BaseUser;
import common.users.MainUser;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Category({SmokeTestCategory.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MainApiTest extends BaseApiTest{

    private BaseUser mainUser = new MainUser();
    private MainExplorer mainExplorer =  new MainExplorer(mainUser);
    private static List<String>idsToRemove = new ArrayList<>();

    @Test
    public void t01GetStatuses(){
        log.info("TEST> it should be possible to get list of statuses and verify that status has the following fields: " +
                "retweet_count, created_at, text");
        Response response = mainExplorer.getHomeStatuses();
        JSONObject firstStatus = mainExplorer.getObjectFromResponseByIndex(response, 0);

        log.info("getting fields retweet_count={} created_at={} text={}",
                firstStatus.getInt("retweet_count"), firstStatus.getString("created_at"), firstStatus.getString("text"));
        Assert.assertTrue("field 'retweet_count' is not presented", firstStatus.has("retweet_count"));
        Assert.assertTrue("field 'created_at' is not presented", firstStatus.has("created_at"));
        Assert.assertTrue("field 'text' is not presented", firstStatus.has("text"));
    }

    @Test
    public void t02RemoveStatus(){
        log.info("TEST> It should be possible to REMOVE status");

        log.info("create status for removal and get its id");
        String textFor = "this status will be removed 8";
        Response response = mainExplorer.createStatus(textFor, 200);
        String id_str = mainExplorer.getIdStrFromResponse(response);
        log.info("created status with id={}", id_str);

        log.info("remove status");
        mainExplorer.removeStatus(id_str);

        log.info("check that status was really removed, 404 status will be returned");
        mainExplorer.getSpecificStatus(id_str, 404);
    }

    @Test
    public void t03CreateStatus(){
        log.info("TEST> It should be possible to CREATE status");

        log.info("create status");
        String textFor = "this status will be created 8";
        Response response = mainExplorer.createStatus(textFor, 200);
        String id_str = mainExplorer.getIdStrFromResponse(response);
        idsToRemove.add(id_str);

        log.info("verify that status was created with text");
        response = mainExplorer.getSpecificStatus(id_str, 200);
        JSONObject updatedStatus = new JSONObject(response.asString());
        Assert.assertEquals("text wasnt set", textFor, updatedStatus.getString("text"));
    }

    @Test
    public void t04StatusDuplicationMessage(){
        log.info("TEST> It should send message and 403 on attempt to create Duplicated status");

        log.info("create status for duplication check");
        String textFor = "this status will be used for duplication test";
        Response response = mainExplorer.createStatus(textFor, 200);
        String id_str = mainExplorer.getIdStrFromResponse(response);
        idsToRemove.add(id_str);

        log.info("try to create duplicated status");
        response = mainExplorer.createStatus(textFor, 403);
        log.info("verify that message is thrown with 403");
        Assert.assertTrue("response contains error message about duplication", response.asString().contains("Status is a duplicate."));
    }

//    @Test
//    public void tempMethodForRemoveAllStatuses(){
//        List<String>statuses = mainExplorer.getAllUserStatusesIds();
//        log.info("Clean all statuses = {}", statuses);
//        statuses.forEach(status -> mainExplorer.removeStatus(status));
//    }

    @AfterClass
    public static void afterAll(){
        log.info("remove all statuses which were created during the test");
        BaseUser mainUser = new MainUser();
        MainExplorer mainExplorer = new MainExplorer(mainUser);
        idsToRemove.forEach(mainExplorer::removeStatus);
    }
}
