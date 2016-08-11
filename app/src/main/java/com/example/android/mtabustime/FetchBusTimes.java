package com.example.android.mtabustime;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class FetchBusTimes extends AsyncTask<Void, Void, Void> {

    Context context;
    ArrayList<Bus> buses = new ArrayList<>();
    ListView listview;
    String stopNumber = "400134";

    public FetchBusTimes(ListView listview, Context context) {
        this.listview = listview;
        this.context = context;
    }

    public FetchBusTimes(ListView listview, Context context, String stopNumber) {
        this.context = context;
        this.listview = listview;
        this.stopNumber = stopNumber;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader;
        String resultJSON;
        URL url = null;

        try {
            String linkText = context.getString(R.string.guardian_api_technology) + stopNumber;
            url = new URL(linkText);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;

            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            resultJSON = buffer.toString();
            fetchArticles(resultJSON);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        BusAdapter adapter = new BusAdapter((Activity) context, buses);
        listview = (ListView) listview.findViewById(R.id.articleList);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //openWebPage(buses.get(i).getmUrl());
            }
        });
    }

    private ArrayList<Bus> fetchArticles(String jsonString) throws JSONException {
        buses = new ArrayList<>();

        JSONObject root = new JSONObject(jsonString);
        JSONObject siri = root.optJSONObject("Siri");
        JSONObject serviceDelivery = siri.optJSONObject("ServiceDelivery");
        JSONArray stopMonitoringDelivery = serviceDelivery.optJSONArray("StopMonitoringDelivery");
        JSONObject stopMonitorObject = (JSONObject) stopMonitoringDelivery.get(0);
        JSONArray monitoredStopVisit = stopMonitorObject.optJSONArray("MonitoredStopVisit");

        for (int i = 0; i < monitoredStopVisit.length(); i++) {
            JSONObject monitoredVehicleJourney = (JSONObject) monitoredStopVisit.get(i);
            JSONObject vehicle = monitoredVehicleJourney.optJSONObject("MonitoredVehicleJourney");
            JSONObject monitoredCall = vehicle.optJSONObject("MonitoredCall");
            JSONObject extentions = monitoredCall.optJSONObject("Extensions");
            JSONObject distances = extentions.optJSONObject("Distances");

            String mLineRef = vehicle.optString("PublishedLineName");
            String mExpectedArrivalTime = monitoredCall.optString("ExpectedArrivalTime");
            String mDestinationName = vehicle.optString("DestinationName");
            String mPresentableDistance = distances.optString("PresentableDistance");
            String mProgressStatus = vehicle.optString("ProgressStatus");

            buses.add(new Bus(mLineRef, mExpectedArrivalTime, mDestinationName, mPresentableDistance, mProgressStatus));
        }

        return buses;
    }
    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }
}
