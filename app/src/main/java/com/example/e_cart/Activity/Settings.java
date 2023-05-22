package com.example.e_cart.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.e_cart.R;
import com.example.e_cart.authactivities.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class Settings extends AppCompatActivity {
TextView EditProfile , Signout;
ImageButton back;
FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        EditProfile = findViewById(R.id.EditProfle);
        Signout = findViewById(R.id.signout);
        back =findViewById(R.id.back);
        firebaseAuth= FirebaseAuth.getInstance();
        EditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Settings.this, EditProfile.class));
            }
        });
        //
        Signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                startActivity(new Intent(Settings.this, LoginActivity.class));
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}