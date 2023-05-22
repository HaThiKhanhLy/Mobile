package com.example.e_cart.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_cart.Adapters.OrderDetailAdapter;
import com.example.e_cart.Model.ModelOrder;
import com.example.e_cart.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class Order_Detail_Shop extends AppCompatActivity {
    String orderBy,Orderby2;
    TextView OrderId, date, order_status, buyerEmail, buyerPhone, Amount, Adress ,chats;
    ImageButton Editorder , back;
    RecyclerView orderrecycler;
    String orderid;
    String Order_id;
    ArrayList<ModelOrder> orderlist;
 OrderDetailAdapter orderApdater;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail_shop);
        OrderId = findViewById(R.id.orderid);
        back= findViewById(R.id.back);
        chats= findViewById(R.id.cahts);
        Editorder= findViewById(R.id.EditOrder);
        orderrecycler=findViewById(R.id.recyceroforder);
        date = findViewById(R.id.date);
        order_status = findViewById(R.id.Status);
        buyerEmail = findViewById(R.id.BuyerEmail);
        buyerPhone = findViewById(R.id.BuyerPhone);
        Amount = findViewById(R.id.Amount);
        Adress = findViewById(R.id.Delivery_Address);
        Order_id = getIntent().getStringExtra("orderid");
        orderBy = getIntent().getStringExtra("orderby");
        //
        firebaseAuth = FirebaseAuth.getInstance();
        //
        orderdetail();
        userdetail();
        showOrders();
        // Clicks
        Editorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editorderstatus();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void editorderstatus() {
        String [] option ={"Progress", "Completed","Cancelled"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Order_Detail_Shop.this);
        builder.setTitle("Order Status").setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String selected = option[i];
                editstatus(selected);

            }
        }).show();
    }

    private void editstatus(String selected) {
        HashMap<String,Object>hashMap =  new HashMap<>();
        hashMap.put("Order_Status",""+selected);
        DatabaseReference databaseReference =  FirebaseDatabase.getInstance().getReference("My Orders").child(firebaseAuth.getUid());
        databaseReference.child(orderid).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
           Toast.makeText(Order_Detail_Shop.this,"Your Order is "+selected,Toast.LENGTH_SHORT).show();
            }
        });

    }

    // This method show the Order in recycler
    private void showOrders() {
        // Recycler of order
        orderlist = new ArrayList<>();
        orderrecycler.setHasFixedSize(true);
        orderApdater = new OrderDetailAdapter( this, orderlist);
        orderrecycler.setLayoutManager(new LinearLayoutManager(this));
        orderrecycler.setAdapter(orderApdater);
        // Database reference getting order
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Orders");
        databaseReference.child(orderBy).child("User Order").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ModelOrder modelOrder = dataSnapshot.getValue(ModelOrder.class);
                    orderlist.add(modelOrder);

                }
                orderApdater.notifyDataSetChanged();


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Order_Detail_Shop.this, error.getMessage(), Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    // this is the method to show products in seller
    private void userdetail() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.orderByChild("uid").equalTo(orderBy).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String name = (String) dataSnapshot.child("name").getValue();
                    String email = (String) dataSnapshot.child("email").getValue();
                    String phone = (String) dataSnapshot.child("phone").getValue();
                    String accounttype = "" + dataSnapshot.child("accounttype").getValue();
                    String image = (String) dataSnapshot.child("profileimage").getValue();
                    String city = "" + dataSnapshot.child("city").getValue();
                    String address = "" + dataSnapshot.child("address").getValue();

                    buyerEmail.setText("Email : "+email);
                    buyerPhone.setText("Phone : "+phone);
                    Adress.setText("Address : "+address);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //
    private void orderdetail() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("My Orders");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String uid = "" + dataSnapshot.getKey();
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("My Orders").child(uid);
                    ref.orderByChild("Order_BY").equalTo(orderBy).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                String Orderstatus = ""+dataSnapshot.child("Order_Status").getValue();
                                 orderid = "" + dataSnapshot.child("Order_ID").getValue();
                                 Orderby2= ""+dataSnapshot.child("Order_BY").getValue();
                                String amount = "" + dataSnapshot.child("Order_Total").getValue();
                                String date1 = "" + dataSnapshot.child("Order_Date").getValue();
                                OrderId.setText("Order NO # "+orderid);
                                Amount.setText("Total Amount : "+amount);
                                date.setText("Date : "+date1);
                                order_status.setText("Order Status : "+Orderstatus);
                            }


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






