package com.example.e_cart.Activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_cart.Adapters.UserAdapter;
import com.example.e_cart.Model.UserModel;
import com.example.e_cart.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminUser extends AppCompatActivity {
    RecyclerView userrecycler;
    String UserId;
    ImageButton back;

    private ArrayList<UserModel> userList;
    UserAdapter userAdapter;
    EditText search, search1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user);
        userrecycler=findViewById(R.id.recyclerofuser);
        back= findViewById(R.id.back);
        search = findViewById(R.id.searchtextuser);
        UserId = getIntent().getStringExtra("USER ID");
        userrecycler.setHasFixedSize(true);
        userrecycler.setLayoutManager(new LinearLayoutManager(this));
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try{
                }catch (Exception e){
                    Toast.makeText(AdminUser.this,e.getMessage(),Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        loadAllUser();
        //
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    private void loadAllUser() {
        userList = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.child("User").equalTo(UserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    UserModel userModel = dataSnapshot.getValue(UserModel.class);
                    userList.add(userModel);
                }
                userAdapter = new UserAdapter(AdminUser.this, userList);
                userrecycler.setAdapter(userAdapter);
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminUser.this, error.getMessage(),Toast.LENGTH_SHORT)
                        .show();
            }
        });

    }
}