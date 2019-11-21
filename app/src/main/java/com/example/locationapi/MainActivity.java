package com.example.locationapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;

public class MainActivity extends AppCompatActivity {

    TextView txtViewLocation;
    Button buttonMaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonMaps = (Button) findViewById(R.id.buttonMaps);
        buttonMaps.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, MapsActivity.class));
                    }
        });


        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)==
        PackageManager.PERMISSION_GRANTED) {
            FusedLocationProviderClient fusedLocationProviderClient = new FusedLocationProviderClient(this);

            fusedLocationProviderClient.requestLocationUpdates(mLocationRequest,
                    new LocationCallback() {

                        public void onLocationResult(LocationResult locationResult) {
                            super.onLocationResult(locationResult);
                            double lat = locationResult.getLastLocation().getLatitude();
                            double lng = locationResult.getLastLocation().getLongitude();
                            txtViewLocation = (TextView) findViewById(R.id.textViewLocation);
                            txtViewLocation.setText("Coordinate: " + String.valueOf(lat) + ", " + String.valueOf(lng));
                        }
                    }
                    ,getMainLooper());
        }else{//request permission
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
        }
        }


}

//
