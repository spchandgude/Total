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
    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin;
    private ProgressDialog progressDialog;
    private TextView textViewSignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail= (EditText) findViewById(R.id.editTextUsername);
        editTextPassword= (EditText)findViewById(R.id.editTextPassword);
        buttonLogin= (Button) findViewById(R.id.buttonLogin);
        textViewSignup= (TextView) findViewById(R.id.textviewSignup) ;
        progressDialog= new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");


        buttonLogin.setOnClickListener(this);
        textViewSignup.setOnClickListener(this);

    }

    private void userLogin(){
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
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(obj.getString("lpg_id"),
                                        obj.getString("email_id")
                                        );
                                Toast.makeText(
                                        getApplicationContext(),
                                        "User Login Successful",
                                        Toast.LENGTH_LONG
                                ).show();


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
            startActivity(new Intent(this,MainActivity.class));
        }
    }
}
