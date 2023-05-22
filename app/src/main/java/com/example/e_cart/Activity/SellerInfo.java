package com.example.e_cart.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.e_cart.Adapters.AdminPanelShop;
import com.example.e_cart.Adapters.Shopadapter;
import com.example.e_cart.Model.Modelshop;
import com.example.e_cart.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SellerInfo extends AppCompatActivity {
    RecyclerView shoprecycler;
    private ArrayList<Modelshop>shoplist;
AdminPanelShop adminPanelShop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_info);
        shoprecycler =findViewById(R.id.recyclerofshop);
        //

        loadcityshops();
    }
    // This the method to get all shops
    private void loadcityshops() {
        shoplist = new ArrayList<>();
        shoprecycler.setHasFixedSize(true);
        shoprecycler.setLayoutManager(new LinearLayoutManager(SellerInfo.this));

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
                        //   shoplist.notify();
                        adminPanelShop = new AdminPanelShop(SellerInfo.this,shoplist);
                        shoprecycler.setAdapter(adminPanelShop);
                        adminPanelShop.notifyDataSetChanged();



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}