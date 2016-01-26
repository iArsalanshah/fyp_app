package project.com.fyp.layouts.passenger_pages;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import project.com.fyp.layouts.R;
import project.com.fyp.layouts.util.Tracker;

public class destination_Selection_pass extends AppCompatActivity implements OnMapReadyCallback {
    private Tracker tracker;
    GoogleMap mMap;
    private static final double KARACHI_LAT = 24.8600,KARACHI_LON =67.0100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination_pass);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapSearch);
        mapFragment.getMapAsync(this);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                mMap = googleMap;
            }
        });
        enableTracking();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng latLng = new LatLng(KARACHI_LAT,KARACHI_LON);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.addMarker(new MarkerOptions().position(latLng).title("Destination"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
    }

    private void enableTracking() {
        tracker = new Tracker(this);
        if(tracker.isCanGetLocation()){
        }
        else{
            tracker.settingGPS();
        }
    }
    private void hideSoftKeyboard(View v){
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public void geoLocate(View v) throws IOException {

        hideSoftKeyboard(v);
        EditText editText = (EditText) findViewById(R.id.editTextSearchDestin);
        String searchString = editText.getText().toString();

        Geocoder geocoder = new Geocoder(this);
        List<Address> list = geocoder.getFromLocationName(searchString, 1);
        if (list.size() >0){
            Address add = list.get(0);
            String locality = add.getLocality();
            Toast.makeText(this,"Found:"+locality,Toast.LENGTH_SHORT).show();
            double lat = add.getLatitude();
            double lng = add.getLongitude();
            gotoLocation(lat, lng, 15);
        }
        else {
            Toast.makeText(this,"Please Enter Search Address",Toast.LENGTH_SHORT).show();
        }
    }

    private void gotoLocation(double lat, double lon,float zoom) {
        LatLng latlong = new LatLng(lat,lon);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latlong, zoom);
        mMap.animateCamera(cameraUpdate);
    }
}
