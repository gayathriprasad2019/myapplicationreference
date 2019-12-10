package com.example.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class LoginActivity extends AppCompatActivity {
    private Context context;
    public static final String TAG="LoginActivity";

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //set listener

        final EditText etUsername = findViewById(R.id.id_login_email);
        final EditText etPassword = findViewById(R.id.id_login_password);
        Button btnLogin = findViewById(R.id.id_btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                if (username.equals("username") && password.equals("password")) {

                    //subscription to firebase messaging
                    FirebaseMessaging.getInstance()
                            .subscribeToTopic("backtowork")
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG,"Successfully Subscribed to FCM Topic");
                                    }
                                    else {
                                        Log.d(TAG,"Error Subscribing to FCM Topic");

                                    }

                                }
                            });

                    Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                    startActivity(intent);

                } else {
                    Toast
                            .makeText(getApplicationContext(), "InvalidCredentials" + username, Toast.LENGTH_LONG)
                            .show();

                }
                // TODO Perform User Validation
                /*Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://api.github.com/")
                        .build();


                Toast
                        .makeText(getApplicationContext(),"Username is" +username, Toast.LENGTH_LONG)
                        .show();
                Toast
                        .makeText(getApplicationContext(), password, Toast.LENGTH_SHORT)
                        .show();*/

            }
        });

    }


    public void processSignUpLinkClick(View view) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }
}
