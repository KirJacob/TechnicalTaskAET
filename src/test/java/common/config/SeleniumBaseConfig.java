package common.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import static common.config.BaseUserName.MAIN_USER;
import static common.config.BaseUserName.SECONDARY_USER;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeleniumBaseConfig {
    SeleniumBaseUser mainUser;
    SeleniumBaseUser secondaryUser;

    public SeleniumBaseUser getByName(BaseUserName name) {

        Map<BaseUserName, SeleniumBaseUser> userMap = new HashMap<BaseUserName, SeleniumBaseUser>();
        userMap.put(MAIN_USER, mainUser);
        userMap.put(SECONDARY_USER, secondaryUser);

        return userMap.get(name);
    }

    @Override
    public String toString() {
        return "SeleniumBaseConfig{" +
                "mainUser=" + mainUser +
                ", secondaryUser=" + secondaryUser +
                '}';
    }
}
