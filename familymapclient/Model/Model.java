package austen.arts.familymapclient.Model;

import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import austen.arts.familymapclient.Client;
import austen.arts.familymapclient.MainActivity;

public class Model {

    private static Model theModel;
    private CurrentUser currentUser;
    private String authToken;
    private Map<String, Person> people;
    private Map<String, Event[]> peopleEvents; //key: personID; value: life events.
    private Map<String, Event> events;
    private Event[] eventArray;
    private Person[] personArray;
    private boolean loggedIn = false;
    private Setting settings = settings = new Setting();
    private String mPort;
    private String mHost;
    private String mPassword;
    private Set<String> mEventTypes;
    private Map<String, Integer> mMarkerColors;

    public static Model getInstance() {
        if (theModel == null) {
            theModel = new Model();
        }
        return theModel;
    }

    private Model() {
    }

    public void setCurrentUser(String personID) {

        Person person = people.get(personID);

        currentUser = new CurrentUser(person.getFirstName(), person.getLastName(),
                personID, person.getAssociatedUsername());
    }

    public CurrentUser getCurrentUser() {
        return currentUser;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setPeople(Person[] persons) {

        personArray = persons;
        people = new HashMap<>();

        for (Person p : persons) {
            people.put(p.getPersonID(), p);
        }
    }

    public Map<String, Person> getPeople() {
        return people;
    }

    public void setEvents(Event[] eventses) {
        events = new HashMap<>();
        eventArray = eventses;

        for (Event e : eventses) {
            events.put(e.getEventID(), e);
        }
    }

    public Map<String, Event> getEvents() {
        return events;
    }


    public void setPeopleEvents() {

        peopleEvents = new HashMap<>();

        for (int i = 0; i < personArray.length; i++) {
            ArrayList eventList = new ArrayList<Event>();
            for (int j = 0; j < eventArray.length; j++) {

                if (personArray[i].getPersonID().equals(eventArray[j].getPersonID())) {
                    eventList.add(eventArray[j]);
                }
            }
            Object[] object = eventList.toArray();
            Event[] array = new Event[object.length];
            for (int k = 0; k < object.length; k++) {
                array[k] = (Event) object[k];
            }
            array = sortEventList(array);
            peopleEvents.put(personArray[i].getPersonID(), array);
        }
    }

    public Event[] getEventArray() {
        return eventArray;
    }

    public Map<String, Event[]> getPeopleEvents() {
        return peopleEvents;
    }

    /**
     * Sorts the person's life events into chronological order.
     * @param a
     * @return
     */
    private Event[] sortEventList(Event[] a) {

        Event[] b = a;
        for (int i = 0; i < b.length; i++) {
            if (((i + 1) < b.length)) {
                if (Integer.parseInt(b[i].getYear()) > Integer.parseInt(b[i + 1].getYear())){
                    Event temp = b[i];
                    b[i] = b[i + 1];
                    b[i + 1] = temp;
                }
            }
        }
        return b;
    }

    public Person[] getPersonArray() {
        return personArray;
    }

    public Setting getSettings() {
        return settings;
    }

    public String getPort() {
        return mPort;
    }

    public String getHost() {
        return mHost;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPort(String port) {
        mPort = port;
    }

    public void setHost(String host) {
        mHost = host;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public void setEventTypes() {
        mEventTypes = new HashSet<>();

        for (int i = 0; i < eventArray.length; i++) {
            if (!mEventTypes.contains(eventArray[i].getType())) {
                mEventTypes.add(eventArray[i].getType());
            }
        }
    }

    public Set<String> getEventTypes() {
        return mEventTypes;
    }

    public void setMarkerColors() {

        mMarkerColors = new HashMap<>();
        Random rand = new Random();

        for (String s : mEventTypes) {
            Integer color = Math.abs(rand.nextInt() % 360);
            mMarkerColors.put(s, color);
        }
    }

    public Map<String, Integer> getMarkerColors() {
        return mMarkerColors;
    }

}

