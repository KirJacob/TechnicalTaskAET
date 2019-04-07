package ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import common.helpers.AppHelper;
import ui.enums.Navigation;

import static com.codeborne.selenide.Selenide.*;
import static common.constants.Timeouts.stdWaitTimeout;
import static org.openqa.selenium.By.xpath;

public class BasePage {

    private SelenideElement userMenuBtn = $(xpath("//*[contains(@id,'user-dropdown-toggle')]"));

    private SelenideElement logOutLnk = $(xpath("//*[contains(@id,'signout-button')]/button"));

    public void waitForUserMenu(){
        userMenuBtn.waitUntil(Condition.visible, stdWaitTimeout);
    }

    public <T extends BasePage> T openPageWithURL(Navigation navObj, String url){
        open(url);
        AppHelper.waitForLoad();
        return page((Class<T>) navObj.getPageClass());
    }

    public void logout(){
        userMenuBtn.click();
        logOutLnk.click();
        AppHelper.waitForLoad();
    }
}
