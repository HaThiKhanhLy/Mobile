package com.example.e_cart.Adapters;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.e_cart.Activity.Edit;
import com.example.e_cart.Activity.MainSellerAcoount;
import com.example.e_cart.Model.ProductModel;
import com.example.e_cart.R;
import com.example.e_cart.utils.filter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.HolderProduct>implements Filterable {
   FirebaseAuth firebaseAuth;
    Context context;
   public ArrayList<ProductModel> products,filterlist;
   private filter filterproduct;
    public ProductAdapter(Context context, ArrayList<ProductModel> products) {
        this.context = context;
        this.products = products;
       this.filterlist=products;
    }

    @NonNull
    @Override
    public HolderProduct onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_products_shown,parent,false);

        return new HolderProduct(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderProduct holder, int position) {
        ProductModel productModel = products.get(position);
        holder.name.setText(productModel.getProductTitle());
        holder.cata.setText(productModel.getProductCategory());
        holder.price.setText(productModel.getProductprice());
        holder.quantity.setText(productModel.getProductQuantity());
        try{

            Glide
                    .with(context)
                    .load(productModel.getProductimage())
                    .placeholder(R.drawable.holderitem)
                    .into(holder.productimage);
        } catch (Exception e){

        }
        //
        holder.arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailbottomsheet(productModel);
            }
        });

    }
//
    private void detailbottomsheet(ProductModel productModel) {
        BottomSheetDialog bottomSheetDialog =  new BottomSheetDialog(context);
        View view =  LayoutInflater.from(context).inflate(R.layout.product_edit,null);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();

        ImageButton back= view.findViewById(R.id.back);
        ImageButton edit =view.findViewById(R.id.edit);
        ImageButton delete = view.findViewById(R.id.delete);
        TextView  name = view.findViewById(R.id.name);
        TextView descrip =view.findViewById(R.id.Descrip);
        TextView  cata = view.findViewById(R.id.Category);
        TextView quant = view.findViewById(R.id.Quantity);
        TextView price  =view.findViewById(R.id.Price);
        ImageView iamge =view.findViewById(R.id.roundImage);
        //
        name.setText("Product: "+productModel.getProductTitle());
        cata.setText("Category: "+productModel.getProductCategory());
        descrip.setText("Description: "+productModel.getProductDescription());
        price.setText("Price: "+productModel.getProductprice());
        quant.setText("Quantity: "+productModel.getProductQuantity());
        String id = productModel.getProductid();
        try {
            Glide.with(context)
                    .load(productModel.getProductimage())
                    .placeholder(R.drawable.holderitem)
                    .into(iamge);
        }catch (Exception e){

        }
        //
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete").setMessage("Are you sure you want to delete Product")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                 deleteproduct(id);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();

                            }
                        }).show();

            }
        });

        //
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });
        //
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Edit.class);
                intent.putExtra("Productid",id);
                context.startActivity(intent);
            }
        });



    }
//
    private void deleteproduct(String id) {
        firebaseAuth =FirebaseAuth.getInstance();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("USer");
        databaseReference.child("Products").child(id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
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
     return  products.size();
    }

    @Override
    public Filter getFilter() {
        if(filterproduct==null){
           filterproduct =  new filter(this,filterlist);

        }
        return filterproduct;
    }

    public class HolderProduct extends RecyclerView.ViewHolder {
        TextView name,price,quantity,cata;
        ImageView productimage;
        ImageButton arrow;
        public HolderProduct(@NonNull View itemView) {
            super(itemView);
            arrow =itemView.findViewById(R.id.arrow);
            name=itemView.findViewById(R.id.Title);
            price=itemView.findViewById(R.id.price);
            cata =itemView.findViewById(R.id.cata);
            quantity=itemView.findViewById(R.id.quantitiy);
            productimage= itemView.findViewById(R.id.roundImage);
        }

    }
}
