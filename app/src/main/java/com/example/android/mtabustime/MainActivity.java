package com.example.android.mtabustime;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.yayandroid.locationmanager.LocationBaseActivity;
import com.yayandroid.locationmanager.LocationConfiguration;
import com.yayandroid.locationmanager.LocationManager;
import com.yayandroid.locationmanager.constants.FailType;
import com.yayandroid.locationmanager.constants.LogType;
import com.yayandroid.locationmanager.constants.ProviderType;


public class MainActivity extends LocationBaseActivity {

    private ProgressDialog progressDialog;
    private TextView locationText;
    private double longitude;
    private double latitude;


    @Override
    public LocationConfiguration getLocationConfiguration() {
        return new LocationConfiguration()
                .keepTracking(false)
                .askForGooglePlayServices(true)
                .setMinAccuracy(200.0f)
                .setWaitPeriod(ProviderType.GOOGLE_PLAY_SERVICES, 5 * 1000)
                .setWaitPeriod(ProviderType.GPS, 10 * 1000)
                .setWaitPeriod(ProviderType.NETWORK, 5 * 1000)
                .setGPSMessage("Would you mind to turn GPS on?")
                .setRationalMessage("Gimme the permission!");
    }

    @Override
    public void onLocationFailed(int failType) {
        dismissProgress();

        switch (failType) {
            case FailType.PERMISSION_DENIED: {
                locationText.setText("Couldn't get location, because user didn't give permission!");
                break;
            }
            case FailType.GP_SERVICES_NOT_AVAILABLE:
            case FailType.GP_SERVICES_CONNECTION_FAIL: {
                locationText.setText("Couldn't get location, because Google Play Services not available!");
                break;
            }
            case FailType.NETWORK_NOT_AVAILABLE: {
                locationText.setText("Couldn't get location, because network is not accessible!");
                break;
            }
            case FailType.TIMEOUT: {
                locationText.setText("Couldn't get location, and timeout!");
                break;
            }
            case FailType.GP_SERVICES_SETTINGS_DENIED: {
                locationText.setText("Couldn't get location, because user didn't activate providers via settingsApi!");
                break;
            }
            case FailType.GP_SERVICES_SETTINGS_DIALOG: {
                locationText.setText("Couldn't display settingsApi dialog!");
                break;
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        dismissProgress();
        setText(location);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button showBusTimes = (Button) findViewById(R.id.displayBusTimes);
        showBusTimes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent busTimeIntent = new Intent(MainActivity.this, BusTimesActivity.class);
                startActivity(busTimeIntent);
            }
        });

        locationText = (TextView) findViewById(R.id.locationText);

        LocationManager.setLogType(LogType.GENERAL);
        getLocation();

    }

    private void dismissProgress() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void setText(Location location) {
        String appendValue = "Lat:" + location.getLatitude() + ", Lon: " + location.getLongitude() + "\n";
        String newValue;
        CharSequence current = locationText.getText();

        if (!TextUtils.isEmpty(current)) {
            newValue = current + appendValue;
        } else {
            newValue = appendValue;
        }

        latitude = location.getLatitude();
        longitude = location.getLongitude();

        locationText.setText(newValue);

        Log.v("Longitude", String.valueOf(longitude));
        Log.v("Latitude", String.valueOf(latitude));

    }

    private void displayProgress() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.getWindow().addFlags(Window.FEATURE_NO_TITLE);
            progressDialog.setMessage("Getting location...");
        }

        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }


}
