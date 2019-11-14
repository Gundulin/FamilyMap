package austen.arts.familymapclient;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

import austen.arts.familymapclient.Model.Event;
import austen.arts.familymapclient.Model.LoginRequest;
import austen.arts.familymapclient.Model.LoginResponse;
import austen.arts.familymapclient.Model.Model;
import austen.arts.familymapclient.Model.Person;
import austen.arts.familymapclient.Model.RegisterRequest;
import austen.arts.familymapclient.Model.RegisterResponse;

public class LoginFragment extends Fragment {

    private Button mLoginButton;
    private Button mRegisterButton;
    private EditText mServerHostField;
    private EditText mServerPortField;
    private EditText mUserNameField;
    private EditText mPasswordField;
    private EditText mFirstNameField;
    private EditText mLastNameField;
    private EditText mEmailField;
    private RadioGroup mGenderButtonGroup;
    private RadioButton mMaleButton;
    private RadioButton mFemaleButton;
    boolean mPortFill = false;
    boolean mHostFill = false;
    boolean mUserNameFill = false;
    boolean mPasswordFill = false;
    boolean mFirstNameFill = false;
    boolean mLastNameFill = false;
    boolean mEmailFill = false;
    boolean mGenderFill = false;
    private String mPort;
    private String mHost;
    private String mUserName;
    private String mPassword;
    private String mFirstName;
    private String mLastName;
    private String mEmail;
    private String mGender;
    private String userID;
    private boolean loggedIn;

    public static final String EXTRA_LOGIN_ID = "austen.arts.familymapclient.loggedIn";

    /**
     * Checks to see if the necessary text boxes are filled
     * then activates the Login and Register buttons if the
     * prerequisites are met.
     */
    private void activateButtons () {

        if (mPortFill && mHostFill && mUserNameFill && mPasswordFill)
        {
            mLoginButton.setEnabled(true);
        }
        else
        {
            mLoginButton.setEnabled(false);
        }
        if (mPortFill && mHostFill && mUserNameFill && mPasswordFill &&
        mFirstNameFill && mLastNameFill && mEmailFill && mGenderFill) {
            mRegisterButton.setEnabled(true);
        }
        else
        {
            mRegisterButton.setEnabled(false);
        }
    }

     @Override
    public void onCreate(Bundle savedInstanceState)
     {
         super.onCreate(savedInstanceState);
     }

     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
     {
         View v = inflater.inflate(R.layout.login_fragment, container, false);

        mServerHostField = (EditText) v.findViewById(R.id.server_host);
        mServerHostField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mHostFill = true;
                }
                else
                {
                    mHostFill = false;
                }

                activateButtons();

                mHost = s.toString();
                Model.getInstance().setHost(mHost);
            }
        });

        mServerPortField = (EditText) v.findViewById(R.id.server_port);
        mServerPortField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mPortFill = true;
                }
                else
                {
                    mPortFill = false;
                }

                activateButtons();

                mPort = s.toString();
                Model.getInstance().setPort(mPort);
            }
        });

        mUserNameField = (EditText) v.findViewById(R.id.user_name);
        mUserNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mUserNameFill = true;
                }
                else
                {
                    mUserNameFill = false;
                }

                activateButtons();

                mUserName = s.toString();
            }
        });

        mPasswordField = (EditText) v.findViewById(R.id.password);
        mPasswordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mPasswordFill = true;
                }
                else
                {
                    mPasswordFill = false;
                }

                activateButtons();

                mPassword = s.toString();
            }
        });

        mFirstNameField = (EditText) v.findViewById(R.id.first_name);
        mFirstNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mFirstNameFill = true;
                }
                else
                {
                    mFirstNameFill = false;
                }

                activateButtons();

                mFirstName = s.toString();
            }
        });

        mLastNameField = (EditText) v.findViewById(R.id.last_name);
        mLastNameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mLastNameFill = true;
                }
                else
                {
                    mLastNameFill = false;
                }

                activateButtons();

                mLastName = s.toString();
            }
        });

        mEmailField = (EditText) v.findViewById(R.id.email);
        mEmailField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mEmailFill = true;
                }
                else
                {
                    mEmailFill = false;
                }

                activateButtons();

                mEmail = s.toString();
            }
        });

        /* THE LOGIN BUTTON */
        mLoginButton = (Button) v.findViewById(R.id.login_button);
        mLoginButton.setEnabled(false);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginRequest logReq = new LoginRequest(mUserName, mPassword);
                Model.getInstance().setPassword(mPassword);
                login(logReq);
//                LoginTask loginTask = new LoginTask(logReq);
//
//                try {
//                    /* Log the user in */
//                    String earl = "http://" + mHost + ":" + mPort + "/user/login";
//                    URL url = new URL(earl);
//                    loginTask.execute(url);
//
//                } catch (MalformedURLException e) {
//                    e.printStackTrace();
//                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT);
//                }
            }
        });

        mRegisterButton = (Button) v.findViewById(R.id.register_button);
        mRegisterButton.setEnabled(false);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterRequest regReq = new RegisterRequest(mUserName, mPassword, mFirstName,
                        mLastName, mGender, mEmail);
                Model.getInstance().setPassword(mPassword);

                RegisterTask registerTask = new RegisterTask(regReq);

                try {
                    /* Register the user */
                    String earl = "http://" + mHost + ":" + mPort + "/user/register";
                    URL url = new URL(earl);
                    registerTask.execute(url);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });

        mGenderButtonGroup = (RadioGroup) v.findViewById(R.id.gender_group);
        mMaleButton = (RadioButton) v.findViewById(R.id.male_radio_button);
        mMaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGender = "m";
                mGenderFill = true;
                activateButtons();
            }
        });
        mFemaleButton = (RadioButton) v.findViewById(R.id.female_radio_button);
        mFemaleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGender = "f";
                mGenderFill = true;
                activateButtons();
            }
        });

        return v;
     }

    public class LoginTask extends AsyncTask<URL, Integer, Boolean> {

        private LoginRequest mLoginRequest;

        public LoginTask(LoginRequest loginRequest) {
            mLoginRequest = loginRequest;
        }

        protected Boolean doInBackground(URL... urls) {

            Client client = new Client(urls[0]);
            LoginResponse loginResponse = client.login(mLoginRequest);


            if (loginResponse != null) {
                Model.getInstance().setAuthToken(loginResponse.getAuthToken());
                userID = loginResponse.getPersonID();
                return true;
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean b) {
            if (b) {
                try {
                    loadTheData();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
            else {
                Toast.makeText(getContext(), "Invalid username or password.",Toast.LENGTH_LONG).show();
            }
        }
    }

    public class RegisterTask extends AsyncTask<URL, Integer, Boolean> {
        private RegisterRequest mRegisterRequest;

        public RegisterTask (RegisterRequest registerRequest) {mRegisterRequest = registerRequest; }

        protected Boolean doInBackground(URL... urls) {

            Client client = new Client(urls[0]);
            RegisterResponse registerResponse = client.register(mRegisterRequest);

            if (registerResponse != null) {
                Model.getInstance().setAuthToken(registerResponse.getAuthToken());
                userID = registerResponse.getPersonID();
                return true;
            }
            return false;

        }

        @Override
        protected void onPostExecute(Boolean b) {
            if (b) {
                try {
                    loadTheData();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
            else {
                Toast.makeText(getContext(), "Unable to register", Toast.LENGTH_LONG).show();
            }
        }
    }

    public class PersonTask extends AsyncTask<URL, Integer, Boolean> {

        public PersonTask() {}

        protected Boolean doInBackground(URL... urls) {

            Client client = new Client(urls[0]);

            Person[] people = client.getFamilyForUser(Model.getInstance().getAuthToken()).getData();

            Model.getInstance().setPeople(people);

            if (people != null) {
                return true;
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean b) {
            if (b) {
                Model.getInstance().setCurrentUser(userID);
                System.out.println("PersonTask complete");

            }
            else {
                Toast.makeText(getContext(), "Unable to load Persons Array", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class EventTask extends  AsyncTask<URL, Integer, Boolean> {

        public EventTask() {}

        protected Boolean doInBackground(URL... urls) {

            Client client = new Client(urls[0]);

            Event[] events = client.getEvents(Model.getInstance().getAuthToken()).getData();

            Model.getInstance().setEvents(events);

            if (Model.getInstance().getEvents() != null) {
                return true;
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean b) {
            if (b) {
                System.out.println("EventTask complete");
                welcomeToast();

                ((MainActivity) getActivity()).setLoggedIn(true);
                ((MainActivity) getActivity()).switchFragments();
            }
            else {
                Toast.makeText(getContext(), "Unable to load Events Array", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadTheData() throws MalformedURLException {

        String earl;
        URL url;
        PersonTask personTask = new PersonTask();
        EventTask eventTask = new EventTask();

        /* Get all the people associated with the user */
        earl = "http://" + mHost + ":" + mPort + "/person";
        url = new URL(earl);
        personTask.execute(url);

        /* Get all the evens associated with the user */
        earl = "http://" + mHost + ":" + mPort + "/event";
        url = new URL(earl);
        eventTask.execute(url);
    }

    private void welcomeToast() {
        String welcome = "Welcome, " + Model.getInstance().getCurrentUser().getFirstName() +
                " " + Model.getInstance().getCurrentUser().getLastName() + "!";
        Toast.makeText(getContext(), welcome, Toast.LENGTH_SHORT).show();
    }

    public void login(LoginRequest logReq) {
        LoginTask loginTask = new LoginTask(logReq);
        String host = Model.getInstance().getHost();
        String port = Model.getInstance().getPort();

        try {
            /* Log the user in */
            String earl = "http://" + host + ":" + port + "/user/login";
            URL url = new URL(earl);
            loginTask.execute(url);

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT);
        }
    }
}
