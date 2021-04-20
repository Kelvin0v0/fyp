package com.example.vibtran;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.vibtran.model.AppStore;
import com.example.vibtran.network.NetRequest;
import com.example.vibtran.network.NetRequestInterface;
import com.example.vibtran.ui.login.LoginViewModel;

import org.json.JSONObject;


public class RegisterActivity extends AppCompatActivity {
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final EditText usernameEditText = findViewById(R.id.new_username);
        final EditText emailEditText = findViewById(R.id.new_email);
        final EditText phoneEditText = findViewById(R.id.phone_num);
        final EditText passwordEditText = findViewById(R.id.new_password);
        final EditText confirmPasswordEditText = findViewById(R.id.confirm_password);
        final CheckBox isMerchant = findViewById(R.id.isMerchant);
        final Button RegisterButton = findViewById(R.id.Register);
        String uuid = AppStore.getUuid(this);



        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Reg:" , this+" : " + usernameEditText.getText().toString()+" : "+uuid+" : "+emailEditText.getText().toString()+" : "+phoneEditText.getText().toString()+" : "+passwordEditText.getText().toString()+" : "+ confirmPasswordEditText.getText().toString()+" : "+isMerchant.isChecked());
                if(usernameEditText.getText().toString().matches("")|| emailEditText.getText().toString().matches("")||passwordEditText.getText().toString().matches("")){
                    Toast.makeText(getApplicationContext(), "Information is Empty", Toast.LENGTH_LONG).show();
                    Log.d("Error", "confirm password wrong");
                }else {
                    if (passwordEditText.getText().toString().matches(confirmPasswordEditText.getText().toString())) {
                        NetRequest.register(RegisterActivity.this, usernameEditText.getText().toString(), uuid, emailEditText.getText().toString(), phoneEditText.getText().toString(), passwordEditText.getText().toString(), isMerchant.isChecked(), new NetRequestInterface() {
                            @Override
                            public void respones(JSONObject json, VolleyError error) {
                                if (json != null) {
                                    Log.d("Success", json.toString());
                                    Toast.makeText(RegisterActivity.this, "Registered new account", Toast.LENGTH_LONG).show();
                                    RegisterActivity.this.finish();

                                } else {
                                    Log.e("Error", error.toString());
                                }

                            }

                        });
                    } else {
                        Toast.makeText(getApplicationContext(), "confirm password wrong", Toast.LENGTH_LONG).show();
                        Log.d("Error", "confirm password wrong");
                    }
                }
            }
        });
    }
}