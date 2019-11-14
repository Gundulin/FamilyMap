package austen.arts.familymapclient;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

public class EventActivity extends AppCompatActivity {

    private static final String EVENT_ACTIVITY_ID = "austen.arts.familymap.client.EventActivity";
    Fragment mapFrag;
    FragmentManager fragMag = getSupportFragmentManager();
    private String mEventID;

    public static Intent newIntent(Context packageContext, String eventID) {
        Intent intent = new Intent(packageContext, EventActivity.class);
        intent.putExtra(EVENT_ACTIVITY_ID, eventID);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        /* Get the intent information */
        mEventID = getIntent().getStringExtra(EVENT_ACTIVITY_ID);

        /* Set up the toolbar */
        getSupportActionBar().setTitle("Event Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mapFrag = fragMag.findFragmentById(R.id.event_fragment);
        mapFrag = new MapFragment();
        ((MapFragment) mapFrag).setFromEventActivity(mEventID);

       fragMag.beginTransaction()
               .add(R.id.event_fragment, mapFrag)
               .commit();

    }
}
