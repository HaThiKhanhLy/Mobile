package com.example.e_cart.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.example.e_cart.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Adminshop extends AppCompatActivity {
String ShopUid;
    TextView Shopname, checkProduct, ShopPhone, ShopeEmail, ShopAdress , checOrder, Chat;
    ImageView shopiamge1;
    ImageButton back;
    Switch toggle1,toggle2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asminshop);
        Shopname = findViewById(R.id.shopname);
        back =findViewById(R.id.back);
        checkProduct = findViewById(R.id.checkproduct);
        Chat=findViewById(R.id.Chat);
        checOrder = findViewById(R.id.checkorder);
        toggle1 = (Switch) findViewById(R.id.switch1);
        toggle2 = (Switch) findViewById(R.id.switch2);
        ShopeEmail = findViewById(R.id.Email);
        ShopPhone = findViewById(R.id.Phone);
        shopiamge1 = findViewById(R.id.shopimage);
        ShopAdress = findViewById(R.id.Adress);
        ShopUid = getIntent().getStringExtra("SHOP_ID");
        //

        //
        checkProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Adminshop.this,Adminproduts.class);
                intent.putExtra("SHOP_ID",ShopUid);
                startActivity(intent);
            }
        });
        //
        checOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Intent intent = new Intent(Adminshop.this,AdminOrder.class);
              intent.putExtra("SHOP_ID",ShopUid);
              startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        //
        toggle1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                    HashMap<String,Object> hashMap =  new HashMap<>();
                    hashMap.put("Blocked","Blocked");
                    DatabaseReference databaseReference =  FirebaseDatabase.getInstance().getReference("Users").child(ShopUid);
                    databaseReference.updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(Adminshop.this,"Seller is Blocked",Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(Adminshop.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    // The toggle is disabled
                    HashMap<String,Object> hashMap =  new HashMap<>();
                    hashMap.put("Blocked","Unblocked");
                    DatabaseReference databaseReference =  FirebaseDatabase.getInstance().getReference("Users").child(ShopUid);
                    databaseReference.updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(Adminshop.this,"Seller is Unblocked",Toast.LENGTH_SHORT).show();

                        }
                    });


                }
            }
        });
        //
        toggle2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // The toggle is enabled
                } else {
                    // The toggle is disabled
                }
            }
        });
           //
        loadShopdetail();
    }
    private void loadShopdetail() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(ShopUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String Adress = (String) snapshot.child("address").getValue();
                String deliveryfess =(String) snapshot.child("deliveryprice").getValue();
                String Phone = "" +snapshot.child("phone").getValue();
                String shopname = "" + snapshot.child("shopname").getValue();
                String Email1 = "" + snapshot.child("email").getValue();
                String open = (String) snapshot.child("shopopen").getValue();
                String Profileiamge = "" + snapshot.child("profileimage").getValue();
                Shopname.setText(shopname);
                ShopAdress.setText(Adress);
//                ShopFess.setText(deliveryfess);
                ShopeEmail.setText(Email1);
                ShopPhone.setText(Phone);

                try {
                    Glide.with(Adminshop.this)
                            .load(Profileiamge).
                            placeholder(R.drawable.shop2).into(shopiamge1);
                }catch (Exception e){}





            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}