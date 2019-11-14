package austen.arts.familymapclient.Model;

public class RegisterResponse {

    private String authToken;
    private String userName;
    private String personID;

    /**
     * Upon construction saves all the response
     * data for later use.
     * @param authToken
     * @param userName
     * @param personID
     */
    public RegisterResponse(String authToken, String userName, String personID)
    {
        this.authToken = authToken;
        this.userName = userName;
        this.personID = personID;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getPersonID() {
        return personID;
    }
}
