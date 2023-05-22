package com.example.e_cart.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.e_cart.Adapters.OrderApdater;
import com.example.e_cart.Adapters.ProductAdapter;
import com.example.e_cart.Adapters.ProductUser;
import com.example.e_cart.Model.ModelOrder;
import com.example.e_cart.Model.Modelshop;
import com.example.e_cart.Model.ProductModel;
import com.example.e_cart.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;

import p32929.androideasysql_library.Column;
import p32929.androideasysql_library.EasyDB;

public class ShopDetail extends AppCompatActivity {
    ImageView shopiamge1 , shopimage2;
    EditText search;
    String ShopUid;
    String myaddress;
    TextView Shopname, Showingall , ShopPhone, ShopeEmail, ShopAdress , ShopOpen, ShopFess;
    ImageButton back, call ,map, cart, category;
    FirebaseAuth firebaseAuth;
    String Shopuid1 ;
    public double total= 0.00;
    //
    RecyclerView productrecycler;
   ProductUser productUser;
    ArrayList<ProductModel> productlist;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);
        shopiamge1 = findViewById(R.id.shopimage);
        shopimage2 = findViewById(R.id.shopimage2);
        Shopname = findViewById(R.id.shopname);
        ShopeEmail = findViewById(R.id.Email);
        Showingall = findViewById(R.id.SHOWL);
        ShopAdress = findViewById(R.id.Adress);
        productrecycler =findViewById(R.id.recyclerofshopdet);
       ShopFess = findViewById(R.id.Deliveryfees);
        ShopOpen = findViewById(R.id.Open);
        call= findViewById(R.id.phonebu);
        search =findViewById(R.id.search);
          back = findViewById(R.id.back);
          map= findViewById(R.id.map);
          category = findViewById(R.id.cata);
        cart= findViewById(R.id.cart);
          ShopPhone = findViewById(R.id.Phone);
          //
          firebaseAuth= FirebaseAuth.getInstance();
          ShopUid = getIntent().getStringExtra("SHOP_ID");

        //
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try{
                    productUser.getFilter().filter(charSequence);
                }catch (Exception e){
                    Toast.makeText(ShopDetail.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //
        //



        //
        loadmyinfo();
        loadproducts();
        loadShopdetail();
        //

//click
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(ShopDetail.this,order.class);
                intent.putExtra("Shop_UID",Shopuid1);
                startActivity(intent);


            }
        });
        //
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        //
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openmap();

            }
        });
        //
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opnecall();



            }
        });

    }

    private void opnecall() {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", ShopPhone.getText().toString(), null));
        startActivity(intent);
    }


    private void openmap() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://maps.google.co.in/maps?saddr="+myaddress+"&daddr="+ShopAdress.getText().toString()));

            startActivity(intent);


    }

    //
    private void loadmyinfo() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.orderByChild("uid").equalTo(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String name = (String) dataSnapshot.child("name").getValue();
                    String adress= " "+ dataSnapshot.child("address").getValue();
                    String email = (String) dataSnapshot.child("email").getValue();
                    String phone = (String) dataSnapshot.child("phone").getValue();
                    String accounttype = "" + dataSnapshot.child("accounttype").getValue();
                    String image = (String) dataSnapshot.child("profileimage").getValue();
                    String city =" " + dataSnapshot.child("city").getValue();

                    //
                    myaddress = adress.toString();


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

//
    private void loadShopdetail() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(ShopUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String Adress = (String) snapshot.child("address").getValue();
                String deliveryfess =(String) snapshot.child("deliveryprice").getValue();
                String Phone = "" +snapshot.child("phone").getValue();
                String shopname = "" + snapshot.child("shopname").getValue();
                Shopuid1 = ""+ snapshot.child("uid").getValue();
                        String Email1 = "" + snapshot.child("email").getValue();
                String open = (String) snapshot.child("shopopen").getValue();
                String Profileiamge = "" + snapshot.child("profileimage").getValue();
                Shopname.setText(shopname);
                ShopAdress.setText(Adress);
                ShopFess.setText(deliveryfess);
                ShopeEmail.setText(Email1);
                ShopPhone.setText(Phone);

                if(open=="true"){
                    ShopOpen.setText("Open");
                }else{
                    ShopOpen.setText("Closed");
                }
                try {
                    Glide.with(ShopDetail.this)
                            .load(Profileiamge).into(shopiamge1);
                }catch (Exception e){}





            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
//
    private void loadproducts() {
        //
        productlist = new ArrayList<>();
        productrecycler.setHasFixedSize(true);
        productrecycler.setLayoutManager(new GridLayoutManager(ShopDetail.this,2));



        //
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("USer");
        databaseReference.child("Products").orderByChild("uid").equalTo(ShopUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productlist.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ProductModel productModel =dataSnapshot.getValue(ProductModel.class);
                    productlist.add(productModel);
                }
                productUser = new ProductUser(ShopDetail.this,productlist);
                productrecycler.setAdapter(productUser);
                productUser.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        }

}