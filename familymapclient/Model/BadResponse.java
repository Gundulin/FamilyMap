package austen.arts.familymapclient.Model;

public class BadResponse {

    private String message;

    public BadResponse(String message) {
        this.message = message;
    }

    public String toString() {
        return this.message;
    }
}
