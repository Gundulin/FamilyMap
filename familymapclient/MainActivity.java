package austen.arts.familymapclient;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/* api key: AIzaSyAMxaNha60H8ELaVI6ByvAMRIY_ybwalEo */

public class MainActivity extends AppCompatActivity {

    private boolean loggedIn = false;
    Fragment logFrag;
    Fragment mapFrag;
    FragmentManager fragMag = getSupportFragmentManager();
    private final int settingID = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logFrag = fragMag.findFragmentById(R.id.fragment_container);
        mapFrag = fragMag.findFragmentById(R.id.fragment_container);

        switchFragments();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (!loggedIn) {
            menu.getItem(0).setVisible(false);
            menu.getItem(1).setVisible(false);
            menu.getItem(2).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.settings:
                Intent intent = SettingsActivity.newIntent(MainActivity.this);
                startActivityForResult(intent, settingID);
            case R.id.filter:

            case R.id.search:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public void switchFragments() {

        invalidateOptionsMenu();
        if (loggedIn) {
            if (logFrag != null) {
                fragMag.beginTransaction()
                        .remove(logFrag)
                        .commit();
            }
            mapFrag = new MapFragment();
            fragMag.beginTransaction()
                    .add(R.id.fragment_container, mapFrag)
                    .commit();
        }
        else
        {
            if (mapFrag != null) {
                fragMag.beginTransaction()
                        .remove(mapFrag)
                        .commit();
            }
            logFrag = new LoginFragment();
            fragMag.beginTransaction()
                    .add(R.id.fragment_container, logFrag)
                    .commit();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /* Logout */
        if (requestCode == settingID && resultCode == RESULT_OK && data != null) {
            loggedIn = false;
            switchFragments();
        }
    }
}
