package com.example.e_cart.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.e_cart.Model.ProductModel;
import com.example.e_cart.R;
import com.example.e_cart.utils.Constants;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class Edit extends AppCompatActivity {
    EditText Tile, description , Price, Category, Quantity;
    ImageButton back;
    String productid;
    ImageView productImage;
    Button Add;
    FirebaseAuth firebaseAuth;

    // Image picker
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Tile = findViewById(R.id.Title);
        Price = findViewById(R.id.Price);
        Add =findViewById(R.id.add);
        description = findViewById(R.id.Discription);
        Quantity= findViewById(R.id.Quantity);
        Category = findViewById(R.id.catogeroy);
        back = findViewById(R.id.back);
        productImage = findViewById(R.id.roundImage);
        //
        firebaseAuth= FirebaseAuth.getInstance();
        productid = getIntent().getStringExtra("Productid");
           //
        loadproductdetail();


        //  On   image cLicks
        productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(Edit.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();

            }
        });
        /// on CategoryClick
        Category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogueCategory();
            }
        });
        // on click of add button
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
            }
        });

    }

    private void loadproductdetail() {
        //
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("USer");
        databaseReference.child("Products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String productid =""+dataSnapshot.child("productid").getValue();
                    String producttilte =""+dataSnapshot.child("productTitle").getValue();
                    String productcategory =""+dataSnapshot.child("productCategory").getValue();
                    String productdescription =""+dataSnapshot.child("productDescription").getValue();
                    String productiamge =""+dataSnapshot.child("productimage").getValue();
                    String productprice =""+dataSnapshot.child("productprice").getValue();
                    String productqnatity =""+dataSnapshot.child("productQuantity").getValue();
                    String timestemp =""+dataSnapshot.child("timestemp ").getValue();
                    String uid =""+dataSnapshot.child("uid").getValue();

          ///


                    Tile.setText(producttilte);
                     description.setText(productdescription);
                     Price.setText(productprice);
                     Category.setText(productcategory);
                     Quantity.setText(productqnatity);

                    try {
                        Glide
                                .with(Edit.this)
                                .load(productiamge)
                                .placeholder(R.drawable.placeholder)
                                .into(productImage);
                    }catch (Exception e){
                        Toast.makeText(Edit.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }



                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Edit.this, error.getMessage(),Toast.LENGTH_SHORT)
                        .show();
            }
        });



    }

    // method for dataAdding
    private void updateData() {
        if(TextUtils.isEmpty(Tile.getText().toString())){
            Toast.makeText(Edit.this, "Please Enter Title",Toast.LENGTH_SHORT).show();
            return;

        }
        if(TextUtils.isEmpty(description.getText().toString())){
            Toast.makeText(Edit.this, "Please Enter Description",Toast.LENGTH_SHORT).show();
            return;

        }
        if(TextUtils.isEmpty(Price.getText().toString())){
            Toast.makeText(Edit.this, "Please Enter Price",Toast.LENGTH_SHORT).show();
            return;

        } if(TextUtils.isEmpty(Category.getText().toString())){
            Toast.makeText(Edit.this, "Please Enter Category",Toast.LENGTH_SHORT).show();
            return;

        }
        if(TextUtils.isEmpty(Quantity.getText().toString())){
            Toast.makeText(Edit.this, "Please Enter Quantity",Toast.LENGTH_SHORT).show();
            return;

        }
        updateProduct();
    }
    // FOr Category
    private void dialogueCategory() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Edit.this);
        builder.setTitle("Product Category").setItems(Constants.options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String category = Constants.options[i];
                Category.setText(category);

            }
        }).show();

    }
    //
    private void updateProduct() {
        String timestemp = "" + System.currentTimeMillis();
        if (uri==null){
            HashMap<String , Object> hashMap = new HashMap<>();
            hashMap.put("uid", "" + firebaseAuth.getUid().toString());
            hashMap.put("Timestemp", "" + timestemp);
            hashMap.put("productid",""+timestemp);
            hashMap.put("productprice",""+Price.getText().toString());
            hashMap.put("productTitle",""+Tile.getText().toString());
            hashMap.put("productDescription",""+description.getText().toString());
            hashMap.put("productCategory",""+Category.getText().toString());
            hashMap.put("productQuantity",""+Quantity.getText().toString());
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("USer");
            reference.child("Products")
                    .child(productid).updateChildren(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(Edit.this, "Your data is updated",Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Edit.this, e.getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    });

        }else {
            String filename = "profileiamges/" + "" + firebaseAuth.getUid();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference(filename);
            storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful()) ;
                    Uri downloaduri = uriTask.getResult();
                    String timestemp = "" + System.currentTimeMillis();
                    if (uriTask.isSuccessful()) {
                        //
                        HashMap<String , Object> hashMap = new HashMap<>();
                        hashMap.put("uid", "" + firebaseAuth.getUid().toString());
                        hashMap.put("Timestemp", "" + timestemp);
                        hashMap.put("productid",""+timestemp);
                        hashMap.put("productTitle",""+Tile.getText().toString());
                        hashMap.put("productimage",""+downloaduri);
                        hashMap.put("productDescription",""+description.getText().toString());
                        hashMap.put("productCategory",""+Category.getText().toString());
                        hashMap.put("productQuantity",""+Quantity.getText().toString());
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("USer");
                        reference.child(firebaseAuth.getUid()).child("Products").child(timestemp).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(Edit.this, "YOUR data is added",Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Edit.this, e.getMessage(),Toast.LENGTH_SHORT).show();

                            }
                        });

                    }else{

                        //
                    }

                }
            });



        }
//


        //
    }




    // Start of iamge picking
    @Override
    protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK

            uri = data.getData();
            // Use Uri object instead of File to avoid storage permissions
            productImage.setImageURI(uri);
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}