package com.example.vibtran;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.vibtran.analysis.PatternToVib;
import com.example.vibtran.model.AppStore;
import com.example.vibtran.network.NetRequest;
import com.example.vibtran.network.NetRequestInterface;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class ReceiverActivity extends AppCompatActivity {

    EditText cost;
    //initialize vibration
    private ArrayList<Long> testPattern = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ReceiverActivity activity = this;
        setContentView(R.layout.activity_send_vib);

       cost = (EditText) findViewById(R.id.Cost);

        EditText power = (EditText) findViewById(R.id.powerVal);

        EditText delay = (EditText) findViewById(R.id.delayVal);

        new android.os.Handler(Looper.getMainLooper()).postDelayed(
                new Runnable() {
                    public void run() {
                        Log.i("tag", "This'll run 300 milliseconds later");
                    }
                },
                300);





        Button test = findViewById(R.id.ReceiveStartButt);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String costNum = cost.getText().toString();
                Log.d("Success", AppStore.getUuid(activity) + costNum);

                if( costNum.trim().length() > 0) {
                    if(Integer.parseInt(costNum)>0){
                        NetRequest.getOtp(activity, AppStore.getUuid(activity), costNum, new NetRequestInterface() {
                            @Override
                            public void respones(JSONObject json, VolleyError error) {
                                if (json != null) {
                                    Log.d("Success", json.toString());

                                    String otp = "";
                                    try {
                                        otp = json.getString("message");
                                    } catch (JSONException e) {
                                        Log.d("fail", "otp cannot get");
                                    }
                                    long[] pattern = PatternToVib.encode(otp, Integer.parseInt(power.getText().toString()), Integer.parseInt(delay.getText().toString()));


                                    Intent intent = new Intent(ReceiverActivity.this, TransmitActivity.class);
                                    intent.putExtra("pattern", pattern);
                                    intent.putExtra("otp", otp);
                                    intent.putExtra("cost",cost.getText().toString());
                                    startActivity(intent);





                                } else {
                                    Log.d("fail", "null");
                                }
                            }
                        });
                    }else{
                        Toast.makeText(getApplicationContext(), "0 cost cannot start transaction", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Cost should not be empty", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}