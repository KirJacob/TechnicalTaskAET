package ui.scenarios;

import com.codeborne.selenide.Configuration;
import common.helpers.AppHelper;
import org.junit.BeforeClass;

public class BaseTest {

    @BeforeClass
    public static void beforeClass() {
        AppHelper.setWebDriverByOS();
        Configuration.browser = "chrome";
        Configuration.timeout = 30000;
        Configuration.screenshots = true;
        Configuration.captureJavascriptErrors = true;
        Configuration.pageLoadStrategy = "normal";
        Configuration.reportsFolder = "target/reports";
    }


}
