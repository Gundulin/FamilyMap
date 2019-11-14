package austen.arts.familymapclient.Model;

public class RegisterRequest {

    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String gender;
    private String email;

    public RegisterRequest(String userName, String password, String firstName, String lastName, String gender, String email) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
