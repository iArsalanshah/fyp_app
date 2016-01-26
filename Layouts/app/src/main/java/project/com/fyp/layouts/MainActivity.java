package project.com.fyp.layouts;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import project.com.fyp.layouts.login_pages.login;
import project.com.fyp.layouts.signup_pages.signupOption;

public class MainActivity extends AppCompatActivity  {

    private static final int ERROR_DIALOG_REQUEST = 9001;

    Button login_page;
    Button signup_page;
    public static RadioButton radioButton1;
    public RadioButton radioButton2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);
        initialization();
        onClickEvents();

        if (servicesOK()){
            Toast.makeText(this, "Play Services OK", Toast.LENGTH_SHORT).show();
        }
    }

    private void onClickEvents() {
        login_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, login.class);
                startActivity(i);
            }
        });
        signup_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, signupOption.class);
                startActivity(intent);
            }
        });
        if (radioButton1.isChecked()){
            Intent i = new Intent();
        }
        else{
            Intent q = new Intent();
        }
    }

    private void initialization() {
        login_page = (Button) findViewById(R.id.buttonLogin);
        signup_page = (Button) findViewById(R.id.buttonSignUp);
        radioButton1 = (RadioButton) findViewById(R.id.radioButtonPassenger);
        radioButton2 = (RadioButton) findViewById(R.id.radioButtonDriver);
    }

    private boolean servicesOK() {
        GoogleApiAvailability api = GoogleApiAvailability.getInstance();
        int isAvailable = api.isGooglePlayServicesAvailable(this);

        if (isAvailable == ConnectionResult.SUCCESS) {
                return true;
        } else if (api.isUserResolvableError(isAvailable)) {
            Dialog dialog = api.getErrorDialog(this,isAvailable,ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            String str = GoogleApiAvailability.getInstance().getErrorString(isAvailable);
            Toast.makeText(this, str, Toast.LENGTH_LONG).show();
        }
        return false;
    }


}
