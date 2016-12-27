package com.pappaya.prms.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.pappaya.prms.sessionmanagement.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by yasar on 25/12/16.
 */
public class JsonRequest {


    public static void makeRequest(Context context, JSONObject jsonObject, String method, final RequestCallback requestCallback) {
//        final RequestCallback requestCallback = (RequestCallback) context;
//        JSONObject params = new JSONObject();
//        try {
//
//            params.put("db", "pappaya");
//            params.put("login", username);
//            params.put("password", password);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        JSONObject jsonObject = new JSONObject();
//
//
//        try {
//            jsonObject.put("jsonrpc", "2.0");
//            jsonObject.put("method", "call");
//            jsonObject.put("id", "2");
//            jsonObject.put("params", params);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, Cons.url + method,
                jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                requestCallback.onSuccess(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                requestCallback.OnFail(error);
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjReq);
    }


    public interface RequestCallback {
        void onSuccess(JSONObject response);

        void OnFail(VolleyError error);
    }

}
