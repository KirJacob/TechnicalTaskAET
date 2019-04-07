package common.config;

public enum BaseUserName {
    MAIN_USER("mainUser"),
    SECONDARY_USER("secondaryUser");

    private String user;

    BaseUserName(String user) {
        this.user = user;
    }

    public String getBaseUser() {
        return user;
    }
}
