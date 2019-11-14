package austen.arts.familymapclient.Model;

public class AllEventResponse {

    private Event[] data;

    /**
     * Uses a personID to find all events associated
     * with all family members of the specified user.
     * Saves the response information for later use.
     * @param events
     */
    public AllEventResponse(Event[] events)
    {
        this.data = events;
    }

    public Event[] getData() {
        return data;
    }
}
