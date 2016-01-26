package project.com.fyp.layouts;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;
import java.util.Map;

import project.com.fyp.layouts.login_pages.login;
import project.com.fyp.layouts.passenger_pages.map_urgent_show;

public class map_option extends AppCompatActivity {
login l;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_option_layout);
        l = new login();
        Button urgentMapOpt = (Button) findViewById(R.id.btnUrgentMapOpt);
        Button eventBookingOpt = (Button) findViewById(R.id.btnEventBookingOpt);
        urgentMapOpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(map_option.this, map_urgent_show.class));
            }
        });
    }
    @Override
    protected void onResume(){
        super.onResume();
        if(MainActivity.radioButton1.isChecked()){
            //isOnline updation Passenger
            l.is_Online = true;
            Map<String,Object> isOnlineUpdate = new HashMap<String, Object>();
            isOnlineUpdate.put("isOnline", l.is_Online);
            l.ref.child("passengers").child(l.UID).updateChildren(isOnlineUpdate);
        }
        else {
            //isOnline updation Driver
            l.is_Online = true;
            Map<String,Object> isOnlineUpdate = new HashMap<String, Object>();
            isOnlineUpdate.put("isOnline", l.is_Online);
            l.ref.child("drivers").child(l.UID).updateChildren(isOnlineUpdate);
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                            moveTaskToBack(true); // exist app
                            map_option.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(MainActivity.radioButton1.isChecked()) {
            login.is_Online = false;
            boolean val = login.is_Online;
            Map<String, Object> update = new HashMap<String, Object>();
            update.put("isOnline", val);
            login.ref.child("passengers").child(login.UID).updateChildren(update);
        }else{
            //isOnline updation Driver
            l.is_Online = false;
            Map<String,Object> isOnlineUpdate = new HashMap<String, Object>();
            isOnlineUpdate.put("isOnline", l.is_Online);
            l.ref.child("drivers").child(l.UID).updateChildren(isOnlineUpdate);
        }
    }
}
