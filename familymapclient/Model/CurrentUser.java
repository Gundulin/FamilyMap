package austen.arts.familymapclient.Model;

public class CurrentUser {

    private String firstName;
    private String lastName;
    private String personID;
    private String userName;
    private String password;

    public CurrentUser(String firstName, String lastName, String personID, String userName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.personID = personID;
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPersonID() {
        return personID;
    }

    public String getUserName() {
        return userName;
    }
}
