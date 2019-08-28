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

public class complaint extends AppCompatActivity {

    private TextView textViewLpgId, textViewConsumerNo;
    String lpg_id,consumer_no;
    Button btn_send;

    private EditText edit_complaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

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



        edit_complaint= (EditText) findViewById(R.id.id_complaint);


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
        String complaint= edit_complaint.getText().toString().trim();


        String phoneNo = Constants.MOBILE_NO;
        String msg="This compalint has been submitted with\n"+ "LPG ID:"+lpg_id + " and Consumer no:"+ consumer_no + "\n"+complaint ;

        if(!edit_complaint.getText().toString().equals("") ) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(this, "Complaint sent successfully", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Please type something",Toast.LENGTH_SHORT).show();
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
