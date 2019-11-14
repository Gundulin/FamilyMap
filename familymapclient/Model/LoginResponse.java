package austen.arts.familymapclient.Model;

public class LoginResponse {

    private String authToken;
    private String userName;
    private String personID;

    /**
     * Upon construction, saves response information
     * for later use.
     * @param authToken
     * @param userName
     * @param personID
     */
    public LoginResponse(String authToken, String userName, String personID)
    {
        this.authToken = authToken;
        this.userName = userName;
        this. personID = personID;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getUserName() {
        return userName;
    }

    public String getPersonID() {
        return personID;
    }
}
