package com.example.e_cart.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.e_cart.Adapters.OrderApdater;
import com.example.e_cart.Adapters.OrderUserAdapter;
import com.example.e_cart.Adapters.ProductAdapter;
import com.example.e_cart.Adapters.ShopOrderAdpater;
import com.example.e_cart.Model.ModelOrder;
import com.example.e_cart.Model.ModelOrderShop;
import com.example.e_cart.Model.ModelOrederUser;
import com.example.e_cart.Model.ProductModel;
import com.example.e_cart.R;
import com.example.e_cart.authactivities.LoginActivity;
import com.example.e_cart.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainSellerAcoount extends AppCompatActivity {
TextView NAME, SHopName, Email, product , order ,showingAll;
ImageView shopimage,AddProduct;
    ImageButton message;
EditText search, search1;
ImageButton Logout, catagory,catagory1;

FirebaseAuth firebaseAuth;
//Recycler
RecyclerView productrecycler;
private ArrayList<ProductModel>productlist;
ProductAdapter productAdapter;
RecyclerView orderrecycler;
ShopOrderAdpater shopOrderAdpater;
   public ArrayList<ModelOrderShop> orderlist;
//
    RelativeLayout productRl;
    RelativeLayout OrderRl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_seller_acoount);
        //
        orderrecycler= findViewById(R.id.recycleroforder1);
        productrecycler=findViewById(R.id.recyclerofproduct);
        search = findViewById(R.id.searchtext);
        catagory =findViewById(R.id.catogery);
        catagory1=findViewById(R.id.catogeryorder);
        productRl = findViewById(R.id.productui);
        OrderRl = findViewById(R.id.ordertui);
        product = findViewById(R.id.product);
        order =findViewById(R.id.order);
        Logout= findViewById(R.id.logout);
        message = findViewById(R.id.message);
        AddProduct= findViewById(R.id.addproduct);
        NAME = findViewById(R.id.Name);
        Email =findViewById(R.id.Email);
        SHopName=findViewById(R.id.Shopname);
        shopimage= findViewById(R.id.roundImage);
        firebaseAuth =FirebaseAuth.getInstance();
        search1=findViewById(R.id.searchtextorder);
        //seraching
        search1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try{
                    shopOrderAdpater.getFilter().filter(charSequence);
                }catch (Exception e){
                    Toast.makeText(MainSellerAcoount.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try{
                    productAdapter.getFilter().filter(charSequence);
                }catch (Exception e){
                    Toast.makeText(MainSellerAcoount.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        //

            productrecycler.setHasFixedSize(true);
            productrecycler.setLayoutManager(new LinearLayoutManager(this));
            
        //
        loadallproduct();
        checkuser();
        showOrders(); OrderRl.setVisibility(View.GONE);
        //
        shopimage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                startActivity(new Intent(MainSellerAcoount.this,EditSeller.class));
                Toast.makeText(MainSellerAcoount.this, "Please Wait", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        //

        // Clicks

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                checkuser();
            }
        });
        //
        AddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainSellerAcoount.this,ProductAdd.class));
            }
        });
        // on click of tab of the product
        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // show productUI
                ShowproductUi();

            }
        });
        // onclcik of the productcategory button
        catagory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainSellerAcoount.this);
                builder.setTitle("Chose Category").setItems(Constants.category, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String selected = Constants.category[i];
                        search.setText(selected);
                    }
                }).show();
            }
        });
        //
        catagory1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 String [] option ={"Shipped", "Completed","Cancelled"};
                AlertDialog.Builder builder = new AlertDialog.Builder(MainSellerAcoount.this);
                builder.setTitle("Filter Orders").setItems(option, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String selected = option[i];
                        search1.setText(selected);

                    }
                }).show();
            }
        });
    //      onclick of the order
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showorderUi();

            }
        });
        //





    }
    //
    private void loadallproduct() {
      
        productlist = new ArrayList<>();
       
        //
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("USer");
        databaseReference.child("Products").orderByChild("uid").equalTo(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                productlist.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ProductModel productModel =dataSnapshot.getValue(ProductModel.class);
                   productlist.add(productModel);
                }
                productAdapter = new ProductAdapter(MainSellerAcoount.this,productlist);
                productrecycler.setAdapter(productAdapter);
                productAdapter.notifyDataSetChanged();


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainSellerAcoount.this, error.getMessage(),Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    //
    private void loadfilterproducts(String selected) {
        productlist = new ArrayList<>();
        //
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("USer");
        databaseReference.child(firebaseAuth.getUid()).child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String productcategory = ""+dataSnapshot.child("productCategory").getValue();
                    if(selected.equals(productcategory)) {
                        ProductModel productModel = dataSnapshot.getValue(ProductModel.class);
                        productlist.add(productModel);

                        //
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainSellerAcoount.this, error.getMessage(),Toast.LENGTH_SHORT)
                        .show();
            }
        });

    }



    // this is the method to show products in seller
    private void ShowproductUi() {
        productRl.setVisibility(View.VISIBLE);
        OrderRl.setVisibility(View.GONE);
        product.setBackgroundResource(R.drawable.shap_retc);
        product.setTextColor(getResources().getColor(R.color.black));

        order.setTextColor(getResources().getColor(R.color.white));
        order.setBackgroundColor(getResources().getColor(R.color.purple_200));
    }


    // this is the method to show order in seller
    private void showorderUi() {
        productRl.setVisibility(View.GONE);
        OrderRl.setVisibility(View.VISIBLE);
        order.setBackgroundResource(R.drawable.shap_retc);
        order.setTextColor(getResources().getColor(R.color.black));

        product.setTextColor(getResources().getColor(R.color.white));
        product.setBackgroundColor(getResources().getColor(R.color.purple_200));
    }


    // method for checking userr
    private  void checkuser(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser==null){
            startActivity(new Intent(MainSellerAcoount.this, LoginActivity.class));
            finish();
        }
        else {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
            reference.orderByChild("uid").equalTo(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                      String name = (String) dataSnapshot.child("name").getValue();
                      String accounttype = "" + dataSnapshot.child("accounttype").getValue();
                        String shopname = "" + dataSnapshot.child("shopname").getValue();
                        String Email1 = "" + dataSnapshot.child("email").getValue();
                        String Profileiamge = "" + dataSnapshot.child("profileimage").getValue();

                      // set all items
                        NAME.setText(name +"  "+ accounttype);
                        Email.setText(Email1);
                        SHopName.setText(shopname);



                        try{

                            Glide
                                    .with(MainSellerAcoount.this)
                                    .load(Profileiamge)
                                    .placeholder(R.drawable.imageholder)
                                    .into(shopimage);
                        } catch (Exception e){
                            Toast.makeText(MainSellerAcoount.this,"Failed to load Image",Toast.LENGTH_SHORT).show();
                        }




                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
    // method for loading order
    private void showOrders() {
        // Recycler of order
        orderlist  = new ArrayList<>();
        orderrecycler.setHasFixedSize(true);
        orderrecycler.setLayoutManager(new LinearLayoutManager(this));

        // Database reference getting order
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("My Orders");
        databaseReference.child(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderlist.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ModelOrderShop modelOrderShop= dataSnapshot.getValue(ModelOrderShop.class);
                    orderlist.add(modelOrderShop);

                }
                shopOrderAdpater = new ShopOrderAdpater(MainSellerAcoount.this,orderlist);
                orderrecycler.setAdapter(shopOrderAdpater);
                shopOrderAdpater.notifyDataSetChanged();




            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainSellerAcoount.this, error.getMessage(),Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }
    // method for showing filter orders



}