package com.example.keepu;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;

public class ThirdActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    LinearLayout linearLayout;
    TextView textViewQuestion1a;
    TextView textViewQuestion1b;
    TextView textViewQuestion1c;
    TextView textViewQuestion1d;
    TextView textViewFundedBy;
    TextView textViewComputersNeeded;
    TextView textViewTabletsNeeded;
    TextView textViewOfficePhonesNeeded;
    TextView textViewOfficeSpace;
    TextView textViewEventSpace;
    TextView textViewTrainingSpace;
    TextView textViewManagersNeeded;
    TextView textViewTrainingSpecialistsNeeded;
    TextView textViewCaseWorkersNeeded;
    TextView textViewCommunityEducatorsNeeded;
    TextView textViewQuestion18a;
    TextView textViewQuestion18b;
    TextView textViewQuestion18c;
    TextView textViewOutputTitle;
    TextView textViewOutput;
    TextView textViewOutcomeTitle;
    TextView textViewOutcome;
    RequestQueue requestQueue;
    Context context;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        this.context = this;

        drawerLayout = findViewById(R.id.drawer_layout);
        linearLayout = findViewById(R.id.linearLayout);

        textViewQuestion1a = findViewById(R.id.textViewQuestion1A);
        textViewQuestion1b = findViewById(R.id.textViewQuestion1B);
        textViewQuestion1c = findViewById(R.id.textViewQuestion1C);
        textViewQuestion1d = findViewById(R.id.textViewQuestion1D);
        textViewFundedBy = findViewById(R.id.textViewFundedBy);
        textViewComputersNeeded = findViewById(R.id.textViewComputersNeeded);
        textViewTabletsNeeded = findViewById(R.id.textViewTabletsNeeded);
        textViewOfficePhonesNeeded = findViewById(R.id.textViewOfficePhonesNeeded);
        textViewOfficeSpace = findViewById(R.id.textViewOfficeSpace);
        textViewEventSpace = findViewById(R.id.textViewEventSpace);
        textViewTrainingSpace = findViewById(R.id.textViewTrainingSpace);
        textViewManagersNeeded = findViewById(R.id.textViewManagersNeeded);
        textViewTrainingSpecialistsNeeded = findViewById(R.id.textViewTrainingSpecialistsNeeded);
        textViewCaseWorkersNeeded = findViewById(R.id.textViewCaseWorkersNeeded);
        textViewCommunityEducatorsNeeded = findViewById(R.id.textViewCommunityEducatorsNeeded);
        textViewQuestion18a = findViewById(R.id.textViewQuestion18a);
        textViewQuestion18b = findViewById(R.id.textViewQuestion18b);
        textViewQuestion18c = findViewById(R.id.textViewQuestion18c);
        textViewOutputTitle = findViewById(R.id.textViewOutputTitle);
        textViewOutput = findViewById(R.id.textViewOutput);
        textViewOutcomeTitle = findViewById(R.id.textViewOutcomeTitle);
        textViewOutcome = findViewById(R.id.textViewOutcome);

        Intent intent = getIntent();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(intent.getStringExtra("Title"));
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
                                goToSecondActivity();
                                return true;
                            case R.id.nav_logout:
                                goToMainActivity();
                                return true;
                        }

                        return true;
                    }
                });

        getUserTopic(intent.getStringExtra("user_id"));
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

    private void goToSecondActivity() {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

    private void getUserTopic(String user_id){
        this.url = "https://keepuapp.herokuapp.com/api/knot1/" + user_id;

        requestQueue = Volley.newRequestQueue(this.context);

        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response.length() > 0) {
                            try {
                                String[] question1 = response.getJSONObject(0).get("q1").toString().replace("[", "").replace("]", "").split(",");

                                if(question1[0].equals("true")){
                                    textViewQuestion1a.setVisibility(View.VISIBLE);
                                }
                                if(question1[1].equals("true")){
                                    textViewQuestion1b.setVisibility(View.VISIBLE);
                                }
                                if(question1[2].equals("true")){
                                    textViewQuestion1c.setVisibility(View.VISIBLE);
                                }
                                if(question1[3].equals("true")){
                                    textViewQuestion1d.setVisibility(View.VISIBLE);
                                }

                                String question2 = response.getJSONObject(0).get("q2").toString();
                                String question3 = response.getJSONObject(0).get("q3").toString();

                                if(question2.equals("true")){
                                    textViewFundedBy.setText("  - Project funded by " + question3);
                                    textViewFundedBy.setVisibility(View.VISIBLE);
                                }

                                String[] question4 = response.getJSONObject(0).get("q4").toString().replace("[", "").replace("]", "").split(",");
                                String[] question5 = response.getJSONObject(0).get("q5").toString().replace("[", "").replace("]", "").split(",");
                                String[] question6 = response.getJSONObject(0).get("q6").toString().replace("[", "").replace("]", "").split(",");
                                String[] question7 = response.getJSONObject(0).get("q7").toString().replace("[", "").replace("]", "").split(",");
                                String question8 = response.getJSONObject(0).get("q8").toString();
                                String question9 = response.getJSONObject(0).get("q9").toString();
                                String question10 = response.getJSONObject(0).get("q10").toString();
                                String question11 = response.getJSONObject(0).get("q11").toString();
                                String question12 = response.getJSONObject(0).get("q12").toString();
                                String question13 = response.getJSONObject(0).get("q13").toString();
                                String question14 = response.getJSONObject(0).get("q14").toString();
                                String question15 = response.getJSONObject(0).get("q15").toString();
                                String question16 = response.getJSONObject(0).get("q16").toString();
                                String question17 = response.getJSONObject(0).get("q17").toString();

                                if(question4[0].equals("true")){
                                    textViewComputersNeeded.setText("  - Computers Needed - " + question8);
                                    textViewComputersNeeded.setVisibility(View.VISIBLE);
                                }
                                if(question4[1].equals("true")){
                                    textViewTabletsNeeded.setText("  - Tablets Needed - " + question9);
                                    textViewTabletsNeeded.setVisibility(View.VISIBLE);
                                }
                                if(question4[2].equals("true")){
                                    textViewOfficePhonesNeeded.setText("  - Office Phones Needed - " + question10);
                                    textViewOfficePhonesNeeded.setVisibility(View.VISIBLE);
                                }

                                if(question5[0].equals("true")){
                                    textViewOfficeSpace.setText("  - Office Space Needed - " + question11 + " square feet");
                                    textViewOfficeSpace.setVisibility(View.VISIBLE);
                                }
                                if(question5[1].equals("true")){
                                    textViewEventSpace.setText("  - Event Space Needed - " + question12 + " square feet");
                                    textViewEventSpace.setVisibility(View.VISIBLE);
                                }
                                if(question5[2].equals("true")){
                                    textViewTrainingSpace.setText("  - Training Space Needed - " + question13 + " square feet");
                                    textViewTrainingSpace.setVisibility(View.VISIBLE);
                                }

                                if(question7[0].equals("true")){
                                    textViewManagersNeeded.setText("  - Managers Needed - " + question14);
                                    textViewManagersNeeded.setVisibility(View.VISIBLE);
                                }
                                if(question7[1].equals("true")){
                                    textViewTrainingSpecialistsNeeded.setText("  - Training Specialists Needed - " + question15);
                                    textViewTrainingSpecialistsNeeded.setVisibility(View.VISIBLE);
                                }
                                if(question7[2].equals("true")){
                                    textViewCaseWorkersNeeded.setText("  - Case Workers Needed - " + question16);
                                    textViewCaseWorkersNeeded.setVisibility(View.VISIBLE);
                                }
                                if(question7[3].equals("true")){
                                    textViewCommunityEducatorsNeeded.setText("  - Community Educators Needed - " + question17);
                                    textViewCommunityEducatorsNeeded.setVisibility(View.VISIBLE);
                                }

                                String[] question18 = response.getJSONObject(0).get("q18").toString().replace("[", "").replace("]", "").split(",");

                                if(question18[0].equals("true")){
                                    textViewQuestion18a.setVisibility(View.VISIBLE);
                                }
                                if(question18[1].equals("true")){
                                    textViewQuestion18b.setVisibility(View.VISIBLE);
                                }
                                if(question18[2].equals("true")){
                                    textViewQuestion18c.setVisibility(View.VISIBLE);
                                }

                            } catch (JSONException e) {
                                Log.e("Volley", "Invalid JSON Object.");
                            }
                        } else {

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                    }
                }
        ) {
            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
                    return Response.success(new JSONArray(jsonString),
                            HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (JSONException je) {
                    return Response.error(new ParseError(je));
                }
            }
        };

        req.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        requestQueue.add(req);

        this.url = "https://keepuapp.herokuapp.com/api/knot2/" + user_id;

        JsonArrayRequest req2 = new JsonArrayRequest(Request.Method.GET, url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response.length() > 0) {
                            try {
                                String question1 = response.getJSONObject(0).get("q1").toString();
                                String question2 = response.getJSONObject(0).get("q2").toString();
                                String question3 = response.getJSONObject(0).get("q3").toString();
                                String question4 = response.getJSONObject(0).get("q4").toString();
                                String question5 = response.getJSONObject(0).get("q5").toString();
                                String question6 = response.getJSONObject(0).get("q6").toString();

                                if(!question1.equals("null")){
                                    textViewOutputTitle.setVisibility(View.VISIBLE);
                                    textViewOutput.setText("  -" + question1 + " " + question2 + " will " + question3 + " in " + question4 + " " + question5 + " " + question6);
                                    textViewOutput.setVisibility(View.VISIBLE);
                                }

                                String question7 = response.getJSONObject(0).get("q7").toString();
                                String question8 = response.getJSONObject(0).get("q8").toString();
                                String question9 = response.getJSONObject(0).get("q9").toString();
                                String question10 = response.getJSONObject(0).get("q10").toString();
                                String question11 = response.getJSONObject(0).get("q11").toString();

                                if(!question7.equals("null")){
                                    textViewOutcomeTitle.setVisibility(View.VISIBLE);
                                    textViewOutcome.setText("  -" + question7 + " " + question8 + " will " + question9 + " " + question10 + " " + question11);
                                    textViewOutcome.setVisibility(View.VISIBLE);
                                }

                            } catch (JSONException e) {
                                Log.e("Volley", "Invalid JSON Object.");
                            }
                        } else {

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                    }
                }
        ) {
            @Override
            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
                    return Response.success(new JSONArray(jsonString),
                            HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (JSONException je) {
                    return Response.error(new ParseError(je));
                }
            }
        };

        req.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        requestQueue.add(req2);
    }
}
