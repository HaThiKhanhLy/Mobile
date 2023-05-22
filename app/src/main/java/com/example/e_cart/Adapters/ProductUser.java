package com.example.e_cart.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_cart.Model.ProductModel;
import com.example.e_cart.R;
import com.example.e_cart.utils.filteruser;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import p32929.androideasysql_library.Column;
import p32929.androideasysql_library.EasyDB;

public class ProductUser extends RecyclerView.Adapter<ProductUser.ProductUserHolder>implements Filterable {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public ProductUser(Context context, ArrayList<ProductModel> productlist) {

        this.context = context;
        this.productlist = productlist;
        this.filterlist = productlist;
    }

    Context context ;
    public  ArrayList<ProductModel> productlist,filterlist;
    filteruser filterproduct;

    @NonNull
    @Override
    public ProductUserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(context).inflate(R.layout.row_productsuser_shown,parent,false);
        return  new ProductUserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductUserHolder holder, int position) {
        ProductModel productModel = productlist.get(position);

        holder.Name.setText("NAME: "+productModel.getProductTitle());
        holder.price.setText("PKR:"+productModel.getProductprice());
        holder.Description.setText(productModel.getProductCategory());
        try{

            Glide
                    .with(context)
                    .load(productModel.getProductimage())
                    .placeholder(R.drawable.holderitem)
                    .into(holder.productimage);
        } catch (Exception e) {

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.Addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showqnantityproduct(productModel);
            }
        });

        }
        private double cost=0;
    private double  final_cost =0;
    int qnatity =1;


    private void showqnantityproduct(ProductModel productModel) {

        View view = LayoutInflater.from(context).inflate(R.layout.cartdialogue,null);
           ImageView addiamge = view.findViewById(R.id.roundImage);
           TextView name =view.findViewById(R.id.Title);
           TextView cata =view.findViewById(R.id.cata);
           TextView price = view.findViewById(R.id.price);
           TextView addprice =view.findViewById(R.id.addtext);
           TextView finalcost =view.findViewById(R.id.finalcost);
           ImageButton remove=view.findViewById(R.id.remove);
        ImageButton  add = view.findViewById(R.id.add);
        Button addtocart = view.findViewById(R.id.addtocart);
        String price1  =productModel.getProductprice();
        String productid = productModel.getProductid();

        cost = Double.parseDouble(price1.replaceAll("PKR",""));
        final_cost = Double.parseDouble(price1.replaceAll("PKR",""));
        qnatity =1;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        try {Glide.with(context)
                .load(productModel.getProductimage())
                .placeholder(R.drawable.holderitem)
                .into(addiamge);

        }catch (Exception e){}
        name.setText(productModel.getProductTitle().toString());
        cata.setText(productModel.getProductCategory().toString());
        price.setText(price1.toString());
        finalcost.setText(String.valueOf(final_cost));
        addprice.setText(productModel.getProductQuantity().toString());
        AlertDialog dialog = builder.create();
        dialog.show();
        //
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final_cost =final_cost +cost;
                qnatity++;
                finalcost.setText(""+final_cost);
                addprice.setText(""+qnatity);
            }
        });
        //
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(qnatity>1) {
                    final_cost = final_cost - cost;
                    qnatity--;
                    finalcost.setText(""+final_cost);
                    addprice.setText(""+qnatity);
                }

            }
        });
      addtocart.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
             String id = productModel.getUid();
              String Title  = productModel.getProductTitle();
              String productid= productModel.getProductid();
              String priceEach  = price.getText().toString();
              String totalprice  = finalcost.getText().toString();
              String qunatity = addprice.getText().toString();
              addTOcart(Title,productid,priceEach,totalprice,qunatity,id);
              dialog.dismiss();
          }
      });


    }

    private void addTOcart(String Title,String productid, String  priceEach ,String totalprice ,String qunatity,String id) {
        String timestemp = "" + System.currentTimeMillis();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Id", "" + firebaseAuth.getUid());
        hashMap.put("Title",""+Title);
        hashMap.put("shopid",""+id);
        hashMap.put("Timestamp", "" + timestemp);
        hashMap.put("Product_id", "" + timestemp);
        hashMap.put("PriceEach", "" + priceEach);
        hashMap.put("Total_price",""+totalprice);
        hashMap.put("Quantity", "" + qunatity);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Orders");
        databaseReference.child(firebaseAuth.getCurrentUser().getUid()).child("User Order").child(timestemp).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context,"Data Added successfully",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context,"Data Added Failed",Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    public int getItemCount() {
        return productlist.size();
    }

    @Override
    public Filter getFilter() {
        if(filterproduct==null){
            filterproduct =  new filteruser(this,filterlist);

        }
        return filterproduct;
    }

    public class ProductUserHolder extends RecyclerView.ViewHolder {
        TextView Name ,price ,Description;
        ImageView productimage;
        Button Addtocart;
        public ProductUserHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.Title);
            Description =itemView.findViewById(R.id.cata);
            Addtocart = itemView.findViewById(R.id.Addtocart);
            productimage = itemView.findViewById(R.id.roundImage);
            price =itemView.findViewById(R.id.price);
        }
    }
}
