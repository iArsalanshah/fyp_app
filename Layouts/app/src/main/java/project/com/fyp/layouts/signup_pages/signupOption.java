package project.com.fyp.layouts.signup_pages;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import project.com.fyp.layouts.R;

public class signupOption extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_option);
        Button b = (Button) findViewById(R.id.btnSignupPassengerOption);
        Button c = (Button) findViewById(R.id.btnSignupDriverOption);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signupOption.this, signupFormPassenger.class));
            }
        });
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(signupOption.this,signupFormDriver.class));
            }
        });
    }


}
