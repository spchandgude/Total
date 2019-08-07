package com.example.total;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RefillOrder extends AppCompatActivity {
    private TextView textViewLpgId, textViewConsumerNo;
    String lpg_id,consumer_no;
    Button btn_send;

    private EditText edit_quantity1, edit_quantity2,edit_quantity3, edit_quantity4, edit_prefered_date, edit_prefered_time, edit_addressline, edit_state,edit_city,edit_pincode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refill_order);

        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }
        textViewLpgId = (TextView) findViewById(R.id.id_lpg_id2);
        textViewConsumerNo = (TextView) findViewById(R.id.id_consumer_no2);

        textViewLpgId.setText(SharedPrefManager.getInstance(this).getLpgId());
        textViewConsumerNo.setText(SharedPrefManager.getInstance(this).getConsumerNo());

        lpg_id=SharedPrefManager.getInstance(this).getLpgId();
        consumer_no=SharedPrefManager.getInstance(this).getConsumerNo();



        edit_quantity1= (EditText) findViewById(R.id.id_quantity1);
        edit_quantity2= (EditText) findViewById(R.id.id_quantity2);
        edit_quantity3= (EditText) findViewById(R.id.id_quantity3);
        edit_quantity4= (EditText) findViewById(R.id.id_quantity4);
        edit_prefered_date =(EditText) findViewById(R.id.id_prefered_date);
        edit_prefered_time = (EditText) findViewById(R.id.id_prefered_time);
        edit_addressline= (EditText) findViewById(R.id.id_shipping_address);
        edit_state =(EditText) findViewById(R.id.id_consumer_state2);
        edit_city = (EditText) findViewById(R.id.id_consumer_city2);
        edit_pincode = (EditText) findViewById(R.id.id_consumer_pincode2);

    }

    public void set(View view) {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);

        if(permissionCheck== PackageManager.PERMISSION_GRANTED){
            sendMessage();
        }
        else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS}, 0);
        }
    }


    private void sendMessage() {
        String quantity1= edit_quantity1.getText().toString().trim();
        String quantity2= edit_quantity2.getText().toString().trim();
        String quantity3= edit_quantity3.getText().toString().trim();
        String quantity4= edit_quantity4.getText().toString().trim();
        String prefered_date= edit_prefered_date.getText().toString().trim();
        String prefered_time= edit_prefered_time.getText().toString().trim();
        String addressline= edit_addressline.getText().toString().trim();
        String state= edit_state.getText().toString().trim();
        String city= edit_city.getText().toString().trim();
        String pincode= edit_pincode.getText().toString().trim();

        String phoneNo = Constants.MOBILE_NO;
        String msg="This order is requested with\n"+ "LPG ID:"+lpg_id + " and Consumer no:"+ consumer_no + " \n8 kg quantity:"+quantity1+" \n12 kg quantity:"+quantity2+" \n17 kg quantity:"+quantity3+ " \n33 kg quantity:"+quantity4 ;

        if(!edit_quantity1.getText().toString().equals("") && !edit_quantity2.getText().toString().equals("") || !edit_quantity3.getText().toString().equals("") || !edit_quantity4.getText().toString().equals("")
                || !edit_prefered_date.getText().toString().equals("") && !edit_prefered_time.getText().toString().equals("") && !edit_addressline.getText().toString().equals("")
                && !edit_state.getText().toString().equals("") && !edit_city.getText().toString().equals("") && !edit_pincode.getText().toString().equals("")) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(this, "Order requested successfully", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Please fill all details",Toast.LENGTH_SHORT).show();
        }

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case 0:
                if(grantResults.length>=0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    sendMessage();
                }
                else{
                    Toast.makeText(this, "Request failed!!", Toast.LENGTH_SHORT).show();
                }
        }
    }



}
