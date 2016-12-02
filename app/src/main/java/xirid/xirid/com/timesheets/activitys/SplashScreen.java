package xirid.xirid.com.timesheets.activitys;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import xirid.xirid.com.timesheets.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Intent in = new Intent(SplashScreen.this, LoginActivity.class);
        startActivity(in);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                Intent in = new Intent(SplashScreen.this, LoginActivity.class);
//                startActivity(in);
//
//            }
//        }, 2000);

    }
}
