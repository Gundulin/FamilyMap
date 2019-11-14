package austen.arts.familymapclient;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;

import com.google.android.gms.maps.GoogleMap;

import austen.arts.familymapclient.Model.Model;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner mLifeStoryLineSpinner;
    private Switch mLifeStoryLineSwitch;
    private Spinner mFamilyTreeLinesSpinner;
    private Switch mFamilyTreeLinesSwitch;
    private Spinner mSpouseLinesSpinner;
    private Switch mSpouseLinesSwitch;
    private Button mLogoutButton;
    private Spinner mMapType;
    public static String LOG_OUT = "loggedOut";
    private Button mResyncButton;
    public static String RESYNCH = "resync";

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, SettingsActivity.class);
        return intent;
    }

   @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_settings);

       /* Set up all the spinners */
       mLifeStoryLineSpinner = findViewById(R.id.life_stor_lines_spinner);
       ArrayAdapter<CharSequence> lifeAdapter = ArrayAdapter.createFromResource(this,
               R.array.line_colors, android.R.layout.simple_spinner_item);
       lifeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       mLifeStoryLineSpinner.setAdapter(lifeAdapter);
       mLifeStoryLineSpinner.setOnItemSelectedListener(this);
       mLifeStoryLineSpinner.setSelection(setColorMenuSelection(Model.getInstance().getSettings()
       .getLifeStoryLinesColor()));

       mFamilyTreeLinesSpinner = findViewById(R.id.family_tree_lines_spinner);
       mFamilyTreeLinesSpinner.setAdapter(lifeAdapter);
       mFamilyTreeLinesSpinner.setOnItemSelectedListener(this);
       mFamilyTreeLinesSpinner.setSelection(setColorMenuSelection(Model.getInstance().getSettings()
       .getFamilyTreeLinesColor()));

       mSpouseLinesSpinner = findViewById(R.id.spouse_lines_spinner);
       mSpouseLinesSpinner.setAdapter(lifeAdapter);
       mSpouseLinesSpinner.setOnItemSelectedListener(this);
       mSpouseLinesSpinner.setSelection(setColorMenuSelection(Model.getInstance().getSettings()
       .getSpouseLinesColor()));

       mMapType = findViewById(R.id.map_type_spinner);
       ArrayAdapter<CharSequence> mapAdapter = ArrayAdapter.createFromResource(this,
               R.array.map_type, android.R.layout.simple_spinner_item);
       mapAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       mMapType.setAdapter(mapAdapter);
       mMapType.setSelection(setMapSelection(Model.getInstance().getSettings().getMapType()));
       mMapType.setOnItemSelectedListener(this);
//       mMapType.setSelection(Model.getInstance().getSettings().getMapType());

       /* Set up all the switches */
       mLifeStoryLineSwitch = findViewById(R.id.life_story_lines_switch);
       mLifeStoryLineSwitch.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (Model.getInstance().getSettings().isLifeStoryLinesOn()) {
                   Model.getInstance().getSettings().setLifeStoryLinesOn(false);
               }
               else {
                   Model.getInstance().getSettings().setLifeStoryLinesOn(true);
               }
           }
       });
       mLifeStoryLineSwitch.setChecked(Model.getInstance().getSettings().isLifeStoryLinesOn());

       mFamilyTreeLinesSwitch = findViewById(R.id.family_tree_lines_switch);
       mFamilyTreeLinesSwitch.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (Model.getInstance().getSettings().isFamilyTreeLinesOn()) {
                   Model.getInstance().getSettings().setFamilyTreeLinesOn(false);
               }
               else {
                   Model.getInstance().getSettings().setFamilyTreeLinesOn(true);
               }
           }
       });
       mFamilyTreeLinesSwitch.setChecked(Model.getInstance().getSettings().isFamilyTreeLinesOn());

       mSpouseLinesSwitch = findViewById(R.id.spouse_lines_switch);
       mSpouseLinesSwitch.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (Model.getInstance().getSettings().isSpouseLinesOn()) {
                   Model.getInstance().getSettings().setSpouseLinesOn(false);
               }
               else {
                   Model.getInstance().getSettings().setSpouseLinesOn(true);
               }
           }
       });
       mSpouseLinesSwitch.setChecked(Model.getInstance().getSettings().isSpouseLinesOn());

       /* Set up the re-sync button */
       mResyncButton = findViewById(R.id.resync_button);
       mResyncButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               resync();
           }
       });

       /* Set up the logout button */
       mLogoutButton = findViewById(R.id.logout_button);
       mLogoutButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                logout();
           }
       });
   }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String selected = parent.getItemAtPosition(position).toString();
        int lineColor = translateColor(selected);

        switch (parent.getId()) {
            case R.id.life_stor_lines_spinner:
                Model.getInstance().getSettings().setLifeStoryLinesColor(lineColor); break;
            case R.id.family_tree_lines_spinner:
                Model.getInstance().getSettings().setFamilyTreeLinesColor(lineColor); break;
            case R.id.spouse_lines_spinner:
                Model.getInstance().getSettings().setSpouseLinesColor(lineColor); break;
            case R.id.map_type_spinner:
                switch(position) {
                    case 0:
                        Model.getInstance().getSettings().setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        break;
                    case 1:
                        Model.getInstance().getSettings().setMapType(GoogleMap.MAP_TYPE_HYBRID);
                        break;
                    case 2:
                        Model.getInstance().getSettings().setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                        break;
                    case 3:
                        Model.getInstance().getSettings().setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                        break;
                    default:
                        Model.getInstance().getSettings().setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * Translates the given String into the integer needed to
     * set the color of the line.
     * @param colorString
     * @return
     */
    private int translateColor(String colorString) {

        switch(colorString) {
            case "Green": return getResources().getColor(R.color.Green);
            case "Purple": return getResources().getColor(R.color.Purple);
            case "Blue": return getResources().getColor(R.color.Blue);
            case "Black": return getResources().getColor(R.color.Black);
            case "Red": return getResources().getColor(R.color.Red);
            case "Yellow": return getResources().getColor(R.color.Yellow);
            default: return 0;
        }
    }

    /**
     * Logs the user out via the MainActivity.
     */
    private void logout() {

        Intent intent = new Intent();
        intent.putExtra(LOG_OUT, "logout");
        setResult(RESULT_OK, intent);
        this.finish();
    }

    /**
     * Resyncs information from the server.
     */
    private void resync() {
        Intent intent = new Intent();
        intent.putExtra(RESYNCH, "resync");
        setResult(RESULT_OK, intent);
        this.finish();
    }

    /**
     * Provides the correct position for the line color spinners.
     * @param color
     * @return
     */
    private int setColorMenuSelection(int color) {

        switch (color) {
            case 0xFF008000: return 0; // Green
            case 0xFF0000FF: return 1; // Blue
            case 0xFFFF0000: return 2; // Red
            case 0xFFFFFF00: return 3; // Yellow
            case 0xFF000000: return 4; // Black
            case 0xFF800080: return 5; // Purple
            default: return 0;
        }
    }

    /**
     * Provides the correct position for the map type spinner.
     * @param mapType
     * @return
     */
    private int setMapSelection(int mapType) {

        switch (mapType) {
            case GoogleMap.MAP_TYPE_NORMAL: return 0;
            case GoogleMap.MAP_TYPE_HYBRID: return 1;
            case GoogleMap.MAP_TYPE_SATELLITE: return 2;
            case GoogleMap.MAP_TYPE_TERRAIN: return 3;
            default: return 0;
        }
    }


}
