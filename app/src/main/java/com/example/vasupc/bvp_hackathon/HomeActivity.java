package com.example.vasupc.bvp_hackathon;

import android.*;
import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.model.LatLng;

public class HomeActivity extends AppCompatActivity implements LocationListener {

    LatLng myPosition;
    Location location;
    LocationManager locationManager;
    private  TextView tvLatitude, tvLongitude;
    private String latitude_value = "";
    private String longitude_value = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tvLatitude = (TextView) findViewById(R.id.tvLatitude);
        tvLongitude = (TextView) findViewById(R.id.tvLongitude);

        onMapReady();

        if (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        double latitude ;
        double longitude;
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if(location!= null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
        else {
            Toast.makeText(HomeActivity.this, "Please select your location.", Toast.LENGTH_SHORT).show();
            return;

        }

        latitude_value = Double.toString(latitude);
        longitude_value = Double.toString(longitude);


    }

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(this, ""+location.getLatitude(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

public void onMapReady() {

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Creating a criteria object to retrieve provider
        Criteria criteria = new Criteria();


        // Getting the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Getting Current Location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        // TODO: Consider calling;
        return;
        }

        location = locationManager.getLastKnownLocation(provider);


        }

        }
