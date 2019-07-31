package com.example.total;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edit_consumer_no;
    private EditText edit_lpg_id;

    private EditText edit_first_name;
    private EditText edit_last_name;
    private EditText edit_contact_no;
    private EditText edit_email_id;
    private EditText edit_state;
    private EditText edit_city;
    private EditText edit_pincode;
    private EditText edit_landmark;
    private EditText edit_addressline1;
    private EditText edit_addressline2;
    private EditText edit_adhaar_no;
    private EditText edit_gst_no;
    private EditText edit_password;
    private Button buttonRegister;
    private ProgressDialog progressDialog;
    private TextView textViewLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, FirstActivity.class));
            return;
        }
        edit_consumer_no = (EditText) findViewById(R.id.id_consumer_no) ;
        edit_lpg_id = (EditText) findViewById(R.id.id_lpg_id);

        edit_first_name = (EditText) findViewById(R.id.id_consumer_first_name);
        edit_last_name = (EditText) findViewById(R.id.id_consumer_last_name);
        edit_contact_no=(EditText) findViewById(R.id.id_consumer_contact_no);
        edit_email_id=(EditText) findViewById(R.id.id_consumer_email_id);
        edit_state=(EditText) findViewById(R.id.id_consumer_state);
        edit_city=(EditText) findViewById(R.id.id_consumer_city);
        edit_pincode=(EditText) findViewById(R.id.id_consumer_pincode);
        edit_landmark=(EditText) findViewById(R.id.id_consumer_landmark);
        edit_addressline1=(EditText) findViewById(R.id.id_consumer_addressline1);
        edit_addressline2=(EditText) findViewById(R.id.id_consumer_addressline2);
        edit_adhaar_no=(EditText) findViewById(R.id.id_consumer_adhaar_no);
        edit_gst_no=(EditText)  findViewById(R.id.id_consumer_gst_no);
        edit_password=(EditText) findViewById(R.id.id_password);
        textViewLogin= (TextView) findViewById(R.id.textviewLogin);
        buttonRegister = (Button) findViewById(R.id.btn_signup);
        progressDialog = new ProgressDialog(this);
        buttonRegister.setOnClickListener(this);
        textViewLogin.setOnClickListener(this);
    }

    private void registerUser(){
        final String consumer_no = edit_consumer_no.getText().toString().trim();
        final String lpg_id = edit_lpg_id.getText().toString().trim();

        final String first_name = edit_first_name.getText().toString().trim();
        final String last_name = edit_last_name.getText().toString().trim();
        final String contact_no = edit_contact_no.getText().toString().trim();
        final String email_id = edit_email_id.getText().toString().trim();
        final String state = edit_state.getText().toString().trim();
        final String city = edit_city.getText().toString().trim();
        final String pincode = edit_pincode.getText().toString().trim();
        final String landmark = edit_landmark.getText().toString().trim();
        final String addressline1 = edit_addressline1.getText().toString().trim();
        final String addressline2 = edit_addressline2.getText().toString().trim();
        final String adhaar_no = edit_adhaar_no.getText().toString().trim();
        final String gst_no = edit_gst_no.getText().toString().trim();
        final String password = edit_password.getText().toString().trim();

        progressDialog.setMessage("Registering Consumer....");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params= new HashMap<>();
                params.put("consumer_no",consumer_no);
                params.put("lpg_id",lpg_id);
                params.put("first_name",first_name);
                params.put("last_name",last_name);
                params.put("contact_no",contact_no);
                params.put("email_id",email_id);
                params.put("state",state);
                params.put("city",city);
                params.put("pincode",pincode);
                params.put("landmark",landmark);
                params.put("addressline1",addressline1);
                params.put("addressline2",addressline2);
                params.put("adhaar_no",adhaar_no);
                params.put("gst_no",gst_no);
                params.put("password",password);

                return params;
            }
            /*  @Override
            protected Map<String, String> getParams() throws AuthFailureError{

                Map<String,String> params= new HashMap<>();
                params.put("lpg_id",lpg_id);
                params.put("first_name",first_name);
                params.put("last_name",last_name);
                params.put("contact_no",contact_no);
                params.put("email_id",email_id);
                params.put("state",state);
                params.put("city",city);
                params.put("pincode",pincode);
                params.put("landmark",landmark);
                params.put("addressline1",addressline1);
                params.put("addressline2",addressline2);
                params.put("adhaar_no",adhaar_no);
                params.put("gst_no",gst_no);
                params.put("password",password);
                return params;



            }*/
        };

      /*
      //This lines are deleted after adding RequestHandler Class
      RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
      */

      RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }


    @Override
    public void onClick(View view) {
        if (view == buttonRegister)
            registerUser();
        if (view== textViewLogin)
            startActivity(new Intent(this,LoginActivity.class));

    }
}
