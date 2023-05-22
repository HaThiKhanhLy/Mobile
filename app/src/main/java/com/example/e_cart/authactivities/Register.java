package com.example.e_cart.authactivities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_cart.Activity.MainActivity;
import com.example.e_cart.Activity.MainSellerAcoount;
import com.example.e_cart.R;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Register extends AppCompatActivity {
   private EditText Name, Email, State, city, Country, Address, Password, Confirm_password,
            Phone;
    ImageView UserImage;
    TextView seller;
    Button button;
      // Image picker
        Uri uri;
        // Permission Constants
      private static final int LOCATION_REQUEST_CODE = 100;
//
    FirebaseAuth firebaseAuth;


    //for location
    FusedLocationProviderClient fusedLocationProviderClient;
    ImageButton GPsbutton, Back;


    //////
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        seller = findViewById(R.id.createSelleraccount);
        GPsbutton = findViewById(R.id.gps);
        button= findViewById(R.id.Login);
        UserImage = findViewById(R.id.roundImage);
        Back = findViewById(R.id.back);
        Name = findViewById(R.id.PersonName);
        Email = findViewById(R.id.Email);
        State = findViewById(R.id.State);
        city = findViewById(R.id.city);
        Country = findViewById(R.id.country);
        Phone = findViewById(R.id.editTextPhone);
        Address = findViewById(R.id.CompleteAdress);
        Password = findViewById(R.id.Password);
        Confirm_password = findViewById(R.id.confirmPassword);
        //
        firebaseAuth= FirebaseAuth.getInstance();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

/// Intents
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //
        seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, SellerRegistration.class));
                finish();
            }
        });

        /// Signup
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                 LoginUser();
                }catch (Exception e){
                    Toast.makeText(Register.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }

            }
        });

        //GPs buttonclick
        GPsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLastLocation();


            }
        });

        //


        //Image picker
        UserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(Register.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();

            }
        });
    }
    //
    //
    private void LoginUser() {
        if (TextUtils.isEmpty(Name.getText().toString())) {
            Toast.makeText(Register.this, "Please enter Name", Toast.LENGTH_SHORT).show();

            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(Email.getText().toString()).matches()) {

            Toast.makeText(Register.this, "Please enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(Phone.getText().toString())) {
            Toast.makeText(Register.this, "Please enter Phone", Toast.LENGTH_SHORT).show();

            return;
        }
        if (TextUtils.isEmpty(city.getText().toString())) {

            Toast.makeText(Register.this, "Please enter city", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(Country.getText().toString())) {
            Toast.makeText(Register.this, "Please enter Country", Toast.LENGTH_SHORT).show();

            return;
        }

        if (TextUtils.isEmpty(State.getText().toString())) {

            Toast.makeText(Register.this, "Please enter State", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(Address.getText().toString())) {
            Toast.makeText(Register.this, "Please enter Address", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(Password.getText().toString())) {
            Toast.makeText(Register.this, "Please enter Password", Toast.LENGTH_SHORT).show();

            return;
        }

        if (!Password.getText().toString().equals(Confirm_password.getText().toString())) {
            Toast.makeText(Register.this, "Password dont match", Toast.LENGTH_SHORT).show();

            return;
        }
        CreateAccount();
    }
    private void CreateAccount() {
        firebaseAuth.createUserWithEmailAndPassword(Email.getText().toString(), Password.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {

                    @Override
                    public void onSuccess(AuthResult authResult) {
                        showDialog();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                savefirebasedata();

                            }
                        }, 4000);
                    }


                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this,e.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });
    }
    // THis method save data to firebase and used in the createacount
    private void savefirebasedata(){
        if (uri == null){
            HashMap<String, Object> hashMap= new HashMap<>();
            String timestemp=""+System.currentTimeMillis();
            hashMap.put("uid",""+firebaseAuth.getUid().toString());
            hashMap.put("name",""+Name.getText().toString());
            hashMap.put("email",""+Email.getText().toString());
            hashMap.put("Password",""+Password.getText().toString());
            hashMap.put("phone",""+Phone.getText().toString());
            hashMap.put("state",""+State.getText().toString());
            hashMap.put("city",""+city.getText().toString());
            hashMap.put("country",""+Country.getText().toString());
            hashMap.put("address",""+Address.getText().toString());
            hashMap.put("accounttype","User");
            hashMap.put("online","true");
            hashMap.put("profileimage","");
            hashMap.put("Timestemp",""+timestemp);
            // save to database
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
            reference.child(firebaseAuth.getUid()).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            startActivity(new Intent(Register.this, MainActivity.class));
                        }
                    }, 2000);
                }

            });

        }
        else {
            // save data with iamge
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
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("uid", "" + firebaseAuth.getUid().toString());
                        hashMap.put("name", "" + Name.getText().toString());
                        hashMap.put("email", "" + Email.getText().toString());
                        hashMap.put("Password",""+Password.getText().toString());
                        hashMap.put("phone", "" + Phone.getText().toString());
                        hashMap.put("state", "" + State.getText().toString());
                        hashMap.put("city", "" + city.getText().toString());
                        hashMap.put("country", "" + Country.getText().toString());
                        hashMap.put("address", "" + Address.getText().toString());
                        hashMap.put("accounttype", "User");
                        hashMap.put("online", "true");
                        hashMap.put("profileimage", ""+downloaduri);
                        hashMap.put("Timestemp", "" + timestemp);

                        // save to database
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                        reference.child(firebaseAuth.getUid()).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        startActivity(new Intent(Register.this, MainActivity.class));
                                    }
                                }, 2000);
                            }
                        });

                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Register.this,e.getMessage(),Toast.LENGTH_LONG).show();

                }
            });


        }
    }
    // This is the method for calling location
        private void getLastLocation () {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());

                fusedLocationProviderClient.getLastLocation()
                        .addOnSuccessListener(new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if (location != null) {

                                    List<android.location.Address> addresses = null;
                                    try {
                                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                        Address.setText(addresses.get(0).getAddressLine(0));
                                        State.setText(addresses.get(0).getAdminArea());
                                        city.setText(addresses.get(0).getLocality());
                                        Country.setText(addresses.get(0).getCountryName());

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }


                                }

                            }
                        });


            } else {

                askPermission();

            }
        }
        // This method used in the get location to ask permission to on location
        private void askPermission () {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
        }

        @Override
        public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults){

            if (requestCode == LOCATION_REQUEST_CODE) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLastLocation();
                } else {
                    Toast.makeText(this, "Required Permission", Toast.LENGTH_SHORT).show();
                }
            }


            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        // ENd of location function

    // Start of image picking
    @Override
        protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK

                uri = data.getData();
                // Use Uri object instead of File to avoid storage permissions
                UserImage.setImageURI(uri);
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
            }
            super.onActivityResult(requestCode, resultCode, data);
        }

    public void showDialog(){
        Dialog alertDialog = new Dialog(Register.this);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(R.layout.dialogye);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
    }

       //
    }
