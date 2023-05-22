package com.example.e_cart.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.e_cart.Adapters.AdminPanelShop;
import com.example.e_cart.Adapters.userinfo;
import com.example.e_cart.Model.Modelshop;
import com.example.e_cart.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserInfo extends AppCompatActivity {
    RecyclerView shoprecycler;
    private ArrayList<Modelshop>shoplist;
    userinfo Userinfo;
    ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        shoprecycler =findViewById(R.id.recyclerofshop);
        back= findViewById(R.id.back);
        //
        loaduser();
        //
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    private void loaduser() {
        shoplist = new ArrayList<>();
        shoprecycler.setHasFixedSize(true);
        shoprecycler.setLayoutManager(new LinearLayoutManager(UserInfo.this));

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.orderByChild("accounttype").equalTo("User")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        shoplist.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            Modelshop modelshop = dataSnapshot.getValue(Modelshop.class);
                            shoplist.add(modelshop);
                        }
                        //   shoplist.notify();
                        Userinfo= new userinfo(UserInfo.this,shoplist);
                        shoprecycler.setAdapter(Userinfo);
                        Userinfo.notifyDataSetChanged();



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}