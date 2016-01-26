package project.com.fyp.layouts.signup_pages;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

import project.com.fyp.layouts.R;
import project.com.fyp.layouts.login_pages.login;
import project.com.fyp.layouts.passenger_pages.map_urgent_show;

public class signupFormPassenger extends AppCompatActivity {
login l; map_urgent_show m; String empty = "";
    EditText etUsername; EditText etEmail; EditText etPwd; EditText etRePwd; EditText etCellNo; Button btnSignUp;
    Firebase mFRoot;
    Firebase passenger_ref;
    String UID_passenger;
    String uname; String email; String pwd; String rpwd; String cell;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signupformpassenger);
        mFRoot = new Firebase("https://fypeasytravel.firebaseio.com/");
        passenger_ref =mFRoot.child("passengers");
        initialization();
        buttonEvents();
        l = new login();
        m = new map_urgent_show();
    }

    private void initialization() {
         etUsername = (EditText) findViewById(R.id.editTextuserNamePassenger);
         etEmail = (EditText) findViewById(R.id.editTextemailPass);
         etPwd = (EditText) findViewById(R.id.editTextpwdPass);
         etRePwd = (EditText) findViewById(R.id.editTextRepwdPassenger);
         etCellNo = (EditText) findViewById(R.id.editTextcellNumberPass);
         btnSignUp = (Button) findViewById(R.id.btnSignUpPass);
    }
    private void buttonEvents() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assignment();
                if (!pwd.equals(empty)&&!rpwd.equals(empty)&&!cell.equals(empty)&&!uname.equals(empty)){
                    if (pwd.equals(rpwd)){
                        mFRoot.createUser(email, pwd, new Firebase.ValueResultHandler<Map<String, Object>>() {
                            @Override
                            public void onSuccess(Map<String, Object> stringObjectMap) {
                                UID_passenger = (String) stringObjectMap.get("uid");
                                Toast.makeText(signupFormPassenger.this, "Success "+ UID_passenger, Toast.LENGTH_SHORT).show();
                                //Signup Data Node
                                Map<String,Object> map = new HashMap<String, Object>();
                                map.put("signupData/username",uname);
                                map.put("signupData/email", email);
                                map.put("signupData/pwd", pwd);
                                map.put("signupData/cell", cell);
                                passenger_ref.child(UID_passenger).updateChildren(map);
                                //is user Online Node
                                Map<String,Object> onlineUpdate = new HashMap<String, Object>();
                                onlineUpdate.put("isOnline",l.is_Online);
                                passenger_ref.child(UID_passenger).updateChildren(onlineUpdate);
                                //Location Node Creation
                                Map<String,Object> locationUpdate = new HashMap<String, Object>();
                                locationUpdate.put("location/lat",m.latCurrent);
                                locationUpdate.put("location/lon",m.lonCurrent);
                                passenger_ref.child(UID_passenger).updateChildren(locationUpdate);
                            }
                            @Override
                            public void onError(FirebaseError firebaseError) {
                                Toast.makeText(signupFormPassenger.this, ""+firebaseError, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }   else{
                        Toast.makeText(signupFormPassenger.this, "pwd not matched", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(signupFormPassenger.this, "Please Enter all fields", Toast.LENGTH_SHORT).show();
                }
            }
            private void assignment() {
                email = etEmail.getText().toString();
                pwd =etPwd.getText().toString();
                rpwd =etRePwd.getText().toString();
                uname = etUsername.getText().toString();
                cell = etCellNo.getText().toString();
            }
        });
    }

}
