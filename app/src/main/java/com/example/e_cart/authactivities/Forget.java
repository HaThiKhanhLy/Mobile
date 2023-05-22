package com.example.e_cart.authactivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.e_cart.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class Forget extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    EditText Email;
    Button recover;
     ImageButton Back;
     private ProgressDialog progressDialog;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        Email = findViewById(R.id.Email);
    Back = findViewById(R.id.back);
    recover = findViewById(R.id.Recoverbutton);
    //

     progressDialog = new ProgressDialog(this);
     progressDialog.setTitle("Confirm");
        firebaseAuth=FirebaseAuth.getInstance();
    // Intents
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onBackPressed();
            }
        });


        // on click of forget
        recover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forget();
            }
        });

    }
private void forget(){
    if(!Patterns.EMAIL_ADDRESS.matcher(Email.getText().toString()).matches()) {

        Toast.makeText(Forget.this,"Please enter EMail",Toast.LENGTH_SHORT).show();
        return;
    }
      firebaseAuth.sendPasswordResetEmail(Email.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
          @Override
          public void onSuccess(Void unused) {
              progressDialog.show();
              Toast.makeText(Forget.this," EMail Sent",Toast.LENGTH_SHORT).show();

          }
      }).addOnFailureListener(new OnFailureListener() {
          @Override
          public void onFailure(@NonNull Exception e) {
              Toast.makeText(Forget.this,e.getMessage(),Toast.LENGTH_SHORT).show();

          }
      });



}

}

