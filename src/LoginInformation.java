import java.util.HashMap;

public class LoginInformation {

    HashMap<String, String> loginData = new HashMap<>();
    public LoginInformation() {
        loginData.put("admin", "admin");
    }

    protected HashMap<String, String> getLoginData() {
        return loginData;
    }

}
