package com.example.e_cart.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_cart.Adapters.UserInfoAdapter;
import com.example.e_cart.Model.ModelOrder;
import com.example.e_cart.Model.UserModel;
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

public class User_Info extends AppCompatActivity {
    String Userby,UserBy2;
    TextView UserId, fullname, address, email, phone, chats, user_status;
    ImageButton Edituser , back;
    RecyclerView userrecycler;
    String userId;
    String Order_id;
    String userBy;
    ArrayList<UserModel> userlist;
    UserInfoAdapter userInfoAdapter;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_infor);
        UserId = findViewById(R.id.userid);
        back= findViewById(R.id.back);
        chats= findViewById(R.id.cahts);
        Edituser= findViewById(R.id.EditUser);
        userrecycler=findViewById(R.id.recyclerofuser);
        fullname = findViewById(R.id.fullname);
        address = findViewById(R.id.address);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        user_status = findViewById(R.id.userStatus);
        userId = getIntent().getStringExtra("userid");
        userBy = getIntent().getStringExtra("userby");
        //
        firebaseAuth = FirebaseAuth.getInstance();
        //
        userinfo();
        showUsers();
        // Clicks
        Edituser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edituserstatus();
            }
        });
        chats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(User_Info.this, null);
                intent.putExtra("orderby",userBy);
                startActivity(intent);
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void edituserstatus() {
        String [] option ={"Active", "Block"};
        AlertDialog.Builder builder = new AlertDialog.Builder(User_Info.this);
        builder.setTitle("User Status").setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String selected = option[i];
                editstatus(selected);

            }
        }).show();
    }

    private void editstatus(String selected) {
        HashMap<String,Object>hashMap =  new HashMap<>();
        hashMap.put("User_Status",""+selected);
        DatabaseReference databaseReference =  FirebaseDatabase.getInstance().getReference("My Users").child(firebaseAuth.getUid());
        databaseReference.child(userId).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
           Toast.makeText(User_Info.this,"User is "+selected,Toast.LENGTH_SHORT).show();
            }
        });

    }

     //This method show the Order in recycler
    private void showUsers() {
        // Recycler of order
        userlist = new ArrayList<>();
        userrecycler.setHasFixedSize(true);
        userInfoAdapter = new UserInfoAdapter( this, userlist);
        userrecycler.setLayoutManager(new LinearLayoutManager(this));
        userInfoAdapter.setAdapter(userInfoAdapter);
        // Database reference getting order
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.child(userBy).child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ModelOrder modelOrder = dataSnapshot.getValue(ModelOrder.class);
                    userlist.add(new UserModel());

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(User_Info.this, error.getMessage(), Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    // this is the method to show products in seller
    private void userinfo() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.orderByChild("uid").equalTo(userBy).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String name = (String) dataSnapshot.child("name").getValue();
                    String email = (String) dataSnapshot.child("email").getValue();
                    String phone = (String) dataSnapshot.child("phone").getValue();
                    String address = "" + dataSnapshot.child("address").getValue();

                    email.setText("Email : "+email);
                    phone.setText("Phone : "+phone);
                    address.setText("Address : "+address);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //
//    private void orderdetail() {
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("My Orders");
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    String uid = "" + dataSnapshot.getKey();
//                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("My Orders").child(uid);
//                    ref.orderByChild("Order_BY").equalTo(orderBy).addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                                String Orderstatus = ""+dataSnapshot.child("Order_Status").getValue();
//                                 orderid = "" + dataSnapshot.child("Order_ID").getValue();
//                                 Orderby2= ""+dataSnapshot.child("Order_BY").getValue();
//                                String amount = "" + dataSnapshot.child("Order_Total").getValue();
//                                String date1 = "" + dataSnapshot.child("Order_Date").getValue();
//                                OrderId.setText("Order NO # "+orderid);
//                                Amount.setText("Total Amount : "+amount);
//                                date.setText("Date : "+date1);
//                                order_status.setText("Order Status : "+Orderstatus);
//                            }
//
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
}






