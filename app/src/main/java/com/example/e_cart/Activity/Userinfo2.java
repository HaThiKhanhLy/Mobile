package com.example.e_cart.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.e_cart.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Userinfo2 extends AppCompatActivity {
    String ShopUid;
    TextView Shopname, checkProduct, ShopPhone, ShopeEmail, ShopAdress , checOrder, ShopFess;
    ImageView shopiamge1;
    ImageButton back;
    Switch toggle1,toggle2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo2);
        Shopname = findViewById(R.id.shopname);
        back =findViewById(R.id.back);
        checOrder = findViewById(R.id.checkorder);
        toggle1 = (Switch) findViewById(R.id.switch1);
        ShopeEmail = findViewById(R.id.Email);
        ShopPhone = findViewById(R.id.Phone);
        ShopAdress = findViewById(R.id.Adress);
        ShopUid = getIntent().getStringExtra("SHOP_ID");
        //
        checOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Userinfo2.this,Adminuserorder.class);
                intent.putExtra("SHOP_ID",ShopUid);
                startActivity(intent);

            }
        });
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
                String shopname = "" + snapshot.child("name").getValue();
                String Email1 = "" + snapshot.child("email").getValue();
                String open = (String) snapshot.child("shopopen").getValue();
                String Profileiamge = "" + snapshot.child("profileimage").getValue();
                Shopname.setText(shopname);
                ShopAdress.setText(Adress);
//                ShopFess.setText(deliveryfess);
                ShopeEmail.setText(Email1);
                ShopPhone.setText(Phone);






            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}