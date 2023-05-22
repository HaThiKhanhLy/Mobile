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

import com.bumptech.glide.Glide;
import com.example.e_cart.Activity.Adminshop;
import com.example.e_cart.Activity.ShopDetail;
import com.example.e_cart.Model.Modelshop;
import com.example.e_cart.R;

import java.util.ArrayList;

public class AdminPanelShop extends  RecyclerView.Adapter<AdminPanelShop.ShopViewHolder>{


    Context context;

    public AdminPanelShop(Context context, ArrayList<Modelshop> shoplist) {
        this.context = context;
        this.shoplist = shoplist;
    }

    ArrayList<Modelshop> shoplist;
    @NonNull
    @Override
    public ShopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.shop_row,parent,false);

        return new ShopViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopViewHolder holder, int position) {
        Modelshop modelshop = shoplist.get(position);
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
    Intent intent  =  new Intent(context, Adminshop.class);
                intent.putExtra("SHOP_ID",modelshop.getUid());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {

        return shoplist.size();
    }

    public class ShopViewHolder extends RecyclerView.ViewHolder {
        TextView Shopename, ShopPhone ,shopAddress ,closed;
        ImageView shopimage,product;
        RatingBar ratingBar;
        public ShopViewHolder(@NonNull View itemView) {
            super(itemView);

            product =itemView.findViewById(R.id.product);
            Shopename =itemView.findViewById(R.id.Shopname);
            //   ShopPhone= itemView.findViewById(R.id.Phone);
            //    closed=itemView.findViewById(R.id.closed);
            shopAddress=itemView.findViewById(R.id.address);
            shopimage=itemView.findViewById(R.id.roundImage);
            ratingBar=itemView.findViewById(R.id.rating);
        }
    }
}
