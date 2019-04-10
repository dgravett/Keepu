package com.example.keepu;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.support.v7.widget.Toolbar;

public class SecondActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        drawerLayout = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Your Topics");
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();

                        switch(menuItem.getItemId()){
                            case R.id.nav_home:
                                return true;
                            case R.id.nav_logout:
                                goToMainActivity();
                                return true;
                        }

                        return true;
                    }
                });

        final Intent intent = getIntent();

        final String[] textArray = {"Human Trafficking"};

        LinearLayout layout = findViewById(R.id.linearLayout);

        Button button = new Button(this);
        button.setText(textArray[0]);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Button b = (Button)v;
                goToThirdActivity(b.getText().toString(), intent.getStringExtra("user_id"));
            }
        });

        layout.addView(button);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void goToThirdActivity(String title, String user_id) {
        Intent intent = new Intent(this, ThirdActivity.class);
        intent.putExtra("Title", title);
        intent.putExtra("user_id", user_id);
        startActivity(intent);
    }

}
