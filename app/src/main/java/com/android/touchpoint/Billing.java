package com.android.touchpoint;

import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Billing {

    private BillingListener listener;

    // Constructor to set listener
    public Billing(BillingListener listener) {
        this.listener = listener;
    }

    // Method to start API request
    public void fetchBillingInfo(String plateCode) {
        String apiUrl = "http://192.168.1.150/parkingci/handheldapi/billing?access=Plate&code=" + plateCode;
        new BillingTask().execute(apiUrl);
    }

    // AsyncTask for making HTTP GET request
    private class BillingTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            HttpURLConnection urlConnection = null;

            try {
                URL url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                int responseCode = urlConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String inputLine;

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    result = response.toString();
                } else {
                    result = "Error: " + responseCode;
                }
            } catch (Exception e) {
                result = "Exception: " + e.getMessage();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if (listener != null) {
                listener.onBillingInfoReceived(result);
            }
        }
    }

    // Listener interface for handling response
    public interface BillingListener {
        void onBillingInfoReceived(String result);
    }
}
