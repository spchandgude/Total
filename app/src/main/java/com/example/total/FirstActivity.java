 package com.example.total;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

 public class FirstActivity extends AppCompatActivity implements View.OnClickListener{
    private Button refillOrder,complaint;                           // Button declaration
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }

        refillOrder=(Button) findViewById(R.id.refill_order);       //casting button

        complaint=(Button) findViewById(R.id.complaint);            //casting button
        refillOrder.setOnClickListener(this);                       //setting listener

        complaint.setOnClickListener(this);                         //setting listener

    }

     @Override
     public boolean onCreateOptionsMenu(Menu menu) {                //option menu
         getMenuInflater().inflate(R.menu.menu, menu);
         return true;
     }

     @Override
     public boolean onOptionsItemSelected(@NonNull MenuItem item) {         //menu function for logout
        switch (item.getItemId()){
            case R.id.menuLogout:
                SharedPrefManager.getInstance(this).logout();
                finish();
                startActivity(new Intent(this,LoginActivity.class));
                break;



        }
         return true;

     }

     @Override
     public void onClick(View view) {                   //Intent
         if(view==refillOrder){
             startActivity(new Intent(this,RefillOrder.class));
         }

         if(view==complaint){
             startActivity(new Intent(this,complaint.class));
         }
     }
 }
