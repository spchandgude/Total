package com.example.total;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class RefillOrder extends AppCompatActivity {
    private TextView textViewLpgId, textViewConsumerNo;
    String lpg_id,consumer_no;

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
    }
}
