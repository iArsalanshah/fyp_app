package project.com.fyp.layouts.passenger_pages;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.geofire.GeoFire;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.Map;

import project.com.fyp.layouts.R;
import project.com.fyp.layouts.login_pages.login;
import project.com.fyp.layouts.util.Tracker;

public class map_urgent_show extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    public double latCurrent;
    public double lonCurrent;
    private Tracker tracker;
    Firebase mRef;
    GeoFire mGeo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_urgent_pass_layout);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapUrgent);
        mapFragment.getMapAsync(this);
        mRef = new Firebase("https://fypeasytravel.firebaseio.com");
        mGeo = new GeoFire(mRef);
        Button backButton = (Button) findViewById(R.id.backButtonID);
        Button reqButton = (Button) findViewById(R.id.requestButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        reqButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(map_urgent_show.this,destination_Selection_pass.class));
            }
        });
    }

    private void enableTracking() {
        tracker = new Tracker(this);
        if(tracker.isCanGetLocation()){
            latCurrent = tracker.getLat();
            lonCurrent = tracker.getLng();
            Toast.makeText(this, "Your Location is - \nLat: " + latCurrent + "\nLong: " + lonCurrent, Toast.LENGTH_LONG).show();
            Map<String,Object> update = new HashMap<String, Object>();
            update.put("location/lat",latCurrent);
            update.put("location/lon",lonCurrent);
            login.ref.child("passengers").child(login.UID).updateChildren(update);
        }
        else{
            tracker.settingGPS();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        enableTracking();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tracker.stopGPS();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
       /* LatLng sydney = new LatLng(latCurrent, lonCurrent);
        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker at my Place"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/

        LatLng latLng = new LatLng(latCurrent,lonCurrent);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.addMarker(new MarkerOptions().position(latLng).title("Hi You are Here"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));

        CameraPosition cameraPosition = CameraPosition.builder()
                .target(latLng)
                .zoom(15)
                .bearing(90)
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition),
                2000, null);
    }
}
