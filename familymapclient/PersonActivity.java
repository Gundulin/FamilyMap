package austen.arts.familymapclient;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import austen.arts.familymapclient.Model.Event;
import austen.arts.familymapclient.Model.Model;
import austen.arts.familymapclient.Model.Person;

public class PersonActivity extends AppCompatActivity {

    private String mPersonID;
    private static final String PERSON_ACTIVITY_ID = "austen.arts.familymap.client.personActivity";
    private TextView mFirstName;
    private TextView mLastName;
    private TextView mGender;
    private Person mPerson;
    private Person mChild;
    private Person mSpouse;
    private Event[] mEvents;
    private ExpandableListView mEventList;
    private ExpandableListAdapter mListAdapter;
    private List<String> mListDataHeader;
    HashMap<String, List<String>> mListDataChild;

    public static Intent newIntent(Context packageContext, String personID) {
        Intent intent = new Intent(packageContext, PersonActivity.class);
        intent.putExtra(PERSON_ACTIVITY_ID, personID);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        /* Set up the toolbar */
        getSupportActionBar().setTitle("Person Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /* Get all the person info */
        mPersonID = getIntent().getStringExtra(PERSON_ACTIVITY_ID);
        mPerson = Model.getInstance().getPeople().get(mPersonID);
        findChild();
        findSpouse();

        /* Attach to XML */
        mFirstName = (TextView) findViewById(R.id.person_first_name);
        mFirstName.setText(mPerson.getFirstName());

        mLastName = (TextView) findViewById(R.id.person_last_name);
        mLastName.setText(mPerson.getLastName());

        mGender = (TextView) findViewById(R.id.person_gender);
        mGender.setText(genderToString(mPerson.getGender()));

        mEventList = (ExpandableListView) findViewById(R.id.event_list);

        makeList();

        mListAdapter = new austen.arts.familymapclient.ExpandableListAdapter(this,
                mListDataHeader, mListDataChild);

        mEventList.setAdapter(mListAdapter);

        mEventList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
               @Override
               public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
                                           int childPosition, long id) {

                   /* If the user clicks on an event */
                   if (groupPosition == 0) {
                        Intent intent = EventActivity.newIntent(PersonActivity.this,
                                mEvents[childPosition].getEventID());
                        startActivity(intent);
                   }

                   /* If the user clicks on a person */
                   if (groupPosition == 1) {
                       if (childPosition == 0) {
                           Intent intent = PersonActivity.newIntent(PersonActivity.this, mSpouse.getPersonID());
                           startActivity(intent);
                       }
                       else {
                           Intent intent = PersonActivity.newIntent(PersonActivity.this, mChild.getPersonID());
                           startActivity(intent);
                       }
                   }
                   return false;
               }
           }
        );
    }

    /**
     * Generates the list for use in the ExpandableListViews.
     */
    private void makeList() {
        Event[] personEvents = Model.getInstance().getPeopleEvents().get(mPersonID);
        mEvents = personEvents;

        mListDataHeader = new ArrayList<String>();
        mListDataChild = new HashMap<>();
        List<String> events = new ArrayList<>();

        for (int i = 0; i < personEvents.length; i++) {

            events.add(prepareEventString(personEvents[i]));
        }
        mListDataHeader.add("Life Events");
        mListDataChild.put("Life Events", events);

        List<String> family = new ArrayList<>();

        if (mSpouse != null) {
            String spouse = mSpouse.getFirstName() + " " + mSpouse.getLastName() + "\n" +
                    "Spouse";
        }

        if (mChild != null) {
            String child = mChild.getFirstName() + " " + mChild.getLastName() + "\n" +
                    "Child";
            family.add(child);
        }

        mListDataHeader.add("Family");
        mListDataChild.put("Family", family);
    }

    /**
     * Takes in the gender ("m" or "f") and changes it to
     * "Male" or "Female" respectively.
     * @param gender
     * @return "Male" or "Female"
     */
    private String genderToString(String gender) {
        String lowerCase = gender.toLowerCase();

        if (lowerCase.equals("m")) {
            return "Male";
        }
        return "Female";
    }

    /**
     * Creates a string to be set inside the ExpandableListView that is
     * designated for events.
     * @param e
     * @return
     */
    private String prepareEventString(Event e) {
        String information = e.getType() + ": " + e.getCity() + ", " + e.getCountry() +
                " (" + e.getYear() + ") " + "\n" + mPerson.getFirstName() + " " +
                mPerson.getLastName();
        return information;
    }

    /**
     * Finds the child of the selected person.
     */
    private void findChild() {
        Person[] children = Model.getInstance().getPersonArray();

        for (int i = 0; i < children.length; i++) {
            if (children[i].getMotherID() != null ||
                children[i].getFatherID() != null) {
                mChild = children[i];
                break;
            }
        }
    }

    /**
     * Finds the spouse of the selected person.
     */
    private void findSpouse() {
        Person[] spouses = Model.getInstance().getPersonArray();

        for (int i = 0; i < spouses.length; i++) {
            if (spouses[i].getPersonID() != null) {
                if (spouses[i].getPersonID().equals(mPerson.getSpouseID())) {
                    mSpouse = spouses[i];
                    break;
                }
            }
        }
    }
}
