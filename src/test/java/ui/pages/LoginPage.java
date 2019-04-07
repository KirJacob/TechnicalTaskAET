package ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;
import static common.constants.Timeouts.stdWaitTimeout;
import static org.openqa.selenium.By.xpath;

public class LoginPage extends BasePage{

    private static final String PAGE_OUTER = "//*[contains(@id,'page-outer')]";

    private SelenideElement emailFld = $(xpath(PAGE_OUTER + "//*[contains(@class,'js-username-field')]"));

    private SelenideElement passwordFld = $(xpath(PAGE_OUTER + "//*[contains(@class,'js-password-field')]"));

    private SelenideElement signInFBtn = $(xpath(PAGE_OUTER + "//*[contains(@class,'submit')]"));

    private SelenideElement loginPageLnk = $(xpath("//a[@href='/login' and contains(@class,'js-nav')]"));

    public HomePage login(String login, String password) {
        loginPageLnk.click();
        emailFld.waitUntil(Condition.visible, stdWaitTimeout);
        emailFld.setValue(login);
        passwordFld.setValue(password);
        signInFBtn.click();
        signInFBtn.waitUntil(Condition.hidden, stdWaitTimeout);
        waitForUserMenu();
        HomePage homePage = page(HomePage.class);
        return homePage;
    }

}
