package com.example.vibtran.network;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.vibtran.ReceiverActivity;
import com.example.vibtran.ui.login.LoginViewModel;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NetRequest {
    private static String url = "http://10.2.6.45:8000/";
    private LoginViewModel loginViewModel;


    public static void sendRequest(Activity activity, String action, Map<String, String> params, NetRequestInterface callback) {

        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, (url+"/"+action)

                        .replaceAll("//","/").replaceAll(":/","://"), parameters, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        //Log.d("Response: ", response.toString());
                        callback.respones( response,null );
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        //Log.e("error","cannot send" +  error.toString());
                        callback.respones(null ,error);
                    }
                });
        // Access the RequestQueue through your singleton class.
        NetQueueSingle.getInstance(activity).addToRequestQueue(jsonObjectRequest);
    }
    public static void  register (Activity activity, String username, String uuid,String email, String phone, String password, Boolean isMerchant,NetRequestInterface callback){
        Log.d("info",  username+":"+ uuid+":"+  email+":"+ phone+":"+  password+":"+ isMerchant);
        Map<String, String> params = new HashMap();
        params.put("name", username );
        params.put("email", email);
        params.put("uuid", uuid);
        params.put("phone_num", phone);
        params.put("password", password);
        params.put("is_merchant",isMerchant?"1":"0");
        NetRequest.sendRequest(activity, "mr", params, callback);
    }

    public static void logout (Activity activity, String uuid){
        Map<String, String> params = new HashMap();
        params.put("uuid", uuid );
        NetRequest.sendRequest(activity, "ml", params, new NetRequestInterface() {
            @Override
            public void respones(JSONObject json, VolleyError error) {
                if(json != null ) {
                    Log.d("Success", json.toString() );
//                    loginViewModel.login(username,password);
                } else{
                Log.e("Error", error.toString() );
                }

            }

        });
    }

    public static void registerUUID (Activity activity, String uuid){
        Map<String, String> params = new HashMap();
        params.put("uuid", uuid );
        params.put("balance", "0");
        NetRequest.sendRequest(activity, "s", params, new NetRequestInterface() {
            @Override
            public void respones(JSONObject json, VolleyError error) {
                if(json != null ) {
                    Log.d("Success", json.toString() );
//                    loginViewModel.login(username,password);
                }

            }

        });
    }
    public static void getOtp (Activity activity, String uuid, String cost , NetRequestInterface callback){
        Map<String, String> params = new HashMap();
        params.put("uuid", uuid );
        params.put("cost", cost);
        NetRequest.sendRequest(activity, "vib",params ,callback);
    }

    public static void sendOtp (Activity activity, String uuid , String otp, NetRequestInterface callback){
        Map<String, String> params = new HashMap();
        params.put("uuid", uuid );
        params.put("otp", otp);
        NetRequest.sendRequest(activity, "tran", params, callback);

    }

    public static void confirmPay (Activity activity, String uuid , String otp, String cost, NetRequestInterface callback){
        Map<String, String> params = new HashMap();
        params.put("uuid", uuid );
        params.put("otp", otp);
        params.put("cost", cost);
        NetRequest.sendRequest(activity, "cPay", params, callback);
    }
    public static void cancelPay (Activity activity, String uuid , String otp, String cost, NetRequestInterface callback){
        Map<String, String> params = new HashMap();
        params.put("uuid", uuid );
        params.put("otp", otp);
        params.put("cost", cost);
        NetRequest.sendRequest(activity, "clPay", params, callback);
    }

    public static void getHistory (Activity activity, String uuid ,NetRequestInterface callback){
        Map<String, String> params = new HashMap();
        params.put("uuid", uuid );
        NetRequest.sendRequest(activity, "h", params,callback);
    }

    public static void getBalance (Activity activity, String uuid, NetRequestInterface callback){
        Map<String, String> params = new HashMap();
        params.put("uuid", uuid );
        NetRequest.sendRequest(activity, "b",params ,callback);
    }

    public static void checkPay (Activity activity, String uuid, String otp, NetRequestInterface callback){
        Map<String, String> params = new HashMap();
        params.put("uuid", uuid );
        params.put("otp", otp);
        NetRequest.sendRequest(activity, "check",params ,callback);
    }
}
