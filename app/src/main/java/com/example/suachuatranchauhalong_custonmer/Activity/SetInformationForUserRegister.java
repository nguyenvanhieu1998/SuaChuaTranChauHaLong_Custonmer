package com.example.suachuatranchauhalong_custonmer.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.suachuatranchauhalong_custonmer.Presenter.SetInformationForUserCurrent.PresenterSetInformationForUserCurrent;
import com.example.suachuatranchauhalong_custonmer.R;
import com.example.suachuatranchauhalong_custonmer.View.SetInformationForUserCurrent.ViewSetInformationListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class SetInformationForUserRegister extends AppCompatActivity implements ViewSetInformationListener {
    EditText edtEmail,edtPhone,edtName,edtAddress;
    ImageView imgFace,imgFolderGetImage,imgCameraGetImage;
    RadioGroup rdgSex;
    RadioButton rdbMen,rdbGirl;
    Button btnUpdate;
    int REQUEST_CODE = 1;
    int READ_REQUEST_CODE = 2;
    String sexUserCurrent;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    private PresenterSetInformationForUserCurrent presenterSetInformationForUserCurrent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_information_for_user_register);
        initData();
        addControls();
        addEvents();
    }
    public void initData()
    {
        firebaseAuth = FirebaseAuth.getInstance();
        presenterSetInformationForUserCurrent = new PresenterSetInformationForUserCurrent(this);

    }
    public void addControls()
    {
        //edtEmail = (EditText) findViewById(R.id.ActivitySetInformationForUserRegister_edtEmail);
        edtPhone = (EditText) findViewById(R.id.ActivitySetInformationForUserRegister_edtPhone);
        edtName = (EditText) findViewById(R.id.ActivitySetInformationForUserRegister_edtName);
        edtAddress = (EditText) findViewById(R.id.ActivitySetInformationForUserRegister_edtAddress);
        imgFace = (ImageView) findViewById(R.id.ActivitySetInformationForUserRegister_imgFaceUserCurrent);
        imgFolderGetImage = (ImageView) findViewById(R.id.ActivitySetInformationForUserRegister_imgFolder);
        imgCameraGetImage = (ImageView) findViewById(R.id.ActivitySetInformationForUserRegister_imgCamera);
        rdgSex = (RadioGroup) findViewById(R.id.ActivitySetInformationForUserRegister_radioGroupSex);
        rdbMen = (RadioButton) findViewById(R.id.ActivitySetInformationForUserRegister_rdbMen);
        rdbMen.setChecked(true);
        rdbGirl = (RadioButton) findViewById(R.id.ActivitySetInformationForUserRegister_rdbGirl);
        btnUpdate = (Button) findViewById(R.id.ActivitySetInformationForUserRegister_btnUpdate);
        imgFace.setImageResource(R.drawable.logo_suachua);
//        Picasso.get().load("https://www.google.com/search?q=image+face+user&rlz=1C1CHBF_enVN844VN844&sxsrf=ALeKk000b_zThTGxIxLuf3SC2-gh7WVj0w:1603529534063&source=lnms&tbm=isch&sa=X&ved=2ahUKEwj3zI6C7czsAhVBI6YKHSK0AI0Q_AUoAXoECAwQAw&biw=1366&bih=578#imgrc=_GhRdGGkWoKXOM")
//                .fit()
//                .into(imgFace);

    }
    private void getImageFolder() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }
    private void getImageCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null)
        {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgFace.setImageBitmap(bitmap);
        }
        if(requestCode == READ_REQUEST_CODE && resultCode == RESULT_OK && data != null)
        {
//            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//            imgFace.setImageBitmap(bitmap);
            Uri uri = null;
            uri = data.getData();
            Log.i("AAAA", "Uri: " + uri.toString());
            imgFace.setImageURI(uri);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public String getSexUserCurrent()
    {
        //final String dataAccountType ;
        rdgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId)
                {
                    case R.id.ActivitySetInformationForUserRegister_rdbMen:
                        sexUserCurrent = rdbMen.getText().toString();
                        break;
                    case R.id.ActivitySetInformationForUserRegister_rdbGirl:
                        sexUserCurrent = rdbGirl.getText().toString();
                        break;

                }

            }
        });
        return sexUserCurrent;

    }
    public void addEvents()
    {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth = FirebaseAuth.getInstance();
                // user = firebaseAuth.getCurrentUser();
                user = firebaseAuth.getCurrentUser();
                String email = user.getEmail();
                String userCurrentID = user.getUid().toString();
                //String email = edtEmail.getText().toString();
                String name = edtName.getText().toString();
                String phone = edtPhone.getText().toString();
                String address = edtAddress.getText().toString();
                Bitmap bitmapFace = ((BitmapDrawable) imgFace.getDrawable()).getBitmap();
                String getSexUserCurrent = getSexUserCurrent();
                //Báo cho lớp Presenter biết là shipper đã tương tác click vào nút Login
                //Truyền tham số phone,password từ view cho lớp presenter
                presenterSetInformationForUserCurrent.receivedHandleSetInformation(userCurrentID,name,bitmapFace,getSexUserCurrent,phone,address,email,imgFace);
            }
        });
        imgCameraGetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImageCamera();
            }
        });
        imgFolderGetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImageFolder();
            }
        });
    }

    @Override
    public void onSetInformationUserRegisterEmprty() {
        Toast.makeText(this, "Bạn phải điền đầy đủ dữ liệu", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSetInformationUserRegisterSuccess() {
        Toast.makeText(this, "Bạn đã cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,ActivityMain_Customer.class);
        startActivity(intent);
    }

    @Override
    public void onSetInformationUserRegisterFaile() {
        Toast.makeText(this, "Cập nhật thông tin thất bại", Toast.LENGTH_SHORT).show();
    }
}
