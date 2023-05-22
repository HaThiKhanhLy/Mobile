package com.example.e_cart.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.e_cart.Adapters.ProductAdapter;
import com.example.e_cart.Model.ProductModel;
import com.example.e_cart.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Adminproduts extends AppCompatActivity {
    RecyclerView productrecycler;
    String ShopUid;
    ImageButton back;
    private ArrayList<ProductModel> productlist;
    ProductAdapter productAdapter;
    EditText search, search1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminproduts);
        productrecycler=findViewById(R.id.recyclerofproduct);
        back= findViewById(R.id.back);
        search = findViewById(R.id.searchtext);
        ShopUid = getIntent().getStringExtra("SHOP_ID");
        productrecycler.setHasFixedSize(true);
        productrecycler.setLayoutManager(new LinearLayoutManager(this));
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try{
                    productAdapter.getFilter().filter(charSequence);
                }catch (Exception e){
                    Toast.makeText(Adminproduts.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        loadallproduct();
        //
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }
    //
    private void loadallproduct() {

        productlist = new ArrayList<>();

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
                productAdapter = new ProductAdapter(Adminproduts.this,productlist);
                productrecycler.setAdapter(productAdapter);
                productAdapter.notifyDataSetChanged();


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Adminproduts.this, error.getMessage(),Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }
}