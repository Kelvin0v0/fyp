package com.example.vibtran;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.vibtran.model.AppStore;
import com.example.vibtran.network.NetRequest;
import com.example.vibtran.network.NetRequestInterface;

import org.json.JSONException;
import org.json.JSONObject;


public class TransmitActivity extends AppCompatActivity {
    public Vibrator vibrate;
    String code = "";
    String cost = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transmit);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        startTimer();
        long[] pattern = getIntent().getExtras().getLongArray("pattern");
        code = getIntent().getExtras().getString("otp");
        cost = getIntent().getExtras().getString("cost");
        vibrate = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        vibrate.vibrate(pattern, -1);
        Button cancel = (Button) findViewById(R.id.VibCancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetRequest.cancelPay(TransmitActivity.this, AppStore.getUuid(TransmitActivity.this),code,cost,new NetRequestInterface() {
                    @Override
                    public void respones(JSONObject json, VolleyError error) {
                        if(json != null ) {
                            Intent intent = new Intent(TransmitActivity.this, ReceiverActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Log.d("fail", error.toString() );
                        }

                    }

                });
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        vibrate.cancel();
        cancelTimer();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        vibrate.cancel();
        cancelTimer();
    }

    //Declare timer for check payment
    CountDownTimer cTimer = null;
    //start timer function
    void startTimer() {
        TextView message = (TextView) findViewById(R.id.TransmitText);
        cTimer = new CountDownTimer(2000, 1000) {
            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {
                NetRequest.checkPay(TransmitActivity.this, AppStore.getUuid(TransmitActivity.this), code,   new NetRequestInterface() {

                    @Override
                    public void respones(JSONObject json, VolleyError error) {
                        if (json != null) {
                            try {
                                String status = json.getString("message");
                                if(status.matches("completed")) {
                                    Log.d("Success", json.toString());
                                    Toast.makeText(getApplicationContext(), "Sender received data", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(TransmitActivity.this, ReceiptActivity.class);
                                    intent.putExtra("PayInfo", new String[]{json.getString("sender"), json.getString("receiver"), json.getString("amount"),"receiver"});
                                    startActivity(intent);
                                }else if(status.matches("paying")){
                                    message.setText("Wait for Sender...");
                                    startTimer();
                                }else if(status.matches("transmitting")){
                                    Log.d("CheckStatus", "transmitting");
                                    startTimer();
                                }else if(status.matches("cancelled")){
                                    message.setText("Sender cancelled the payment");
                                    Log.d("CheckStatus", "cancelled");
                                    startTimer();
                                }else{
                                    Toast.makeText(getApplicationContext(), "server error", Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                Log.d("fail", "Payment Info cannot get"+ e.toString());
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        };
        cTimer.start();
    }


    //cancel timer
    void cancelTimer() {
        if(cTimer!=null)
            cTimer.cancel();
    }
}