package project.com.fyp.layouts.login_pages;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import project.com.fyp.layouts.R;
import project.com.fyp.layouts.map_option;
import project.com.fyp.layouts.signup_pages.signupOption;

public class login extends AppCompatActivity {
    public static Firebase ref; Button loginButton; TextView tv; String email; String pwd;EditText etemail;EditText etpwd;
    public static String UID;
    public static Boolean is_Online = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ref = new Firebase("https://fypeasytravel.firebaseio.com");
        initialization();
        clickEvents();
    }

    private void initialization() {
        loginButton = (Button) findViewById(R.id.buttonLoginNext);
        tv = (TextView) findViewById(R.id.tvCreate);
        etemail = (EditText) findViewById(R.id.editTextemailLogin);
        etpwd = (EditText) findViewById(R.id.editTextPwdLogin);
    }
    private void clickEvents() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(login.this,map_option.class));
                email = etemail.getText().toString();
                pwd = etpwd.getText().toString();
                ref.authWithPassword(email, pwd, new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        UID = authData.getUid();
                        Toast.makeText(login.this, "Success" + UID, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(login.this, map_option.class));
                    }
                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        Toast.makeText(login.this, "" + firebaseError, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tv.setTextColor(ContextCompat.getColor(login.this,R.color.colorThemeYellow));
                startActivity(new Intent(login.this, signupOption.class));
            }
        });
    }
}
