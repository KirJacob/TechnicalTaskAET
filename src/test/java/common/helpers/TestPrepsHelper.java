package common.helpers;

import com.codeborne.selenide.WebDriverRunner;
import common.config.BaseUserName;
import common.config.SeleniumBaseConfig;
import common.config.SeleniumBaseUser;
import common.config.TestConfigSingleton;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import ui.pages.HomePage;
import ui.pages.LoginPage;

import static com.codeborne.selenide.Selenide.*;

@Getter
public class TestPrepsHelper {

    private SeleniumBaseConfig seleniumBaseConfig;
    private SeleniumBaseUser baseUser;
    private String email;
    private String password;
    private String url;

    public TestPrepsHelper(boolean isJenkinsResolution, boolean isIncognito, boolean isFullScreen, boolean isMaximized) {
        close();
        WebDriver driver = AppHelper.setCustomDriver(isJenkinsResolution, isIncognito, isFullScreen, isMaximized);
        WebDriverRunner.setWebDriver(driver);
    }

    public SeleniumBaseConfig setUser(BaseUserName user) {
        seleniumBaseConfig = TestConfigSingleton.getBaseConfig(TestConfigSingleton.getPropNamespace());
        baseUser = seleniumBaseConfig.getByName(user);
        email = baseUser.getEmail();
        password = baseUser.getPassword();
        url = baseUser.getUrl();
        return seleniumBaseConfig;
    }

    public HomePage loginToApp(){
        LoginPage loginPage = open(url, LoginPage.class);
        return loginPage.login(email, password);
    }
}
