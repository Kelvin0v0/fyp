package com.example.vibtran;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.vibtran.analysis.ClusterVib;
import com.example.vibtran.analysis.PatternToVib;
import com.example.vibtran.model.AppStore;
import com.example.vibtran.network.NetRequest;
import com.example.vibtran.network.NetRequestInterface;
import com.example.vibtran.ui.home.HomeFragment;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

public class SenderActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private float lastX = 0.0f, lastY = 0.0f, lastZ= 0.0f;
    private double keep = 0.0;
    private boolean restart = false;
    private Sensor accelerometer;
    private Vector<Long> buffer = new Vector<Long>();
    private Vector<Double> bufferSensor = new Vector<Double>();
    private long current =  Calendar.getInstance().getTimeInMillis();
    public Vibrator v;
    float[] gravity = new float[3];

    private boolean sensor_init = false;
    private boolean threshold_init = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_vib);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        initializeViews();
        startTimer();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION ) != null) {
            // success! we have an accelerometer

            buffer = new Vector<Long>();
            current = Calendar.getInstance().getTimeInMillis();
            Log.d("current" , String.valueOf(current));
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION );

        } else if(sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER ) != null){
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER );
        }else
            {
            // fai! we dont have an accelerometer!
        }
        sensor_init = false;
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        //initialize vibration
        v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);


        Button start = findViewById(R.id.SenderStartButton);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vi) {
                resume();
                cancelTimer();
                startTimer();
            }
        });

    }



    //onResume() register the accelerometer for listening the events
    protected void resume() {
        buffer = new Vector<Long>();
        current = Calendar.getInstance().getTimeInMillis();
        Log.d("current" , String.valueOf(current));

        sensor_init = false;
        threshold_init = false;
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    //onPause() unregister the accelerometer for stop listening the events
    protected void pause() {
        sensorManager.unregisterListener(this);
        int size = buffer.size();
//        - (int)((double)buffer.size() %5.0);
        long[] tmp = new long[size];
        long[] getPattern = new long[size];
        long[] getKPattern = new long[size];
        for(int i=0; i < size;i++){
            tmp[i] = buffer.get(i);
        }
        getPattern = PatternToVib.patternReform(tmp);
        //K-mean
        getKPattern = ClusterVib.kmeanEncodedResult(tmp,10);


        ArrayList<String> decodedOtp = new ArrayList<>();
        ArrayList<String> decodedKOtp = new ArrayList<>();
        try {
            decodedOtp = PatternToVib.decode(getPattern, 200);
            decodedKOtp = PatternToVib.decode(getKPattern, 200);
            String sendOtp = "X" + decodedOtp.get(1)+ decodedOtp.get(2)+"Y";
            String sendKOtp = "X" + decodedKOtp.get(1)+ decodedKOtp.get(2)+"Y";
            Log.d("reverse: K:", sendKOtp+" : n :"+sendOtp+ " : "+ String.valueOf(buffer.size())+ " : "+ String.valueOf(size));
            NetRequest.sendOtp(this, AppStore.getUuid(this),sendOtp ,new NetRequestInterface() {

                @Override
                public void respones(JSONObject json, VolleyError error) {
                    SharedPreferences pref = SenderActivity.this.getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                    int balance = pref.getInt("balance", 0);
                    if (json != null) {
                        otpSendSuccess(json,SenderActivity.this, balance);

                    } else {
                        Log.d("fail",  error.toString());
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            });

            NetRequest.sendOtp(this, AppStore.getUuid(this),sendKOtp ,new NetRequestInterface() {

                @Override
                public void respones(JSONObject json, VolleyError error) {
                    SharedPreferences pref = SenderActivity.this.getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                    int balance = pref.getInt("balance", 0);
                    if (json != null) {
                        otpSendSuccess(json,SenderActivity.this, balance);
                    } else {
                        Log.d("fail",  error.toString());
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }catch (Exception e){
            Log.e("error", e.toString());
        }

    }
    private void otpSendSuccess(JSONObject json, Activity activity, int balance){
        TextView msg = findViewById(R.id.ReceiveMsg);
        try {
            if(json.getInt("status") != -1) {
                Intent intent = new Intent(activity, PaymentActivity.class);
                Log.d("Success", json.toString());
                intent.putExtra("PayInfo", new String[]{json.getString("receiver"), json.getString("cost"), json.getString("otp"), Integer.toString(balance)});
                startActivity(intent);
            }else{
                msg.setText("Otp is not correct. Please Rescan");
            }
        } catch (JSONException e) {
            Log.d("fail", "Payment Info cannot get");
        }

    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        final float alpha = 0.8f;
        float[] linear_acceleration = new float[3];
        double vib;
        gravity[0] = alpha * gravity[0] + (1 - alpha) * event.values[0];
        gravity[1] = alpha * gravity[1] + (1 - alpha) * event.values[1];
        gravity[2] = alpha * gravity[2] + (1 - alpha) * event.values[2];
        linear_acceleration[0] = event.values[0] - gravity[0];
        linear_acceleration[1] = event.values[1] - gravity[1];
        linear_acceleration[2] = event.values[2] - gravity[2];
        if (!sensor_init){
            if(sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION ) == null){
                lastX = linear_acceleration[0];
                lastY = linear_acceleration[1];
                lastZ = linear_acceleration[2];
            }else {
                lastX = event.values[0];
                lastY = event.values[1];
                lastZ = event.values[2];
            }
                keep =  0;
                sensor_init = true;
                return;
            }



        if(sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION ) == null){
            vib = Math.sqrt(Math.pow(linear_acceleration[0] - lastX, 2) + Math.pow(linear_acceleration[1] - lastY, 2) );
            lastX = linear_acceleration[0];
            lastY = linear_acceleration[1];
            lastZ = linear_acceleration[2];
        }else {
            vib = Math.sqrt(Math.pow(event.values[0] - lastX, 2) + Math.pow(event.values[1] - lastY, 2) + Math.pow(event.values[2] - lastZ, 2));

            lastX = event.values[0];
            lastY = event.values[1];
            lastZ = event.values[2];
        }

        if (Math.abs(keep - vib) > 0.02) {
            Log.d("dif: ",String.valueOf(keep-vib));
            Log.d("react: ", "1");



           long now  =  Calendar.getInstance().getTimeInMillis();
            if(keep != 0.0) {
                bufferSensor.add(Math.abs(keep - vib));
                if(buffer.size()<300) {
                    buffer.add(now - current);
                    cancelTimer();
                    startTimer();
                }else{
                    cancelTimer();
                    pause();
                }
            }
        }
        keep = vib;
    }


    //Declare timer
    CountDownTimer cTimer = null;
    //start timer function
    void startTimer() {
        TextView msg = findViewById(R.id.ReceiveMsg);
        cTimer = new CountDownTimer(8000, 1000) {
            public void onTick(long millisUntilFinished) {
                msg.setText("Scanning for vibration ");
                restart = false;
                Log.d("seconds remaining: " ,String.valueOf(millisUntilFinished / 1000));


                StringBuffer sb = new StringBuffer();
                StringBuffer sba = new StringBuffer();
                for (Long n : buffer){
                    sb.append(n+",");
                }
                for (double n : bufferSensor){
                    sba.append(n+",");
                }
                Log.d("buffer: " ,sb.toString());

            }
            public void onFinish() {
                pause();
                msg.setText("Scanner is stop");
                restart = true;
            }
        };
        cTimer.start();
    }


    //cancel timer
    void cancelTimer() {
        if(cTimer!=null)
            cTimer.cancel();
    }


    private static ArrayList<Double> standardNormal(Vector<Double> bufferSensor, double max, double min){
        ArrayList<Double> result = new ArrayList<>();
        double sum = 0.0;
        double mean = 0.0;
        double standard = 0.0;
        double standardD = 0.0;
        ArrayList<Integer> temp = new ArrayList<>();
        for(int i=0; i < bufferSensor.size();i++){
            sum+=bufferSensor.get(i);
        }
        mean = sum/bufferSensor.size();
        for(int i=0; i < bufferSensor.size();i++){
            standard+= Math.pow(bufferSensor.get(i)-mean,2);
        }
        standardD = Math.sqrt(standard/sum);
        for(int i = 0;i< bufferSensor.size();i++){
            result.add((bufferSensor.get(i)-mean)/standardD);
        }

        for(int i =0;i< result.size();i++){
            Log.d("normalized " ,String.valueOf(result.get(i)));
        }

        return result;
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
        cancelTimer();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(getApplicationContext(), "Rescan Required", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);
        cancelTimer();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
