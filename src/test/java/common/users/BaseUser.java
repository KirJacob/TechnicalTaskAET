package common.users;

import common.models.OAuthData;

public class BaseUser {
    private OAuthData oAuthData;

    public BaseUser(OAuthData oAuthData) {
        this.oAuthData = oAuthData;
    }

    public OAuthData getoAuthData() {
        return oAuthData;
    }
}
