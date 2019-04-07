package ui.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class HomePage extends BasePage{

    public SelenideElement tweetTextFld(String index){
        String xpathVal = "(//*[contains(@class,'js-tweet-text-container')])[%s]/p";
        xpathVal = String.format(xpathVal, index);
        return $(By.xpath(xpathVal));
    }
}
