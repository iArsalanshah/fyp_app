package project.com.fyp.layouts.login_pages;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;

import project.com.fyp.layouts.R;

public class splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Thread timer = new Thread(){
            public void run(){
                try {
                    sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();

                }finally {
                    Intent i = new Intent("project.com.fyp.layouts.MAINACTIVITY");
                    startActivity(i);
                }
            }
        };
        timer.start();
    }
}
