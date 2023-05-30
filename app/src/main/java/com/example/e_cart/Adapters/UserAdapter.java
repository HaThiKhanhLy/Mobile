package com.example.e_cart.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_cart.Model.UserModel;
import com.example.e_cart.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.HolderUser> {
    FirebaseAuth firebaseAuth;
    Context context;
    TextView subtotal ;
    ArrayList<UserModel> userList;

    public UserAdapter(Context context, TextView subtotal, ArrayList<UserModel> userList) {
        this.context = context;
        this.subtotal = subtotal;
        this.userList = userList;
    }

    public UserAdapter(Context context, ArrayList<UserModel> userList) {
        this.context = context;
        this.userList = userList;
    }

    public UserAdapter(View view) {
    }

    @NonNull
    @Override
    public HolderUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_row,parent,false);
        return new HolderUser(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderUser holder, int position) {
        UserModel userModel = userList.get(position);
        String id = userModel.getIdUser();
        holder.itemName.setText("FullName :"+userModel.getFullname());
        holder.itemEmail.setText("Email: " + userModel.getEmail());

        holder.itemRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeUsers(id);
            }

        });
    }

    private void removeUsers( String id) {

        firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.child(firebaseAuth.getUid()).child("User Information").child(id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context,"Your data is removed",Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();


            }

        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class HolderUser extends RecyclerView.ViewHolder {
        TextView itemName, itemEmail,itemRemove;
        Button Checkoutbutton;
        public HolderUser(@NonNull View itemView) {
            super(itemView);
            itemName=itemView.findViewById(R.id.fullname);
            itemRemove =itemView.findViewById(R.id.remove);
            itemEmail = itemView.findViewById(R.id.email);

        }
    }
}
