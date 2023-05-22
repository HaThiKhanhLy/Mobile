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

import com.example.e_cart.Adapters.OrderUserAdapter;
import com.example.e_cart.Adapters.ShopOrderAdpater;
import com.example.e_cart.Model.ModelOrderShop;
import com.example.e_cart.Model.ModelOrederUser;
import com.example.e_cart.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Adminuserorder extends AppCompatActivity {
    String ShopUid;
    ImageButton back;
    RecyclerView orderrecycler;
    EditText search, search1;
    OrderUserAdapter orderUserAdapter;
    ArrayList<ModelOrederUser> orderlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminuserorder);
        search1=findViewById(R.id.searchtextorder);
        orderrecycler= findViewById(R.id.recycleroforder2);
        back = findViewById(R.id.back);
        ShopUid = getIntent().getStringExtra("SHOP_ID");
       loadallorders();
        Toast.makeText(Adminuserorder.this,""+ShopUid,Toast.LENGTH_SHORT).show();
        //
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
    private void loadallorders() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("My Orders");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderlist  = new ArrayList<>();
                orderrecycler.setHasFixedSize(true);
                orderrecycler.setLayoutManager(new LinearLayoutManager(Adminuserorder.this));
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String uid = ""+dataSnapshot.getKey();
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("My Orders").child(uid);
                    ref.orderByChild("Order_BY").equalTo(ShopUid).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                ModelOrederUser modelOrederUser = dataSnapshot.getValue(ModelOrederUser.class);
                                orderlist.add(modelOrederUser);
                            }
                            orderUserAdapter=  new OrderUserAdapter(Adminuserorder.this,orderlist);
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
}