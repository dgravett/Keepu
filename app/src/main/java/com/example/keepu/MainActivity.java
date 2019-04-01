package com.example.keepu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.loginButton);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                EditText textInputUsername = findViewById(R.id.textInputUsername);
                String usernameInput = textInputUsername.getText().toString();
                EditText textInputPassword = findViewById(R.id.textInputPassword);
                String passwordInput = textInputPassword.getText().toString();

                if(usernameInput.equals("admin") && passwordInput.equals("password")){
                    goToSecondActivity();
                }else{
                    TextView incorrectLogin = findViewById(R.id.textViewIncorrectLogin);
                    incorrectLogin.setVisibility(View.VISIBLE);
                }
            }

        });

    }

    private void goToSecondActivity() {

        Intent intent = new Intent(this, SecondActivity.class);

        startActivity(intent);

    }
}
