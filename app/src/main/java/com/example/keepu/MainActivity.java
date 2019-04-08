package com.example.keepu;

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

    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        loginButton = findViewById(R.id.loginButton);
        textInputUsername = findViewById(R.id.textInputUsername);
        textInputPassword = findViewById(R.id.textInputPassword);
        incorrectLogin = findViewById(R.id.textViewIncorrectLogin);

        requestQueue = Volley.newRequestQueue(this);

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

        this.url = "keepu.azurewebsites.net/api/users/login";

        JSONObject body = new JSONObject();

        try {
            body.put("email", username);
            body.put("password", password);
        }catch(JSONException e) {
            Log.e("Keepu", "Invalid JSON Object.", e);
        }

        CustomJsonArrayRequest req = new CustomJsonArrayRequest(Request.Method.POST, url, body,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response.length() > 0) {
                            try {
                                JSONObject jsonObj = response.getJSONObject(0);
                                String user_id = jsonObj.get("_id").toString();
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
