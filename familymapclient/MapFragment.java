package austen.arts.familymapclient;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import austen.arts.familymapclient.Model.Event;
import austen.arts.familymapclient.Model.Model;
import austen.arts.familymapclient.Model.Person;

public class MapFragment extends Fragment {

    private String mPersonID;
    private GoogleMap theMap;
    private TextView mEventText;
    private ImageButton mPersonButton;
    private Polyline mPolyline;
    private String mEventID = null;
    private float mBirthLatitude;
    private float mBirthLongitude;
    private Set<Polyline> mFamilyLines = new HashSet<>();
    private Set<Polyline> mSpouseLines = new HashSet<>();
    private Set<Polyline> mLifeLines = new HashSet<>();
    private Set<Marker> mFemaleMarkers = new HashSet<>();
    private Set<Marker> mMaleMarkers = new HashSet<>();
    private Marker mMarker;

    public MapFragment() {}

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Model.getInstance().getPeopleEvents() == null) {
            Model.getInstance().setPeopleEvents();
        }
        if (Model.getInstance().getEventTypes() == null) {
            Model.getInstance().setEventTypes();
        }
        if (Model.getInstance().getMarkerColors() == null) {
            Model.getInstance().setMarkerColors();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(GoogleMap googleMap) {
                theMap = googleMap;
                theMap.setMapType(Model.getInstance().getSettings().getMapType());
                updateLineColors();
                removePreferredLines();
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        mEventText = (TextView) v.findViewById(R.id.event_space);
        mPersonButton = (ImageButton) v.findViewById(R.id.person_button);
        mPersonButton.setEnabled(false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(GoogleMap googleMap) {
                theMap = googleMap;
                theMap.setMapType(Model.getInstance().getSettings().getMapType());

                addEventMarkersToMap();

                if (mEventID != null) {
                    centerOnEvent(mEventID);
                    onClick(mEventID);
                }

                /* Clicking on a marker */
                theMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        String eventID = marker.getTag().toString();
//                        mEventID = eventID;
                        onClick(eventID);

                        return false;
                    }
                });

                /* Create the icon listener to open PersonActivity */
                mPersonButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = PersonActivity.newIntent(getActivity(), mPersonID);
                        startActivity(intent);
                    }
                });
            }
        });

        return v;
    }

    /* ALL OTHER FUNCTIONS */

    /**
     * Adds the Event markers to the map
     */
    private void addEventMarkersToMap() {
        for (Map.Entry<String, Event> entry : Model.getInstance().getEvents().entrySet()) {
            Event event = entry.getValue();
            float lat = Float.parseFloat(event.getLatitude());
            float lon = Float.parseFloat(event.getLongitude());
            LatLng doubleL = new LatLng(lat, lon);
            String gender = Model.getInstance().getPeople().get(event.getPersonID()).getGender();
            mMarker = theMap.addMarker(new MarkerOptions().position(doubleL).title(event.getType()));
            mMarker.setTag(event.getEventID());
            if (gender.toLowerCase().equals("m")) {
                mMaleMarkers.add(mMarker);
            }
            else {
                mFemaleMarkers.add(mMarker);
            }
            Integer color = (int) Model.getInstance().getMarkerColors().get(event.getType());
            mMarker.setIcon(BitmapDescriptorFactory.defaultMarker(color));
        }
    }

    /**
     * Activated when a marker is clicked on.
     * @param eventID
     */
    private void onClick(String eventID) {
        mPersonButton.setEnabled(true);

        String information = setEventInformation(eventID);
        String personID = Model.getInstance().getEvents().get(eventID).getPersonID();
        String gender = Model.getInstance().getPeople().get(personID).getGender();

        mPersonButton.setImageDrawable(personIcon(gender));
        removeAllLines();
        setLifeLines(personID);
        setSpouseLines(personID);
        drawFamilyLines(personID);

        mEventText.setText(information);
        removePreferredLines();
    }

    /**
     * Sets up the information for the event_space.
     * @param eventID
     * @return returns a string with the information.
     */
    private String setEventInformation(String eventID) {
        Event event = Model.getInstance().getEvents().get(eventID);
        String personID = event.getPersonID();
        mPersonID = personID;
        String firstName = Model.getInstance().getPeople().get(personID).getFirstName();
        String lastName = Model.getInstance().getPeople().get(personID).getLastName();
        String eventType = Model.getInstance().getEvents().get(eventID).getType();
        String city = Model.getInstance().getEvents().get(eventID).getCity();
        String country = Model.getInstance().getEvents().get(eventID).getCountry();
        String year = Model.getInstance().getEvents().get(eventID).getYear();

        String information = firstName + " " + lastName + "\n" +
                eventType + ": " + city + ", " +
                country + "(" + year + ")";

        return information;
    }

    /**
     * Sets the lines on the map. Called by all other line drawing functions.
     * @param personID
     * @param isSpouse
     * @param lat
     * @param lon
     */
    private void setLines(String personID , boolean isSpouse, float lat, float lon) {

        Event[] events = Model.getInstance().getPeopleEvents().get(personID);

        float lat1 = Float.parseFloat(events[0].getLatitude());
        float lon1 = Float.parseFloat(events[0].getLongitude());
        if (isSpouse) {
            mPolyline = theMap.addPolyline(new PolylineOptions().add(
                    new LatLng(mBirthLatitude, mBirthLongitude),
                    new LatLng(lat1, lon1)
            ));
        }
        else {
            mPolyline = theMap.addPolyline(new PolylineOptions().add(
                    new LatLng(lat, lon),
                    new LatLng(lat1, lon1)
            ));
        }

        if (isSpouse) {
            mPolyline.setColor(Model.getInstance().getSettings().getSpouseLinesColor());
            mSpouseLines.add(mPolyline);
        }
        else {
            mPolyline.setColor(Model.getInstance().getSettings().getFamilyTreeLinesColor());
            mFamilyLines.add(mPolyline);
        }
    }

    /**
     * Draws the lines for the life events of the selected person.
     * @param personID
     */
    private void setLifeLines(String personID) {
        Event[] events = Model.getInstance().getPeopleEvents().get(personID);
        mBirthLatitude = Float.parseFloat(events[0].getLatitude());
        mBirthLongitude = Float.parseFloat(events[0].getLongitude());

        PolylineOptions polylineOptions = new PolylineOptions();
        for (int i = 0; i < events.length; i++) {
            float lat = Float.parseFloat(events[i].getLatitude());
            float lon = Float.parseFloat(events[i].getLongitude());
            polylineOptions.add(new LatLng(lat, lon));
        }
        Polyline polyline = theMap.addPolyline(polylineOptions);
        polyline.setColor(Model.getInstance().getSettings().getLifeStoryLinesColor());
        mLifeLines.add(polyline);
    }

    /**
     * Draws the family lines of the selected person recursively.
     * @param personID
     */
    private void drawFamilyLines(String personID) {
        Person child = Model.getInstance().getPeople().get(personID);
        Event[] events = Model.getInstance().getPeopleEvents().get(personID);
        float lat = Float.parseFloat(events[0].getLatitude());
        float lon = Float.parseFloat(events[0].getLongitude());
        setLines(personID, false, lat, lon);

        if (child.getFatherID() != null && child.getMotherID() != null) {
            if (!child.getFatherID().equals("") && !child.getMotherID().equals("")) {
                drawFamilyLines(child.getFatherID());
                drawFamilyLines(child.getMotherID());
            }
        }
    }

    /**
     * Draws a line from the selected person to their spouse.
     * @param personID
     */
    private void setSpouseLines(String personID) {
        String spouseID = Model.getInstance().getPeople().get(personID).getSpouseID();
        setLines(spouseID, true, mBirthLatitude, mBirthLongitude);
    }

    private void removeFamilyLines() {
        if (mFamilyLines.size() > 0) {
            for (Polyline p : mFamilyLines) {
                p.remove();
            }
        }
    }

    private void removeLifeLines() {
        if (mLifeLines.size() > 0) {
            for (Polyline p : mLifeLines) {
                p.remove();
            }
        }
    }

    private void removeSpouseLines() {
        if (mSpouseLines.size() > 0) {
            for (Polyline p : mSpouseLines) {
                p.remove();
            }
        }
    }

    private void removeAllLines() {
        removeFamilyLines();
        removeLifeLines();
        removeSpouseLines();
    }

    private void removePreferredLines() {

        /* Check the settings and remove the unwanted lines. */
        if (!Model.getInstance().getSettings().isLifeStoryLinesOn()) {
            removeLifeLines();
        }
        if (!Model.getInstance().getSettings().isSpouseLinesOn()) {
            removeSpouseLines();
        }
        if (!Model.getInstance().getSettings().isFamilyTreeLinesOn()) {
            removeFamilyLines();
        }
    }

    /**
     * Centers on a specific event. This method is called
     * when the user selects an event in the PersonActivity.
     * @param eventID
     */
    public void centerOnEvent(String eventID) {
        if (eventID == null) {

        }
        else {
            Event theEvent = Model.getInstance().getEvents().get(eventID);
            float latitude = Float.parseFloat(theEvent.getLatitude());
            float longitude = Float.parseFloat(theEvent.getLongitude());
            LatLng target = new LatLng(latitude, longitude);
            theMap.moveCamera(CameraUpdateFactory.newLatLng(target));
        }
    }

    /**
     * Informs the MapFragment that it is being called from
     * the EventActivity.
     * @param eventID
     */
    public void setFromEventActivity(String eventID) {
        mEventID = eventID;
    }

    /**
     * Returns the respective icon (blue for male, pink for female).
     * @param gender
     * @return
     */
    private Drawable personIcon(String gender) {
        String genderLowercase = gender.toLowerCase();

        if (genderLowercase.equals("m")) {
            return getResources().getDrawable(R.drawable.ic_person_blue_24dp);
        }
        else {
            return getResources().getDrawable(R.drawable.ic_person_purple_24dp);
        }
    }

    /**
     * Updates the Settings on the lines and changes their color.
     * Called upon returning from the SettingsActivity.
     */
    private void updateLineColors() {

        for (Polyline p : mLifeLines) {
            p.setColor(Model.getInstance().getSettings().getLifeStoryLinesColor());
        }
        for (Polyline p : mFamilyLines) {
            p.setColor(Model.getInstance().getSettings().getFamilyTreeLinesColor());
        }
        for (Polyline p : mSpouseLines) {
            p.setColor(Model.getInstance().getSettings().getSpouseLinesColor());
        }
    }
}
