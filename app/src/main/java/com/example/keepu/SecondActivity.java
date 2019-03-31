package com.example.keepu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        final String[] textArray = {"Human Trafficking", "Animal Shelter", "Soup Kitchen", "Orphanage"};

        LinearLayout layout = findViewById(R.id.linearLayout);

        for(int i = 0; i < textArray.length; i++) {
            Button button = new Button(this);
            button.setText(textArray[i]);

            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Button b = (Button)v;
                    goToThirdActivity(b.getText().toString());
                }
            });

            layout.addView(button);
        }

        Button buttonAddTopic = findViewById(R.id.buttonAddTopic);
        buttonAddTopic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goToFourthActivity();
            }
        });

    }

    private void goToThirdActivity(String title) {
        Intent intent = new Intent(this, ThridActivity.class);
        intent.putExtra("Title", title);
        startActivity(intent);
    }

    private void goToFourthActivity(){
        Intent intent = new Intent(this, FourthActivity.class);
        startActivity(intent);
    }
}
