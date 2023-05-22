package com.example.e_cart.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.e_cart.R;

public class AdminPanel extends AppCompatActivity {
    LinearLayout sellerinfo , Complaints , userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        sellerinfo = findViewById(R.id.Sellerinfo);
        Complaints= findViewById(R.id.complainst);
        userInfo= findViewById(R.id.User);

        //
        sellerinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminPanel.this,SellerInfo.class));

            }
        });
        //

        //
        userInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminPanel.this,UserInfo.class));

            }
        });
    }
}