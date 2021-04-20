package com.example.vibtran;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.android.volley.VolleyError;
import com.example.vibtran.model.AppStore;
import com.example.vibtran.network.NetRequest;
import com.example.vibtran.network.NetRequestInterface;
import org.json.JSONObject;

public class ReceiptActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        String[] paymentInfo = getIntent().getExtras().getStringArray("PayInfo");
        SharedPreferences pref = this.getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
//        int balance = pref.getInt("balance", 0);
        SharedPreferences.Editor editor = pref.edit();
        NetRequest.getBalance(this, AppStore.getUuid(this),new  NetRequestInterface() {
            int balance = 0;
            @Override
            public void respones(JSONObject json, VolleyError error) {
                if (json != null) {
                    Log.d("Success", json.toString());
                    try{
                        balance = json.getInt("message");
                    }catch (Exception e){

                    }
                    editor.putInt("balance", balance);
                    editor.apply();
                }
            }
        });
        TextView sender = (TextView) findViewById(R.id.ReceiptSender);
        TextView receiver = (TextView) findViewById(R.id.ReceiptReceiver);
        TextView amount = (TextView) findViewById(R.id.ReceiptAmount);
        TextView balance = (TextView) findViewById(R.id.ReceiptBalance);

        sender.setText(paymentInfo[0]);
        receiver.setText(paymentInfo[1]);
        amount.setText(paymentInfo[2]);
        if(paymentInfo[3].matches("sender")) {
            balance.setText(String.valueOf(pref.getInt("balance", 0)-Integer.parseInt(paymentInfo[2])));
        }else{
            balance.setText(String.valueOf(pref.getInt("balance", 0)+Integer.parseInt(paymentInfo[2])));
        }


        Button receiptConfirm = (Button) findViewById(R.id.ReceiptButton);
        receiptConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReceiptActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });


    }
}