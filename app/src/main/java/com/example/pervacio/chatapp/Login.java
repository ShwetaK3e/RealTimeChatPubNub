package com.example.pervacio.chatapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import javax.security.auth.login.LoginException;

public class Login extends AppCompatActivity {

    EditText userName;
    Button login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userName=(EditText)findViewById(R.id.userName);
        login_btn=(Button)findViewById(R.id.login);

    }

    public void login(View v){
        String UUID=userName.getText().toString();
        if(UUID!=null) {
            Intent login = new Intent(Login.this, MainActivity.class);
            login.putExtra("userName", UUID);
            this.startActivity(login);
        }
    }



}
