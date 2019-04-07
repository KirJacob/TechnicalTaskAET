package common.config;

import com.codeborne.selenide.Configuration;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigBeanFactory;
import com.typesafe.config.ConfigFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.ZonedDateTime;

import static common.constants.StringConstants.*;

@Slf4j
public class TestConfigSingleton {

    private static Config config = ConfigFactory.load(ConfigFileName).getConfig(ConfigFileBase);

    private static Config getConfig() {
        return config;
    }

    public static SeleniumBaseConfig getBaseConfig(String namespace) {
        return ConfigBeanFactory.create(config.getConfig(namespace), SeleniumBaseConfig.class);
    }

    public static String getPropNamespace() {
        String osName = System.getProperty("os.name");
        String timeZone = ZonedDateTime.now().getZone().toString();
        boolean isJenkins = (osName.equals("Linux"))&&(timeZone.equals("UTC"));
        log.info(String.format("OSNAME=%s, TIMEZONE=%s, isJenkins=%s", osName, timeZone, isJenkins));

        if (isJenkins){
            log.info("TEST are RUNNING from JENKINS");
        } else {
            log.info("TEST are RUNNING LOCALLY");
            //For Local Run
            System.setProperty(EnvNameSpaceKey, "dev"); Configuration.baseUrl = "http://twitter.com";
        }

        String namespace = System.getProperty(EnvNameSpaceKey);
        if (StringUtils.isEmpty(namespace)) {
            throw new RuntimeException("Namespace could not be empty please pass -D" + EnvNameSpaceKey + "=ZZZ");
        }
        return namespace;
    }
}
