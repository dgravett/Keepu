package com.example.keepu;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {

    EditText textInputUsername;
    EditText textInputPassword;
    Button loginButton;
    TextView incorrectLogin;
    RequestQueue requestQueue;
    Context context;

    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.loginButton);
        textInputUsername = findViewById(R.id.textInputUsername);
        textInputPassword = findViewById(R.id.textInputPassword);
        incorrectLogin = findViewById(R.id.textViewIncorrectLogin);
        this.context = this;

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String usernameInput = textInputUsername.getText().toString();
                String passwordInput = textInputPassword.getText().toString();

                if(!isValid(usernameInput)) {
                    incorrectLogin.setText("Enter a valid Email");
                    incorrectLogin.setVisibility(View.VISIBLE);
                }else if(passwordInput.length() == 0){
                    incorrectLogin.setText("Enter a valid password");
                    incorrectLogin.setVisibility(View.VISIBLE);
                }
                else{
                    checkLogin(usernameInput, passwordInput);
                }
            }

        });

    }

    //Used to disable Back button on login page
    @Override
    public void onBackPressed(){ }

    //Go to the home page activity
    private void goToSecondActivity(String user_id) {
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("user_id", user_id);
        startActivity(intent);
    }

    //Calls the API to test the login
    private void checkLogin(String username, String password){
        this.url = "https://keepuapp.herokuapp.com/api/users/login";

        requestQueue = Volley.newRequestQueue(this.context);

        JSONObject body = new JSONObject();

        try {
            body.put("email", username);
            body.put("password", password);
        }catch(JSONException e) {
            Log.e("Keepu", "Invalid JSON Object.", e);
        }

        System.out.println(body.toString());

        CustomJsonArrayRequest req = new CustomJsonArrayRequest(Request.Method.POST, url, body,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (response.length() > 0) {
                            try {
                                String user_id = response.get("_id").toString();
                                goToSecondActivity(user_id);
                            } catch (JSONException e) {
                                Log.e("Volley", "Invalid JSON Object.");
                            }
                        } else {
                            incorrectLogin.setText("Incorrect username or password");
                            incorrectLogin.setVisibility(View.VISIBLE);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                    }
                }
        );

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
    }

    //Checks if the inputted Email is Valid
    public static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}
