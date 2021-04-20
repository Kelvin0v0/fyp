package com.example.vibtran.ui.History;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.VolleyError;
import com.example.vibtran.R;
import com.example.vibtran.model.AppStore;
import com.example.vibtran.network.NetRequest;
import com.example.vibtran.network.NetRequestInterface;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

public class HistoryFragment extends Fragment {

    private HistoryViewModel historyViewModel;
    private TableLayout tableLayout;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        historyViewModel =
                new ViewModelProvider(this).get(HistoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_history, container, false);
        tableLayout=(TableLayout)root.findViewById(R.id.tableLayout);


        NetRequest.getHistory(getActivity(), AppStore.getUuid(getActivity()),
        new NetRequestInterface() {
            @Override
            public void respones(JSONObject json, VolleyError error) {
                JSONArray jsonObject = new JSONArray();
                if(json != null ) {
                    Log.d("Success", json.toString() );
                    try {
                         jsonObject = json.getJSONArray("message");

                    }catch (Exception e){
                        Log.d("json fail","fail" );
                    }




                    for (int i=0;i<jsonObject.length();i++){
                        try{JSONObject temp = jsonObject.getJSONObject(i);
                            View tableRow = inflater.inflate(R.layout.fragment_history,null,false);
                        TextView history_sender  = (TextView) tableRow.findViewById(R.id.history_sender);
                        TextView history_receiver  = (TextView) tableRow.findViewById(R.id.history_receiver);
                        TextView history_amount  = (TextView) tableRow.findViewById(R.id.history_amount);
                        TextView history_date  = (TextView) tableRow.findViewById(R.id.history_date);
                            history_sender.setText(temp.getString("sender"));
                            history_receiver.setText(temp.getString("receiver"));
                            history_amount.setText(String.valueOf(temp.getInt("amount")));

                            String[] tmp = temp.getString("created_at").split("T");
                            history_date.setText(tmp[0]+" "+tmp[1].substring(0,5));
                        tableLayout.addView(tableRow);
                        }catch (Exception e){
                            e.printStackTrace();
                        Log.d("json fail data",e.getMessage() );
                        }
//
                    }
                }else{
                    Log.d("fail", error.toString() );
                }

            }

        });


        return root;
    }
}