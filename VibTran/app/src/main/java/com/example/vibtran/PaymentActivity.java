package com.example.vibtran;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.android.volley.VolleyError;
import com.example.vibtran.model.AppStore;
import com.example.vibtran.network.NetRequest;
import com.example.vibtran.network.NetRequestInterface;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentActivity extends AppCompatActivity {
    private String name, costNum,otp , balance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PaymentActivity activity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        TextView receiverName =  findViewById(R.id.ReceiverName);
        TextView cost =  findViewById(R.id.PaymentCost);
        TextView balanceView =  findViewById(R.id.PaymentBalance);
        Button confirm = (Button) findViewById(R.id.ConfirmButton);
        Button cancel = (Button) findViewById(R.id.CancelPay);
        String[] paymentInfo = getIntent().getExtras().getStringArray("PayInfo");
        name = paymentInfo[0];
        costNum = paymentInfo[1];
        otp = paymentInfo[2];
        balance = paymentInfo[3];

        receiverName.setText(name);
        cost.setText(costNum);
        balanceView.setText(balance);

        confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                        NetRequest.confirmPay(activity, AppStore.getUuid(activity), otp,costNum,new NetRequestInterface() {
                            @Override
                            public void respones(JSONObject json, VolleyError error) {
                                if(json != null ) {
                                    try {
                                        Intent intent = new Intent(PaymentActivity.this, ReceiptActivity.class);
                                        intent.putExtra("PayInfo", new String[]{json.getString("sender"), paymentInfo[0], paymentInfo[1],"sender"});
                                        startActivity(intent);
                                        finish();
                                    }catch (JSONException e) {
                                        Log.d("fail", "Payment Info cannot get");
                                    }
//                              loginViewModel.login(username,password);
                                }else{
                                    Log.d("fail", error.toString() );
                                }

                            }

                        });


            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                NetRequest.cancelPay(activity, AppStore.getUuid(activity), otp,costNum,new NetRequestInterface() {
                    @Override
                    public void respones(JSONObject json, VolleyError error) {
                        if(json != null ) {
                                Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
//                              loginViewModel.login(username,password);
                        }else{
                            Log.d("fail", error.toString() );
                        }

                    }

                });


            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}



