package ui.enums;

import ui.pages.BasePage;
import ui.pages.HomePage;
import ui.pages.TweetPage;

public enum  Navigation {

    HOME("Home","", HomePage.class),
    TWEETS("Tweets", "",TweetPage.class);

    private String visibleName;
    private String subURL;
    private Class<? extends BasePage> pageClass;

    private Navigation(String visibleName, String subURL, Class<? extends BasePage> pageClass) {
        this.visibleName = visibleName;
        this.subURL = subURL;
        this.pageClass = pageClass;
    }

    public String getVisibleName() {
        return visibleName;
    }

    public String getSubURL() {
        return subURL;
    }

    public Class<? extends BasePage> getPageClass() {
        return pageClass;
    }
}
