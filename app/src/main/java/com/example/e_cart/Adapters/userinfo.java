package com.example.e_cart.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.e_cart.Activity.Adminshop;
import com.example.e_cart.Activity.Userinfo2;
import com.example.e_cart.Model.Modelshop;
import com.example.e_cart.R;

import java.util.ArrayList;

public class userinfo extends RecyclerView.Adapter<userinfo.Viewholder> {
           Context context;
           ArrayList <Modelshop>  list;

    public userinfo(Context context, ArrayList<Modelshop> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.userinfo,parent,false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Modelshop modelshop = list.get(position);
        holder.Shopename.setText(modelshop.getName());
        holder.shopAddress.setText(modelshop.getAddress());
        String open=modelshop.getShopopen();
        String blockedStatus = modelshop.getBlocked();
        String uid =modelshop.getUid();
        // if (open.equals("true")){
        //    holder.closed.setVisibility(View.GONE);
        //   }else {
        //   holder.closed.setVisibility(View.VISIBLE);}
        try {
            Glide.with(context)
                    .load(modelshop.getProfileimage()).
                    placeholder(R.drawable.shop2).into(holder.shopimage);
        }catch (Exception e){}

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  =  new Intent(context, Userinfo2.class);
                intent.putExtra("SHOP_ID",modelshop.getUid());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView Shopename, ShopPhone ,shopAddress ,closed;
        ImageView shopimage,product;


        public Viewholder(@NonNull View itemView) {
            super(itemView);
            product =itemView.findViewById(R.id.product);
            Shopename =itemView.findViewById(R.id.Shopname);
            shopAddress=itemView.findViewById(R.id.address);
            shopimage=itemView.findViewById(R.id.roundImage);
        }
    }
}

