package com.example.total;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText editTextEmail, editTextPassword;           //Declare Edittexts for mail and password
    private Button buttonLogin;                                 //Login Button
    private ProgressDialog progressDialog;                      //Progress circle
    private TextView textViewSignup;                            //Declare Signup textview
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, FirstActivity.class));
            return;
        }

        editTextEmail= (EditText) findViewById(R.id.editTextUsername);          //Casting elements on screen
        editTextPassword= (EditText)findViewById(R.id.editTextPassword);
        buttonLogin= (Button) findViewById(R.id.buttonLogin);
        textViewSignup= (TextView) findViewById(R.id.textviewSignup) ;
        progressDialog= new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");


        buttonLogin.setOnClickListener(this);                   //Setting listeners for Intents
        textViewSignup.setOnClickListener(this);

    }

    private void userLogin(){                                   //Consuner Login Function
        final String email= editTextEmail.getText().toString().trim();
        final String password= editTextPassword.getText().toString().trim();
        progressDialog.show();
        StringRequest stringRequest=new StringRequest(
                Request.Method.POST,
                Constants.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject obj = new JSONObject(response);
                            if(!obj.getBoolean("error")){
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(obj.getString("lpg_id"),obj.getString("consumer_no"),
                                        obj.getString("email_id")
                                        );
                               startActivity(new Intent(getApplicationContext(),FirstActivity.class));
                               finish();


                            }
                            else{
                                Toast.makeText(
                                  getApplicationContext(),
                                  obj.getString("message"),
                                  Toast.LENGTH_LONG
                                ).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(
                                getApplicationContext(),
                                error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email_id",email);
                params.put("password",password);

               return params;
            }
        };


        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);

    }


    @Override
    public void onClick(View view) {
        if(view==buttonLogin){
            userLogin();
        }
        if(view==textViewSignup){
            startActivity(new Intent(this,MainActivity .class));
        }
    }
}
