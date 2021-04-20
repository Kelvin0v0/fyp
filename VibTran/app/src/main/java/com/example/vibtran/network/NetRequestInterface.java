package com.example.vibtran.network;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface NetRequestInterface {
    public void respones(JSONObject json,VolleyError error);
}
