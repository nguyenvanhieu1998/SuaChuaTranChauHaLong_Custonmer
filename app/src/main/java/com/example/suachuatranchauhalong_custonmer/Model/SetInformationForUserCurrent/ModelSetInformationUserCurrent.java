package com.example.suachuatranchauhalong_custonmer.Model.SetInformationForUserCurrent;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.suachuatranchauhalong_custonmer.Object.Customer;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class ModelSetInformationUserCurrent {
    ModelResponeToPresenterListenerSetInformation callback;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    StorageReference mStorageRef;
    ProgressDialog progressDialog;
    FirebaseUser user;
    public ModelSetInformationUserCurrent(ModelResponeToPresenterListenerSetInformation callback)
    {
        this.callback = callback;
    }
    public void handleSetInformation(String uid,String name, Bitmap bitmapFace,
                                     String getSexUserCurrent, String phone, String address,
                                     String email, ImageView imgFace)
    {
        if(name.equals("") || phone.equals("") || getSexUserCurrent==null  || address.equals("") )
        {
            callback.onSetInformationUserRegisterEmprty();
        }
        else
        {

            setDataForUser(uid,name,bitmapFace,getSexUserCurrent,phone,address,email,imgFace);

            //setDataForUser(email,name,phone,getSexUserCurrent,bienSoXe,giayPhepLaiXe,mauXe,address,bitmapFace,imgFace);

        }
    }
    String photoURL = "";
    boolean getSex = false;
    private void setDataForUser(final String uid,final String name,final Bitmap bitmapFace,
                                final String getSexUserCurrent,final String phone,final String address,
                                final String email,ImageView imgFace){

        databaseReference = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
//        String email2 = user.getEmail();
        String userCurrentID = user.getUid().toString();
        // progressDialog.setMessage("Đang cập nhật.........");
        // progressDialog.show();
        final StorageReference mountainsRef = mStorageRef.child("Customer").child(userCurrentID+".png");

// Create a reference to 'images/mountains.jpg'
        StorageReference mountainImagesRef = mStorageRef.child("images/" +userCurrentID+ ".png\"");

// While the file names are the same, the references point to different files
        mountainsRef.getName().equals(mountainImagesRef.getName());    // true
        mountainsRef.getPath().equals(mountainImagesRef.getPath());    // false

        // Get the data from an ImageView as bytes
        imgFace.setDrawingCacheEnabled(true);
        imgFace.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imgFace.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = mountainsRef.putBytes(data);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                // Toast.makeText(CapNhatDangKiActivity.this, "Lỗi :  "+exception.toString(), Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                if(getSexUserCurrent.equals("Nam"))
                {
                    getSex = true;
                }
                else if(getSexUserCurrent.equals("Nữ"))
                {
                    getSex = false;
                }
                mountainsRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        photoURL = uri.toString();
                        Customer customer = new Customer(uid,photoURL,name,getSex,email,phone,address,"customer",0);
                        //  mb = new member(name,testAccountType,email,phone,address,userCurrentID,photoURL,status,"user");
                        databaseReference.child("ListCustomer").child(user.getUid().toString()).setValue(customer, new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                if(databaseError == null)
                                {
                                    callback.onSetInformationUserRegisterSuccess();
                                }
                                else
                                {
                                    callback.onSetInformationUserRegisterFaile();
                                    //Toast.makeText(CapNhatDangKiActivity.this, "Cập nhật không thành công !", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }
                });
                Log.d("photoURL",photoURL);
                // Log.d("downloadUrl",downloadUrl.toString());

            }
        });
    }
}
