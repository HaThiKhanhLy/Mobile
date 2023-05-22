package com.example.e_cart.authactivities;

import static com.example.e_cart.R.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_cart.Activity.AdminPanel;
import com.example.e_cart.Activity.MainActivity;
import com.example.e_cart.Activity.MainSellerAcoount;
import com.example.e_cart.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    EditText Email, Password;
    Button Login;
    TextView Create, Forget;
    FirebaseAuth firebaseAuth;
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_login);
        Email = findViewById(id.Email);
        Password = findViewById(id.Password);
        Login =findViewById(R.id.Login);
        Create = findViewById(R.id.createaccount);
        Forget = findViewById(R.id.Forget);
        ///
        firebaseAuth= FirebaseAuth.getInstance();



        // Intents for moving


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    LoginUser();
                }catch (Exception e){

                }


            }
        });
        //
        Forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, Forget.class));
            }
        });
        //
       Create.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(LoginActivity.this, Register.class));
           }
       });

    }
    //FOR login to seller or the user and have condition to check email or password is empty or not
    private void LoginUser() {
        if (!Patterns.EMAIL_ADDRESS.matcher(Email.getText().toString()).matches()) {

            Toast.makeText(LoginActivity.this, "Please enter Email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(Password.getText().toString())) {
            Toast.makeText(LoginActivity.this, "Please enter Password", Toast.LENGTH_SHORT).show();

            return;
        }
        firebaseAuth.signInWithEmailAndPassword(Email.getText().toString()
                        , Password.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        try {
                            showDialog();
                            Toast.makeText(LoginActivity.this, "Please wait for few seconds", Toast.LENGTH_SHORT).show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    checkuserType();

                                }
                            }, 2000);


                        } catch (Exception e) {
                            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //This method used in make online and also usd by the login function to make user online and its is third method of login
        private void checkuserType(){
        //
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                reference.orderByChild("uid").equalTo(firebaseAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String accountt = "" + dataSnapshot.child("accounttype").getValue();
                            if (accountt.equals("admin")) {
                                startActivity(new Intent(LoginActivity.this, AdminPanel.class));
                                finish();
                            } else if (accountt.equals("Seller")) {
                                startActivity(new Intent(LoginActivity.this, MainSellerAcoount.class));
                                finish();
                            } else {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                            }


                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(LoginActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();



                    }
                });
            }
    public void showDialog(){
        Dialog alertDialog = new Dialog(LoginActivity.this);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(layout.dialogue);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

}





