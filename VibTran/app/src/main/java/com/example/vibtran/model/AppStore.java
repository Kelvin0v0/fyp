package com.example.vibtran.model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.vibtran.network.NetRequest;

import java.util.UUID;

public class AppStore {

    public static String getUuid(Activity activity){
        SharedPreferences pref = activity.getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        String uuid = pref.getString("key_uuid",UUID.randomUUID().toString());

        SharedPreferences.Editor editor = pref.edit();
        editor.putString("key_uuid", uuid);
        editor.apply();

        NetRequest.registerUUID(activity, uuid);
        return uuid;
    }




}
