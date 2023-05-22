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

import com.example.e_cart.Adapters.ShopOrderAdpater;
import com.example.e_cart.Model.ModelOrderShop;
import com.example.e_cart.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminOrder extends AppCompatActivity {
    String ShopUid;
    ImageButton back;
    RecyclerView orderrecycler;
    ShopOrderAdpater shopOrderAdpater;
    public ArrayList<ModelOrderShop> orderlist;
    EditText search, search1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_order);
        search1=findViewById(R.id.searchtextorder);
        orderrecycler= findViewById(R.id.recycleroforder1);
        back = findViewById(R.id.back);
        ShopUid = getIntent().getStringExtra("SHOP_ID");
        search1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try{
                    shopOrderAdpater.getFilter().filter(charSequence);
                }catch (Exception e){
                    Toast.makeText(AdminOrder.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        showOrders();
        Toast.makeText(AdminOrder.this,""+ShopUid,Toast.LENGTH_SHORT).show();
        //
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    private void showOrders() {
        // Recycler of order
        orderlist  = new ArrayList<>();
        orderrecycler.setHasFixedSize(true);
        orderrecycler.setLayoutManager(new LinearLayoutManager(this));

        // Database reference getting order
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("My Orders");
        databaseReference.child(ShopUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderlist.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ModelOrderShop modelOrderShop= dataSnapshot.getValue(ModelOrderShop.class);
                    orderlist.add(modelOrderShop);

                }
                shopOrderAdpater = new ShopOrderAdpater(AdminOrder.this,orderlist);
                orderrecycler.setAdapter(shopOrderAdpater);
                shopOrderAdpater.notifyDataSetChanged();




            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminOrder.this, error.getMessage(),Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }
}