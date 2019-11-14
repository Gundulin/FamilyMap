package austen.arts.familymapclient.Model;

public class Event {

    private String eventID;
    private String associatedUsername;
    private String personID;
    private String latitude;
    private String longitude;
    private String country;
    private String city;
    private String eventType;
    private String year;

    public Event(String eventID, String associatedUsername, String personID, String latitude,
                 String longitude, String country, String city, String type, String year) {
        this.eventID = eventID;
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = type;
        this.year = year;
    }

    public String getEventID() {
        return eventID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getType() {
        return eventType;
    }

    public String getYear() {
        return year;
    }
}
