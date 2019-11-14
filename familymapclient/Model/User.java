package austen.arts.familymapclient.Model;

public class User {

    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String gender;
    private String personID;

    public User(String userName, String password, String email, String firstName, String lastName,
                String gender, String personID) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = personID;
    }

    public String getUserName() {
        return userName;
    }

    public String getPersonID() {
        return personID;
    }
}
