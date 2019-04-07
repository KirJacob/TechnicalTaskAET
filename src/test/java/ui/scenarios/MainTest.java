package ui.scenarios;

import api.explorers.MainExplorer;
import common.categories.RegressionTestCategory;
import common.categories.SmokeTestCategory;
import common.helpers.TestPrepsHelper;
import common.users.BaseUser;
import common.users.MainUser;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.*;
import org.junit.experimental.categories.Category;
import org.junit.runners.MethodSorters;
import ui.pages.HomePage;
import ui.pages.TweetPage;

import java.util.ArrayList;
import java.util.List;

import static common.config.BaseUserName.MAIN_USER;
import static ui.enums.Navigation.HOME;
import static ui.enums.Navigation.TWEETS;

@Category({SmokeTestCategory.class, RegressionTestCategory.class})
@Slf4j
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MainTest extends BaseTest{

    private static TestPrepsHelper prepsHelper;
    private static String tweetsURL;
    private static HomePage homePage;
    private TweetPage tweetPage;
    private BaseUser mainUser = new MainUser();
    private MainExplorer mainExplorer =  new MainExplorer(mainUser);//FIXME to replace with config reading
    private static List<String> idsToRemove = new ArrayList<>();

    @BeforeClass
    public static void preparation(){
        prepsHelper = new TestPrepsHelper(true, true, false, false);
        prepsHelper.setUser(MAIN_USER);
        homePage = prepsHelper.loginToApp();
        tweetsURL = prepsHelper.getUrl() + "/AutoqaYakov";
    }

    @Test
    public void t01StatusesAreShown(){
        log.info("TEST> it should show same last status in UI and via API");
        homePage.openPageWithURL(HOME, prepsHelper.getUrl());
        Response response = mainExplorer.getHomeStatuses();
        String lastMessageText = mainExplorer.getObjectFromResponseByIndex(response,0).getString("text");
        String textFromUi = homePage.tweetTextFld("1").text();
        log.info("reading statuses API.text={} UI.text={}", lastMessageText, textFromUi);

        log.info("verify that API status text conatins text from UI");
        Assert.assertTrue(lastMessageText.contains(textFromUi));//contains since if we have image text has link
    }

    @Test
    public void t02RemoveStatus(){
        log.info("TEST> it should NOT show status in UI after removing it in API");
        tweetPage = homePage.openPageWithURL(TWEETS, tweetsURL);
        log.info("create status from API");
        String textForStatus = "Mars cannot be terraformed";
        Response response = mainExplorer.createStatus(textForStatus, 200);
        String id_str = mainExplorer.getIdStrFromResponse(response);

        log.info("remove status vi API");
        tweetPage = tweetPage.openPageWithURL(TWEETS, tweetsURL);//see in UI that status was created
        tweetPage.verifyTextIsVisible(textForStatus);
        mainExplorer.removeStatus(id_str);

        log.info("verify in UI that there is no removed status");
        tweetPage = tweetPage.openPageWithURL(TWEETS, tweetsURL);
        tweetPage.verifyTextIsNotVisible(textForStatus);
    }

    @Test
    public void t03UpdateStatus(){
        log.info("TEST> it should show in UI correct text after creation from API");
        log.info("create status via API");
        String textForStatus = "Venus cannot be terraformed";
        Response response = mainExplorer.createStatus(textForStatus, 200);
        String id_str = mainExplorer.getIdStrFromResponse(response);
        idsToRemove.add(id_str);

        log.info("verify that status text is correct in UI");
        tweetPage = homePage.openPageWithURL(TWEETS, tweetsURL);
        tweetPage.verifyTextIsVisible(textForStatus);
    }

    @Test
    public void t04StatusDuplicationMessage(){
        log.info("TEST> it should NOT be possible to create duplicated message and verify it via UI");
        log.info("create status");
        String textFor = "Moon cannot be terraformed";
        Response response = mainExplorer.createStatus(textFor, 200);
        String id_str = mainExplorer.getIdStrFromResponse(response);
        idsToRemove.add(id_str);
        log.info("try to create duplicated status");
        mainExplorer.createStatus(textFor, 403);

        log.info("verify in UI that duplicated status isnt created via");
        tweetPage = homePage.openPageWithURL(TWEETS, tweetsURL);
        Assert.assertEquals("status tests is not 1", 1, tweetPage.getTweetsSize(textFor));
    }

    @AfterClass
    public static void afterTests(){
        log.info("remove all statuses which were created during the test");
        BaseUser mainUser = new MainUser();
        MainExplorer mainExplorer = new MainExplorer(mainUser);
        idsToRemove.forEach(mainExplorer::removeStatus);
        log.info("Logging Out From the system");
        TweetPage tweetPage = homePage.openPageWithURL(TWEETS, tweetsURL);
        tweetPage.logout();
    }
}
