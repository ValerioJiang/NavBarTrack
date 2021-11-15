package com.example.navbartrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.navbartrack.model.UserLogin;
import com.example.navbartrack.service.offline.UserRegistrationServiceOff;
import com.example.navbartrack.service.online.UserLoginService;
import com.example.navbartrack.service.online.UserRegistrationService;
import com.example.navbartrack.util.SessionManagement;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LoginActivity extends AppCompatActivity {

    EditText etx_logEmail, etx_logPassword;
    Button btn_login, btn_toSignUp;
    Gson gson = new Gson();
    UserLoginService userLoginService = new UserLoginService(LoginActivity.this);
    UserRegistrationServiceOff userRegistrationServiceOff = new UserRegistrationServiceOff(LoginActivity.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        etx_logEmail = findViewById(R.id.etx_logEmail);
        etx_logPassword = findViewById(R.id.etx_logPassword);
        btn_login = findViewById(R.id.btn_login);
        btn_toSignUp = findViewById(R.id.btn_toSignUp);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserLogin logUser = new UserLogin("test9Valerio@gmail.com","ciao1234");
                try {
                    userLoginService.login(logUser, new UserRegistrationService.VolleyResponseListener() {
                        @Override
                        public void onError(String message) {

                            Toast.makeText(LoginActivity.this, "Password or email wrong", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onResponse(String response)  {
                            JsonElement jsonElement = gson.fromJson(response, JsonElement.class);
                            JsonObject jsonObject = jsonElement.getAsJsonObject();
                            Toast.makeText(LoginActivity.this, jsonObject.get("accessToken").toString() , Toast.LENGTH_SHORT).show();
                            SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
                            logUser.setToken(jsonObject.get("accessToken").toString());
                            Date date = Calendar.getInstance().getTime();

                            // Display a date in day, month, year format
                            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                            String today = formatter.format(date);


                            logUser.setLastLogin(today);
                            sessionManagement.saveSession(logUser);


                            //2. step
                            moveToMainActivity();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        checkSession();
    }

    private void checkSession() {
        //check if user is logged in
        //if user is logged in --> move to mainActivity

        SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
        String accessToken = sessionManagement.getSession();

        if(accessToken != null){
            //user id logged in and so move to mainActivity
            moveToMainActivity();
        }
        else{
            //do nothing
            //da gestire nel caso il session non sia corretto
        }
    }
    /*

    public void login(View view) {
        // 1.log in to app and save session of user
        // 2. move to mainActivity

        //1. Login and save session


        UserLoginService userLoginService = new UserLoginService(LoginActivity.this);

        UserLogin logUser = new UserLogin(etx_logEmail.getText().toString(),etx_logPassword.getText().toString());
        try {
            userLoginService.login(logUser, new UserRegistrationService.VolleyResponseListener() {
                @Override
                public void onError(String message) {
                    Toast.makeText(LoginActivity.this, "Something wrong", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(String response)  {
                    JsonElement jsonElement = gson.fromJson(response, JsonElement.class);
                    JsonObject jsonObject = jsonElement.getAsJsonObject();

                    Toast.makeText(LoginActivity.this, jsonObject.get("accessToken").toString() , Toast.LENGTH_SHORT).show();
                    SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);

                    logUser.setToken(jsonObject.get("accessToken").toString());
                    sessionManagement.saveSession(logUser);

                    //2. step
                    moveToMainActivity();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    */



    private void moveToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.parentLogLayout,fragment).show(fragment);
        fragmentTransaction.commit();

    }

}