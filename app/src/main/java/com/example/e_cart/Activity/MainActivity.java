package com.example.e_cart.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.e_cart.Adapters.Allproducts;
import com.example.e_cart.Adapters.OrderApdater;
import com.example.e_cart.Adapters.OrderUserAdapter;
import com.example.e_cart.Adapters.ProductAdapter;
import com.example.e_cart.Adapters.ProductUser;
import com.example.e_cart.Adapters.Shopadapter;
import com.example.e_cart.Adapters.cata;
import com.example.e_cart.Model.CatagoryModel;
import com.example.e_cart.Model.ModelOrder;
import com.example.e_cart.Model.ModelOrederUser;
import com.example.e_cart.Model.Modelshop;
import com.example.e_cart.Model.ProductModel;
import com.example.e_cart.R;
import com.example.e_cart.authactivities.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView NAME;
    ImageButton message;
    ImageView profileiamge , home , settings , shops , orders, cart;
    FirebaseAuth firebaseAuth;
    String ShopUid;

    //
    RecyclerView orderrecycler;
    OrderUserAdapter orderUserAdapter;
    ArrayList<ModelOrederUser> orderlist;
    RecyclerView shoprecycler;
    private ArrayList<Modelshop>shoplist;
    Shopadapter shopadapter;
    //

//
RecyclerView productrecycler;
    Allproducts allproducts;
    ArrayList<ProductModel> productlist;
//
    RecyclerView catagory;
      cata Cata;
      ArrayList<CatagoryModel> catalist;

    //
RelativeLayout ShopRl ;
RelativeLayout homeRl;
RelativeLayout OrderRl;

//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     setContentView(R.layout.activity_main);
        // Calling of ids
        catagory= findViewById(R.id.recyclerofhome2);
        message = findViewById(R.id.message);
        productrecycler = findViewById(R.id.recyclerofhome1);
        cart = findViewById(R.id.cart);
        orderrecycler=findViewById(R.id.recycleroforder);
        settings = findViewById(R.id.settings);
        shoprecycler= findViewById(R.id.recyclerofshop);
        profileiamge =findViewById(R.id.roundImage);
        homeRl =findViewById(R.id.homeRl);
        ShopRl = findViewById(R.id.shopsRl);
        OrderRl = findViewById(R.id.orderRl);
        home= findViewById(R.id.home);
        orders= findViewById(R.id.orders);
        shops= findViewById(R.id.shopbutt);
    //    Logout= findViewById(R.id.logout);
        NAME = findViewById(R.id.name);
      //  Phone= findViewById(R.id.Phone);
    //   Email= findViewById(R.id.Email);
        //****
        firebaseAuth =FirebaseAuth.getInstance();
        ShopUid = getIntent().getStringExtra("shopid");
        OrderRl.setVisibility(View.GONE);
        ShopRl.setVisibility(View.GONE);
        //

        // calling of functions
        catageory();
        loadcityshops();
        checkuser();
        loadallorders();
        allproducts();



        // Clicks
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Settings.class));
            }
        });
        //

          //
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, order.class));

            }
        });
        //
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                home();
            }
        });
        //
        shops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // show productUI
                ShowproductUi();

            }
        });
        //
        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showorderUi();

            }
        });
    }
    //
    private  void home(){
        ShopRl.setVisibility(View.GONE);
        homeRl.setVisibility(View.VISIBLE);
        OrderRl.setVisibility(View.GONE);
        home.setBackgroundColor(getResources().getColor(R.color.purple_200));
        shops.setBackground(null);
        orders.setBackground(null);

    }
    // this is the method shops in user its name is because of coping from seller
      private void ShowproductUi() {
        homeRl.setVisibility(View.GONE);
      ShopRl.setVisibility(View.VISIBLE);
     OrderRl.setVisibility(View.GONE);
           shops.setBackgroundColor(getResources().getColor(R.color.purple_200));
          home.setBackground(null);
          orders.setBackground(null);
}

    // this is the method to show order in seller
    private void showorderUi() {
        ShopRl.setVisibility(View.GONE);
        homeRl.setVisibility(View.GONE);
        OrderRl.setVisibility(View.VISIBLE);
        home.setBackground(null);
        shops.setBackground(null);
        orders.setBackgroundColor(getResources().getColor(R.color.purple_200));
    }
    // This the method to get data of user
    private  void checkuser(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser==null){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }
        else {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
            reference.orderByChild("uid").equalTo(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String name = (String) dataSnapshot.child("name").getValue();
                        String email = (String) dataSnapshot.child("email").getValue();
                        String phone = (String) dataSnapshot.child("phone").getValue();
                        String accounttype = "" + dataSnapshot.child("accounttype").getValue();
                        String image = (String) dataSnapshot.child("profileimage").getValue();
                        String city =" " + dataSnapshot.child("city").getValue();

                        NAME.setText(name);
//                        Email.setText(email);
                 //       Phone.setText(phone);
                        try {
                            Glide.with(MainActivity.this)
                                    .load(image)
                                    .placeholder(R.drawable.imageholder)
                                    .into(profileiamge);

                        }catch (Exception e){

                        }


                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
    // This the method to get all shops for user
    private void loadcityshops() {
        shoplist = new ArrayList<>();
        shoprecycler.setHasFixedSize(true);
        shopadapter= new Shopadapter(MainActivity.this,shoplist);


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.orderByChild("accounttype").equalTo("Seller")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                  shoplist.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            Modelshop modelshop = dataSnapshot.getValue(Modelshop.class);
                            shoplist.add(modelshop);
                        }
                        shoprecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        shoprecycler.setAdapter(shopadapter);
                        shopadapter.notifyDataSetChanged();



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void loadallorders() {
        orderlist  = new ArrayList<>();
        orderrecycler.setHasFixedSize(true);
        orderrecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("My Orders");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderlist.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String uid = ""+dataSnapshot.getKey();
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("My Orders").child(uid);
                        ref.orderByChild("Order_BY").equalTo(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                ModelOrederUser modelOrederUser = dataSnapshot.getValue(ModelOrederUser.class);
                                orderlist.add(modelOrederUser);
                            }
                            orderUserAdapter=  new OrderUserAdapter(MainActivity.this,orderlist);
                            orderrecycler.setAdapter(orderUserAdapter);
                            orderUserAdapter.notifyDataSetChanged();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    //this method for all products
    private  void allproducts(){
        productrecycler.setHasFixedSize(true);
        productrecycler.setLayoutManager(new GridLayoutManager(this,2));
        productlist = new ArrayList<>();


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("USer");
        databaseReference.child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productlist.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ProductModel productModel =dataSnapshot.getValue(ProductModel.class);
                    productlist.add(productModel);
                }
                allproducts= new Allproducts(MainActivity.this,productlist);
                productrecycler.setAdapter(allproducts);
                allproducts.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    // this method for catageory
    private  void catageory(){
        catagory.setHasFixedSize(true);
        catagory.setLayoutManager(new GridLayoutManager(this,3));
        catalist = new ArrayList<>();
        Cata= new cata(MainActivity.this,catalist);
        catagory.setAdapter(Cata);
        catalist.add(new CatagoryModel("Clothes",R.drawable.clothes));
        catalist.add(new CatagoryModel("Beverages",R.drawable.drink));
        catalist.add(new CatagoryModel("Biscuits & Snack & Chocolates",R.drawable.snack));
        catalist.add(new CatagoryModel("Baby Kids",R.drawable.babyboy));
        catalist.add(new CatagoryModel("Cooking Needs",R.drawable.cooking));
        catalist.add(new CatagoryModel("Frozen Food",R.drawable.frozenfood));
        catalist.add(new CatagoryModel("Pharmacy",R.drawable.pharmacy));
        catalist.add(new CatagoryModel("Vegetables & Fruits",R.drawable.vegetables));
        catalist.add(new CatagoryModel("Beauty & Personal Care",R.drawable.cosmetics));
        catalist.add(new CatagoryModel("Electronics",R.drawable.electronics));
        catalist.add(new CatagoryModel("BreakFast & Dairy",R.drawable.breakfast));
        catalist.add(new CatagoryModel("Other",R.drawable.other));





    }

    }

