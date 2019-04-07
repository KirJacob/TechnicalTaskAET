package ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.openqa.selenium.By.xpath;

public class TweetPage extends BasePage{

    private static final String TWEET_TEXT = "//*[contains(@class,'js-tweet-text')]";

    private SelenideElement tweetWithTxt(String text){
        String xpathVal = TWEET_TEXT + "//*[contains(text(),'%s')]";
        xpathVal = String.format(xpathVal, text);
        return $(xpath(xpathVal));
    }

    private ElementsCollection tweetsWithTxt(String text){
        String xpathVal = TWEET_TEXT + "//*[contains(text(),'%s')]";
        xpathVal = String.format(xpathVal, text);
        return $$(xpath(xpathVal));
    }

    public void verifyTextIsVisible(String textForStatus){
        tweetWithTxt(textForStatus).shouldBe(Condition.visible);
    }

    public void verifyTextIsNotVisible(String textForStatus){
        tweetWithTxt(textForStatus).shouldNotBe(Condition.visible);
    }

    public int getTweetsSize(String textFor){
        return tweetsWithTxt(textFor).size();
    }
}
