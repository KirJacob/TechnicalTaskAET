package common.helpers;

import com.codeborne.selenide.WebDriverRunner;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

@Slf4j
public class AppHelper {

    public static void setWebDriverByOS() {
        String osName = System.getProperty("os.name");
        log.info("YOUR OS IS = {}", osName);
        switch (osName) {
            case "Mac OS X":
                System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver_mac64");
                break;
            case "Linux":
                System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver_linux64");
                break;
            case "Windows 10":
                System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver_win32.exe");
                break;
            default:
                break;
        }
    }

    public static WebDriver setCustomDriver(boolean isJenkinsResolution, boolean isIncognito,
                                            boolean isFullScreen, boolean isMaximized) {
        ChromeOptions options = new ChromeOptions();
        if (isJenkinsResolution) options.addArguments("--window-size=1440,900");
        if (isIncognito) options.addArguments("--incognito");
        if (isFullScreen) options.addArguments("--start-fullscreen");
        if (isMaximized) options.addArguments("--start-maximized");
        if (isMaximized && isJenkinsResolution) options.addArguments("--window-size=1440,1000");
        options.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
        return new ChromeDriver(options);
    }

    public static void waitForLoad() {
        WebDriver driver = WebDriverRunner.getWebDriver();
        new WebDriverWait(driver, 30).until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }

}
