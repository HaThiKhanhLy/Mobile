package com.example.e_cart.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.e_cart.Adapters.Allproducts;
import com.example.e_cart.Model.ProductModel;
import com.example.e_cart.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Catageoryproduct extends AppCompatActivity {
    RecyclerView productrecycler;
    Allproducts allproducts;
    ArrayList<ProductModel> productlist;
    String name;
//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catageoryproduct);
        name = getIntent().getStringExtra("name");
        productrecycler = findViewById(R.id.recyclerofhome1);
        allproducts();

    }

    private void allproducts() {
        productrecycler.setHasFixedSize(true);
        productrecycler.setLayoutManager(new GridLayoutManager(this, 2));
        productlist = new ArrayList<>();
        allproducts = new Allproducts(Catageoryproduct.this, productlist);
        productrecycler.setAdapter(allproducts);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("USer").child("Products");
        databaseReference.orderByChild("productCategory").equalTo(name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ProductModel productModel = dataSnapshot.getValue(ProductModel.class);
                    productlist.add(productModel);
                }
                allproducts.notifyDataSetChanged();
                Toast.makeText(Catageoryproduct.this,name,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
