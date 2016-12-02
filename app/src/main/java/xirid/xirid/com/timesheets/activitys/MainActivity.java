package xirid.xirid.com.timesheets.activitys;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;


import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import xirid.xirid.com.timesheets.fragments.MyTimesheets;
import xirid.xirid.com.timesheets.R;
import xirid.xirid.com.timesheets.interfaceses.CallFragment;
import xirid.xirid.com.timesheets.sessionmanagement.SessionManager;
import xirid.xirid.com.timesheets.fragments.TimeSheetsToApprove;
import xirid.xirid.com.timesheets.fragments.Weekly;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CallFragment {


    private SessionManager sessionManager;

    private Fragment fragment;

    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sessionManager = new SessionManager(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                OutputStream os = null;
//                InputStream is = null;
//                HttpURLConnection conn = null;
//                StringBuffer response = null;
//                try {
//                    //constants
//                    URL url = new URL("http://192.168.1.24:8069/mobile/login");
//                    JSONObject jsonObject = new JSONObject();
//                    jsonObject.put("user", "admin");
//                    jsonObject.put("passw", "reset123");
//
//                    String message = jsonObject.toString();
//
//                    conn = (HttpURLConnection) url.openConnection();
//                    conn.setReadTimeout(10000 /*milliseconds*/);
//                    conn.setConnectTimeout(15000 /* milliseconds */);
//                    conn.setRequestMethod("GET");
//                    conn.setDoInput(true);
//                    conn.setDoOutput(true);
//                    conn.setFixedLengthStreamingMode(message.getBytes().length);
//
//                    //make some HTTP header nicety
//                    conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
//                    conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");
//
//                    //open
//                    conn.connect();
//
//                    //setup send
//                    os = new BufferedOutputStream(conn.getOutputStream());
//                    os.write(message.getBytes());
//                    //clean up
//                    os.flush();
//
//                    //do somehting with response
//                    is = conn.getInputStream();
//
//                    int responseCode = conn.getResponseCode();
//                    System.out.println("responseCode" + responseCode);
//                    switch (responseCode) {
//                        case 200:
//                            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                            String inputLine;
//                            response = new StringBuffer();
//                            while ((inputLine = in.readLine()) != null) {
//                                response.append(inputLine);
//                            }
//                            in.close();
//                            Log.e("Response", "run: " + response.toString());
//                    }
//
//                    Log.e("Response", "run: " + is.toString());
//
////                    String contentAsString = readIt(is,len);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                } finally {
//                    //clean up
//                    try {
//                        os.close();
//                        is.close();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//
//                    conn.disconnect();
//                }
//            }
//        }).start();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

//        navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.setCheckedItem(R.id.weekly);
        onNavigationItemSelected(navigationView.getMenu().findItem(R.id.weekly));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.weekly) {
            // Handle the camera action
        } else if (id == R.id.mytimesheets) {

        } else if (id == R.id.timesheetstoapprove) {

        } else if (id == R.id.logout) {
            sessionManager.logoutUser();
        }


        addFragment(id);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void addFragment(int id) {


        switch (id) {
            case R.id.weekly:
                fragment = new Weekly();

                break;
            case R.id.mytimesheets:
                fragment = new MyTimesheets();
                break;
            case R.id.timesheetstoapprove:
                fragment = new TimeSheetsToApprove();
                break;

        }

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        if (fragment.isAdded() && (fragment instanceof Weekly || fragment instanceof MyTimesheets || fragment instanceof TimeSheetsToApprove)) {
            fragmentTransaction.show(fragment);
        } else {
            fragmentTransaction.replace(R.id.container, fragment);
        }

        fragmentTransaction.commit();

    }

    @Override
    public void callFragment(int id) {
        navigationView.setCheckedItem(id);
        onNavigationItemSelected(navigationView.getMenu().findItem(id));
    }
}
