package com.example.vibtran.ui.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.VolleyError;
import com.example.vibtran.DepositActivity;
import com.example.vibtran.R;
import com.example.vibtran.SenderActivity;
import com.example.vibtran.ReceiverActivity;
import com.example.vibtran.WithdrawActivity;
import com.example.vibtran.model.AppStore;
import com.example.vibtran.network.NetRequest;
import com.example.vibtran.network.NetRequestInterface;

import org.json.JSONObject;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView showBalance = root.findViewById(R.id.UserEmail);
        SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        NetRequest.getBalance(getActivity(), AppStore.getUuid(getActivity()),new  NetRequestInterface() {
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
                    showBalance.setText("$"+ String.valueOf(balance));


                }
            }
        });





        Button send = (Button) root.findViewById(R.id.Send);
        Button receive = (Button) root.findViewById(R.id.Receive);
        Button deposit = (Button) root.findViewById(R.id.Deposit);
        Button withdraw = (Button) root.findViewById(R.id.Withdraw);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//        return root;
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SenderActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ReceiverActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DepositActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WithdrawActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return root;
    }



}