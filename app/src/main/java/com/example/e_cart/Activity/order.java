package com.example.e_cart.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.e_cart.Adapters.OrderApdater;
import com.example.e_cart.Model.ModelOrder;
import com.example.e_cart.Model.ProductModel;
import com.example.e_cart.R;
import com.example.e_cart.authactivities.LoginActivity;
import com.example.e_cart.authactivities.Register;
import com.example.e_cart.authactivities.Splash;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firestore.v1.StructuredQuery;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class order extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    RecyclerView orderrecycler;
    TextView shop_name;
   public TextView  Subtoatal;
    TextView subtoatalprice;
    TextView deliveryprice;
    TextView  Totalprice;
    Button confirm;
    String ShopUid ;
    // Recycler
    OrderApdater orderApdater;
    ArrayList<ModelOrder> orderlist;

    String Shopname;
    Float TotalAmount;
    String subtoatalprice1;

// Progress

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order);
    //   shop_name = findViewById(R.id.shopnameorder);
       Subtoatal =findViewById(R.id.subtotal);
       subtoatalprice =findViewById(R.id.subtotalprice);
      // deliveryprice =findViewById(R.id.deliverypriceorder);
        confirm =findViewById(R.id.confirm);
        orderrecycler = findViewById(R.id.recycleroforder);
        // For progress Dialogue

        // """""
        firebaseAuth =FirebaseAuth.getInstance();
       // ShopUid = getIntent().getStringExtra("Shop_UID");

        // calling of functions
       // loadShopdetail();
        showOrders();
        // Click on Checkout button
          confirm.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                 movingtomain();
              }
          });
    }
      // method for Moving  in the main user activity


    // Method for calling Shop details
   // private void loadShopdetail() {
     //   DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
       // reference.child(ShopUid).addValueEventListener(new ValueEventListener() {
          //  @Override
          //  public void onDataChange(@NonNull DataSnapshot snapshot) {
               // String Adress = (String) snapshot.child("address").getValue();
             //   String deliveryfess = (String) snapshot.child("deliveryprice").getValue();
            //    String Phone = "" + snapshot.child("phone").getValue();
           //     Shopname = "" + snapshot.child("shopname").getValue();
           //     String Email1 = "" + snapshot.child("email").getValue();
           //     String open = (String) snapshot.child("shopopen").getValue();
           //     String Profileiamge = "" + snapshot.child("profileimage").getValue();

            //    shop_name.setText(Shopname);
            //    deliveryprice.setText(deliveryfess);


           // }

           //// @Override
           // public void onCancelled(@NonNull DatabaseError error) {

          //  }
       // });


   // }
    // This method show the Order in recycler
    private void showOrders() {
        // Recycler of order
        orderlist  = new ArrayList<>();
        orderrecycler.setHasFixedSize(true);

        orderrecycler.setLayoutManager(new LinearLayoutManager(this));

        // Database reference getting order
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Orders");
        databaseReference.child(firebaseAuth.getCurrentUser().getUid()).child("User Order").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                orderlist.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                   ModelOrder modelOrder = dataSnapshot.getValue(ModelOrder.class);
                     ShopUid = modelOrder.getShopid();
                    orderlist.add(modelOrder);

                }
                orderApdater =  new OrderApdater(subtoatalprice,order.this,orderlist);
                orderrecycler.setAdapter(orderApdater);
                orderApdater.notifyDataSetChanged();




            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(order.this, error.getMessage(),Toast.LENGTH_SHORT)
                        .show();
            }
        });
        subtoatalprice1 = subtoatalprice.getText().toString();
    }
    ///
    private void movingtomain() {



        // Data send to Firebase
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String timestemp = "" + System.currentTimeMillis();
        HashMap<String, Object> hashMap =  new HashMap<>();
        hashMap.put("Order_ID", "" + timestemp);
        hashMap.put("Order_Time", "" + timestemp);
        hashMap.put("Order_Date", "" + currentDate);
        hashMap.put("Shop_name",Shopname);
        hashMap.put("Order_Status", "" +"Progress");
        hashMap.put("Order_Total", "" + subtoatalprice1);
        hashMap.put("Order_BY", "" + firebaseAuth.getUid());
        hashMap.put("Order_To", "" + ShopUid);

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("My Orders").child(ShopUid);
           databaseReference.child(timestemp).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
             showDialog();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(order.this, MainActivity.class));

                    }
                }, 4000);
            }


        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(order.this,e.getMessage(),Toast.LENGTH_SHORT);
            }
        });

    }
    public void showDialog(){
        Dialog alertDialog = new Dialog(order.this);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(R.layout.dialguoe2);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

}