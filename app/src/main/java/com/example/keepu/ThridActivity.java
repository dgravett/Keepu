package com.example.keepu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class ThridActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thrid);

        Intent intent = getIntent();

        EditText editText = findViewById(R.id.textViewTitle2);
        editText.setText("   " + intent.getStringExtra("Title"));
    }
}
