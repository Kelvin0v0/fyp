package com.example.vibtran.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.Patterns;

import com.android.volley.VolleyError;
import com.example.vibtran.R;
import com.example.vibtran.model.AppStore;
import com.example.vibtran.network.NetRequest;
import com.example.vibtran.network.NetRequestInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.jar.JarException;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private Activity activity;

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        SharedPreferences pref = activity.getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        // can be launched in a separate asynchronous job
        Map<String, String> params = new HashMap();
        params.put("uuid", AppStore.getUuid(activity));
        params.put("email", username );
        params.put("password", password);
        NetRequest.sendRequest(activity, "m", params, new NetRequestInterface() {
            @Override
            public void respones(JSONObject json, VolleyError error) {
                int balance = 0;
                String user = "";
                String user_mail = "";
                if(json != null ) {
                    try {
                        if (json.getInt("status") == 0) {
                            //                    User fakeUser = new User(java.util.UUID.randomUUID().toString(), username);
                            Log.d("Response111: ", json.toString());
                            //                    return new Result.Success<>(fakeUser);
                            try {
                                balance = json.getInt("balance");
                                user = json.getString("name");
                                user_mail = username;
                            } catch (JSONException e) {
                                balance = 0;
                            }
                            editor.putInt("balance", balance);
                            editor.putString("user_name", user);
                            editor.putString("user_email", user_mail);
                            editor.apply();
                            loginResult.setValue(new LoginResult(new LoggedInUserView(user)));
                        }else{
                            Log.e("error",  " : " +json.toString());
                            loginResult.setValue(new LoginResult(R.string.login_failed));
                        }
                    } catch (JSONException e) {
                         Log.e("error",  " : " +json.toString());
                    }

                }else{
                    Log.e("error",  " : " +error.toString());
                    loginResult.setValue(new LoginResult(R.string.login_failed));
                }

            }

        });


    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}