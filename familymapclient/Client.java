package austen.arts.familymapclient;

import android.os.AsyncTask;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import austen.arts.familymapclient.Model.AllEventResponse;
import austen.arts.familymapclient.Model.FamilyResponse;
import austen.arts.familymapclient.Model.LoginRequest;
import austen.arts.familymapclient.Model.LoginResponse;
import austen.arts.familymapclient.Model.RegisterRequest;
import austen.arts.familymapclient.Model.RegisterResponse;

public class Client {

    private URL mURL;

    public Client(URL url) {
        mURL = url;
    }

    public LoginResponse login (LoginRequest logReq) {

        LoginResponse loginResponse = null;

        try {
            HttpURLConnection http = (HttpURLConnection)mURL.openConnection();

            http.setRequestMethod("POST");
            http.setDoOutput(true);

            http.addRequestProperty("Accept", "application/json");

            http.connect();

            /* Convert to JSON for sending */
            Gson gson = new Gson();
            String reqData = gson.toJson(logReq);

            /* Write it to the Request Body */
            OutputStream os = http.getOutputStream();
            os.write(reqData.getBytes());
            os.close();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                System.out.println("Login successful!");
                System.out.println("Retrieving login response...");
                InputStream in = http.getInputStream();
                Reader rabbit = new InputStreamReader(in);

                loginResponse = gson.fromJson(rabbit, LoginResponse.class);
                rabbit.close();
                return loginResponse;
            }
            else {
                System.out.println("Unable to log in.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public RegisterResponse register(RegisterRequest regReq) {

        try {
            HttpURLConnection http = (HttpURLConnection)mURL.openConnection();
            http.setRequestMethod("POST");
            http.setDoOutput(true);
            http.addRequestProperty("Accept", "application/json");
            http.connect();

            /* Convert to JSON for sending */
            Gson gson = new Gson();
            String reqData = gson.toJson(regReq);

            /* Write it to the Request Body */
            OutputStream os = http.getOutputStream();
            os.write(reqData.getBytes());
            os.close();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                System.out.println("User successfully registered!");
                InputStream in = http.getInputStream();
                Reader rabbit = new InputStreamReader(in);

                RegisterResponse response = gson.fromJson(rabbit, RegisterResponse.class);
                rabbit.close();
                return response;
            }
            else {
                System.out.println("Unable to register user");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public FamilyResponse getFamilyForUser(String authToken) {

        FamilyResponse family = null;

        try {
            HttpURLConnection http = (HttpURLConnection)mURL.openConnection();

            http.setRequestMethod("GET");
            http.setDoOutput(false);

            http.addRequestProperty("Authorization", authToken);
            http.addRequestProperty("Accept", "application/json");

            http.connect();

            System.out.println("Retrieving family information...");

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Gson gson = new Gson();
                InputStream in = http.getInputStream();
                Reader rabbit = new InputStreamReader(in);

                family = gson.fromJson(rabbit, FamilyResponse.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return family;
    }

    public AllEventResponse getEvents(String authToken) {

        AllEventResponse response = null;

        try {
            HttpURLConnection http = (HttpURLConnection) mURL.openConnection();

            http.setRequestMethod("GET");
            http.setDoOutput(false);

            http.addRequestProperty("Authorization", authToken);
            http.addRequestProperty("Accept", "application/json");

            http.connect();

            System.out.println("Retrieving event information...");

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Gson gson = new Gson();
                InputStream in = http.getInputStream();
                Reader rabbit = new InputStreamReader(in);

                response = gson.fromJson(rabbit, AllEventResponse.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

}
