package common.users;

import common.models.OAuthData;

public class MainUser extends BaseUser{
    private static final OAuthData oAuthData =
            new OAuthData(
                    "qFhkAfcirWe1QDRCpXtliH3HJ",
                    "Kwt1xqveyiMwemrO0cLDXYOQWAbp5INTY0A1tbi2JiF3KAecDx",
                    "881859727252238336-oDvPcsk8qhOwULaEhXsgJ7NzI19lVfv",
                    "knYi2E1E7neJ0wIYG72nTvjkjV2DVQ1P1wrakeTPQlY13"
                    );

    public MainUser(){
        super(oAuthData);
    }
}
