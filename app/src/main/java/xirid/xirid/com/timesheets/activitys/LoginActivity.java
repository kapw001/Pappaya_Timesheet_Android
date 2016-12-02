package xirid.xirid.com.timesheets.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import xirid.xirid.com.timesheets.R;
import xirid.xirid.com.timesheets.sessionmanagement.SessionManager;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "LoginActivity";

    private EditText userName, passWord;
    private Button login;

    private SessionManager sessionManager;

    private static final String url = "http://192.168.1.34/odoo/login.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);

        userName = (EditText) findViewById(R.id.email);
        passWord = (EditText) findViewById(R.id.password);

        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);
        Intent in = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(in);
        finish();


//        userName.setOnClickListener(this);
//        passWord.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.login:
                login();
                break;
        }
    }

    private void login() {
        final String email = userName.getText().toString();
        final String password = passWord.getText().toString();

        if (email.length() > 0 && password.length() > 0) {
//            Toast.makeText(LoginActivity.this, "Ok", Toast.LENGTH_LONG).show();

//            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
//                    url, null,
//                    new Response.Listener<JSONObject>() {
//
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            Log.e(TAG, response.toString());
//                            try {
//                                Toast.makeText(LoginActivity.this, response.getString("uid").toString(), Toast.LENGTH_LONG).show();
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    VolleyLog.e(TAG, "Error: " + error.getMessage());
//                    Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_LONG).show();
//
//                }
//            }) {
//
//                @Override
//                protected Map<String, String> getParams() {
//                    Map<String, String> params = new HashMap<String, String>();
//                    params.put("username", email);
//                    params.put("password", password);
//                    return params;
//                }
//
//            };

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String status = jsonObject.getString("status");

                                if (status != null && status.equalsIgnoreCase("success")) {
                                    String uid = jsonObject.getString("uid");
                                    Toast.makeText(LoginActivity.this, uid, Toast.LENGTH_LONG).show();

                                    sessionManager.createLoginSession(email, password);

                                    Intent in = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(in);
                                    finish();

                                } else {
                                    Toast.makeText(LoginActivity.this, status, Toast.LENGTH_LONG).show();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("username", email);
                    params.put("password", password);

                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }

    }
}

//
//    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
//            new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//
//                    Log.e(TAG, "onResponse: " + response);
//
////                            try {
////                                JSONObject jsonObject = new JSONObject(response);
////                                String status = jsonObject.getString("status");
////
////                                if (status != null && status.equalsIgnoreCase("success")) {
////                                    String uid = jsonObject.getString("uid");
////                                    Toast.makeText(LoginActivity.this, uid, Toast.LENGTH_LONG).show();
////
////                                    Intent in = new Intent(LoginActivity.this, MainActivity.class);
////                                    startActivity(in);
////                                    finish();
////
////                                } else {
////                                    Toast.makeText(LoginActivity.this, status, Toast.LENGTH_LONG).show();
////                                }
////
////
////                            } catch (JSONException e) {
////                                e.printStackTrace();
////                            }
//
//
//                }
//            },
//            new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Log.e(TAG, "onErrorResponse: " + error.toString());
//                    Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_LONG).show();
//                }
//            }) {
//        @Override
//        protected Map<String, String> getParams() {
//            Map<String, String> params = new HashMap<String, String>();
//            params.put("db", "spectromax");
//            params.put("database", "spectromax");
//            params.put("key", "admin");
//            params.put("login", "admin");
//            params.put("password", "admin");
//
//            return params;
//        }
//
//    };
