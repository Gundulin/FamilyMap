package austen.arts.familymapclient.Model;

public class LoginRequest {

    private String userName;
    private String password;

    public LoginRequest(String username, String password) {
        this.userName = username;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
