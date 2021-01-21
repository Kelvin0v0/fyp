package com.example.vibtran.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class NetRequest {
    private String url = "http://127.0.0.1";


    public void sendRequest(Context context) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response: ",response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.e("error","cannot send");

                    }
                });
        // Access the RequestQueue through your singleton class.
        NetQueueSingle.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }
}
