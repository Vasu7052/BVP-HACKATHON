package com.example.vasupc.bvp_hackathon;

import android.*;
import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity implements LocationListener {

    private LocationManager locationManager;
    private Location location;
    private final int REQUEST_LOCATION = 200;
    private static final String TAG = "MainActivity";
    ListView lvNearby ;
    ArrayList<String> nearbyList = new ArrayList<>();
    Double myLat = 0.0 , myLng = 0.0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        lvNearby =  findViewById(R.id.lvNearby);

        locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 2, this);
            if (locationManager != null) {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
        }
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (location != null) {
                //latitudePosition.setText(String.valueOf(location.getLatitude()));
                //longitudePosition.setText(String.valueOf(location.getLongitude()));
                getAddressFromLocation(location, getApplicationContext(), new GeoCoderHandler());
            }
        } else {
            showGPSDisabledAlertToUser();
        }
    }
    @Override
    public void onLocationChanged(Location location) {

        Toast.makeText(this, "Location Changed", Toast.LENGTH_SHORT).show();

        myLat = location.getLatitude();
        myLng = location.getLongitude();

        get_all_people();

        getAddressFromLocation(location, getApplicationContext(), new GeoCoderHandler());
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
    @Override
    public void onProviderEnabled(String provider) {
    }
    @Override
    public void onProviderDisabled(String provider) {
        if (provider.equals(LocationManager.GPS_PROVIDER)) {
            showGPSDisabledAlertToUser();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            }
        }
    }
    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(callGPSSettingIntent);
                    }
                });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }
    public static void getAddressFromLocation(final Location location, final Context context, final Handler handler) {
        Thread thread = new Thread() {
            @Override public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                String result = null;
                try {
                    List<Address> list = geocoder.getFromLocation(
                            location.getLatitude(), location.getLongitude(), 1);
                    if (list != null && list.size() > 0) {
                        Address address = list.get(0);
                        // sending back first address line and locality
                        result = address.getAddressLine(0) + ", " + address.getLocality() + ", " +  address.getCountryName() ;
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Impossible to connect to Geocoder", e);
                } finally {
                    Message msg = Message.obtain();
                    msg.setTarget(handler);
                    if (result != null) {
                        msg.what = 1;
                        Bundle bundle = new Bundle();
                        bundle.putString("address", result);
                        msg.setData(bundle);
                    } else
                        msg.what = 0;
                    msg.sendToTarget();
                }
            }
        };
        thread.start();
    }
    private class GeoCoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String result;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    result = bundle.getString("address");
                    break;
                default:
                    result = null;
            }
            //currentCity.setText(result);
        }
    }

    public void get_all_people(){

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("UserLocation");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nearbyList.clear();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    float[] dist = new float[2];

                    double lat = (Double) ds.child("lat").getValue();
                    double lng = (Double) ds.child("lng").getValue();

                    Location.distanceBetween(myLat,myLng,lat,lng,dist);

                    if (dist[0] < 1000){
                        nearbyList.add(ds.getKey());
                    }
                }

                ArrayAdapter adapter = new ArrayAdapter(HomeActivity.this, android.R.layout.simple_list_item_1, nearbyList);
                lvNearby.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


}