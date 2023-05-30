package com.example.e_cart.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_cart.Model.UserModel;
import com.example.e_cart.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class UserInfoAdapter extends RecyclerView.Adapter<UserInfoAdapter.UserHolder>{
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    Context context;
    ArrayList<UserModel> userList;

    public UserInfoAdapter(Context context, ArrayList<UserModel> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_user_infor,parent,false);
        return new UserHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        UserModel userModel = userList.get(position);

        String id = userModel.getIdUser();
        holder.itemName.setText("FullName :" + userModel.getFullname());
        holder.itemEmail.setText("Email:" + userModel.getEmail());
        holder.itemPhone.setText("Phone: " + userModel.getPhone());
        holder.itemPhone.setText("Address: " + userModel.getAddress());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void setAdapter(UserInfoAdapter userInfoAdapter) {
    }

    public class UserHolder extends RecyclerView.ViewHolder {
        TextView itemName, itemAddress, itemPhone, itemEmail;
        Button Checkoutbutton;
        public UserHolder(@NonNull View itemView) {
            super(itemView);
            itemName=itemView.findViewById(R.id.fullname);
            itemEmail = itemView.findViewById(R.id.email);
            itemAddress = itemView.findViewById(R.id.address);
            itemAddress = itemView.findViewById(R.id.phone);
        }
    }
}
