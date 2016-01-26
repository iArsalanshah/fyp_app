package project.com.fyp.layouts.signup_pages;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

import project.com.fyp.layouts.R;
import project.com.fyp.layouts.login_pages.login;

public class signupFormDriver extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Spinner spTown; login l;
    Spinner spUc; Firebase mRef;
    Button btnSubmit; EditText etuname;EditText etemail;EditText etpwd;EditText etRpwd; EditText etcell;
    String[] empty = {""}; String em = ""; String UID_driver;
    String uname; String email; String pwd; String rpwd; String cell; String townName; String ucName;
    ArrayAdapter<String> empty_adapter;
    ArrayAdapter<CharSequence> adapter_uc_baldia;
    ArrayAdapter<CharSequence> adapter_uc_bin_qasim;
    ArrayAdapter<CharSequence> adapter_uc_gadap;
    ArrayAdapter<CharSequence> adapter_uc_gulberg;
    ArrayAdapter<CharSequence> adapter_uc_gulshan;
    ArrayAdapter<CharSequence> adapter_uc_jamshed;
    ArrayAdapter<CharSequence> adapter_uc_kemari;
    ArrayAdapter<CharSequence> adapter_uc_korangi;
    ArrayAdapter<CharSequence> adapter_uc_landhi;
    ArrayAdapter<CharSequence> adapter_uc_liaquatabad;
    ArrayAdapter<CharSequence> adapter_uc_lyari;
    ArrayAdapter<CharSequence> adapter_uc_malir;
    ArrayAdapter<CharSequence> adapter_uc_new_karachi;
    ArrayAdapter<CharSequence> adapter_uc_north_nazimabad;
    ArrayAdapter<CharSequence> adapter_uc_orangi;
    ArrayAdapter<CharSequence> adapter_uc_saddar;
    ArrayAdapter<CharSequence> adapter_uc_shah_faisal;
    ArrayAdapter<CharSequence> adapter_uc_SITE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signupformdriver);
        initialization();
        adapterdata();
        spTown.setOnItemSelectedListener(this);
        buttonEvents();
        l = new login();
        mRef = new Firebase("https://fypeasytravel.firebaseio.com/");
    }

    private void buttonEvents() {
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assignment();
                if (!uname.equals(em) && !email.equals(em) && !pwd.equals(em) && !rpwd.equals(em) && !cell.equals(em) && !townName.contentEquals(em) && !ucName.contentEquals(em)) {
                    if (pwd.equals(rpwd)) {
                        mRef.createUser(email, pwd, new Firebase.ValueResultHandler<Map<String, Object>>() {
                            @Override
                            public void onSuccess(Map<String, Object> stringObjectMap) {
                                UID_driver = (String) stringObjectMap.get("uid");
                                Toast.makeText(signupFormDriver.this, "Submitted", Toast.LENGTH_SHORT).show();
                                //Signup Data Node
                                Map<String, Object> map = new HashMap<String, Object>();
                                map.put("signupData/username", uname);
                                map.put("signupData/email", email);
                                map.put("signupData/pwd", pwd);
                                map.put("signupData/cell", cell);
                                mRef.child("drivers").child(UID_driver).updateChildren(map);
                                //is user Online Node
                                Map<String, Object> onlineUpdate = new HashMap<String, Object>();
                                onlineUpdate.put("isOnline", l.is_Online);
                                mRef.child("drivers").child(UID_driver).updateChildren(onlineUpdate);
                                //Stand Area Update
                                Map<String, Object> standUpdate = new HashMap<String, Object>();
                                standUpdate.put("standArea/town", townName);
                                standUpdate.put("standArea/uc", ucName);
                                mRef.child("drivers").child(UID_driver).updateChildren(standUpdate);
                            }

                            @Override
                            public void onError(FirebaseError firebaseError) {
                                Toast.makeText(signupFormDriver.this, "" + firebaseError, Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(signupFormDriver.this, "Password Not Match", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(signupFormDriver.this, "Please Submit All Fields", Toast.LENGTH_SHORT).show();
                }
            }
            private void assignment() {
                townName = spTown.getSelectedItem().toString();
                ucName = spUc.getSelectedItem().toString();
                uname = etuname.getText().toString();
                email = etemail.getText().toString();
                pwd = etpwd.getText().toString();
                rpwd = etRpwd.getText().toString();
                cell = etcell.getText().toString();
            }
        });
    }

    private void adapterdata() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.town_names, android.R.layout.simple_spinner_dropdown_item);
        spTown.setAdapter(adapter);
        empty_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,empty);
        adapter_uc_baldia = ArrayAdapter.createFromResource(this,
               R.array.uc_Baldia,android.R.layout.simple_spinner_dropdown_item);
       adapter_uc_bin_qasim = ArrayAdapter.createFromResource(this,
               R.array.uc_bin_qasim,android.R.layout.simple_spinner_dropdown_item);
       adapter_uc_gadap = ArrayAdapter.createFromResource(this,
               R.array.uc_gadap,android.R.layout.simple_spinner_dropdown_item);
       adapter_uc_gulberg = ArrayAdapter.createFromResource(this,
               R.array.uc_gulberg,android.R.layout.simple_spinner_dropdown_item);
       adapter_uc_gulshan = ArrayAdapter.createFromResource(this,
               R.array.uc_gulshan,android.R.layout.simple_spinner_dropdown_item);
       adapter_uc_jamshed = ArrayAdapter.createFromResource(this,
               R.array.uc_jamshed,android.R.layout.simple_spinner_dropdown_item);
       adapter_uc_kemari = ArrayAdapter.createFromResource(this,
               R.array.uc_kemari,android.R.layout.simple_spinner_dropdown_item);
       adapter_uc_korangi = ArrayAdapter.createFromResource(this,
               R.array.uc_korangi,android.R.layout.simple_spinner_dropdown_item);
       adapter_uc_landhi = ArrayAdapter.createFromResource(this,
               R.array.uc_landhi,android.R.layout.simple_spinner_dropdown_item);
       adapter_uc_liaquatabad = ArrayAdapter.createFromResource(this,
               R.array.uc_liaquatabad,android.R.layout.simple_spinner_dropdown_item);
       adapter_uc_lyari = ArrayAdapter.createFromResource(this,
               R.array.uc_lyari,android.R.layout.simple_spinner_dropdown_item);
       adapter_uc_malir = ArrayAdapter.createFromResource(this,
               R.array.uc_malir,android.R.layout.simple_spinner_dropdown_item);
       adapter_uc_new_karachi = ArrayAdapter.createFromResource(this,
               R.array.uc_new_karachi,android.R.layout.simple_spinner_dropdown_item);
       adapter_uc_north_nazimabad = ArrayAdapter.createFromResource(this,
               R.array.uc_north_nazimabad,android.R.layout.simple_spinner_dropdown_item);
       adapter_uc_orangi = ArrayAdapter.createFromResource(this,
               R.array.uc_orangi,android.R.layout.simple_spinner_dropdown_item);
       adapter_uc_saddar = ArrayAdapter.createFromResource(this,
               R.array.uc_saddar,android.R.layout.simple_spinner_dropdown_item);
       adapter_uc_shah_faisal = ArrayAdapter.createFromResource(this,
               R.array.uc_shah_faisal,android.R.layout.simple_spinner_dropdown_item);
       adapter_uc_SITE = ArrayAdapter.createFromResource(this,
               R.array.uc_SITE,android.R.layout.simple_spinner_dropdown_item);

    }

    private void initialization() {
        spTown = (Spinner) findViewById(R.id.spinnerTown);
        spUc = (Spinner) findViewById(R.id.spinnerUC);
        btnSubmit = (Button) findViewById(R.id.btnSignUpFormDriver);
         etuname = (EditText) findViewById(R.id.editTextUsernameDriver);
         etemail = (EditText) findViewById(R.id.editTextemailDriver);
         etpwd = (EditText) findViewById(R.id.editTextpwdDriver);
         etRpwd = (EditText) findViewById(R.id.editTextRepwdDriver);
         etcell = (EditText) findViewById(R.id.editTextcellNumberDriv);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                spUc.setAdapter(empty_adapter);
                break;
            case 1:
                spUc.setAdapter(adapter_uc_baldia);
                break;
            case 2:
                spUc.setAdapter(adapter_uc_bin_qasim);
                break;
            case 3:
                spUc.setAdapter(adapter_uc_gadap);
                break;
            case 4:
                spUc.setAdapter(adapter_uc_gulberg);
                break;
            case 5:
                spUc.setAdapter(adapter_uc_gulshan);
                break;
            case 6:
                spUc.setAdapter(adapter_uc_jamshed);
                break;
            case 7:
                spUc.setAdapter(adapter_uc_kemari);
                break;
            case 8:
                spUc.setAdapter(adapter_uc_korangi);
                break;
            case 9:
                spUc.setAdapter(adapter_uc_landhi);
                break;
            case 10:
                spUc.setAdapter(adapter_uc_liaquatabad);
                break;
            case 11:
                spUc.setAdapter(adapter_uc_lyari);
                break;
            case 12:
                spUc.setAdapter(adapter_uc_malir);
                break;
            case 13:
                spUc.setAdapter(adapter_uc_new_karachi);
                break;
            case 14:
                spUc.setAdapter(adapter_uc_north_nazimabad);
                break;
            case 15:
                spUc.setAdapter(adapter_uc_orangi);
                break;
            case 16:
                spUc.setAdapter(adapter_uc_saddar);
                break;
            case 17:
                spUc.setAdapter(adapter_uc_shah_faisal);
                break;
            case 18:
                spUc.setAdapter(adapter_uc_SITE);
                break;
            default:
                Toast.makeText(this,"Please Select a Town",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
