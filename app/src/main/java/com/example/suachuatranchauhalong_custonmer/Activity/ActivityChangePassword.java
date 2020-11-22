package com.example.suachuatranchauhalong_custonmer.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.suachuatranchauhalong_custonmer.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActivityChangePassword extends AppCompatActivity implements View.OnClickListener{
    EditText edtEmail,edtOldPassword,edtNewPassword;
    Toolbar toolbar;
    Button btnUpdate,btnCancel;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initReferenceObject();
        addControls();
        addEvents();
    }
    private void initReferenceObject()
    {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getInstance().getCurrentUser();
    }
    private void addControls()
    {
        toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.ActivityChangePassword_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Đổi mật khẩu");
        edtEmail = (EditText) findViewById(R.id.ActivityChangePassword_edtEmail);
        edtEmail.setText(firebaseUser.getEmail().toString());
        edtOldPassword = (EditText) findViewById(R.id.ActivityChangePassword_edtOldPassword);
        edtNewPassword = (EditText) findViewById(R.id.ActivityChangePassword_edtNewPassword);
        btnUpdate = (Button) findViewById(R.id.ActivityChangePassword_btnUpdate);
        btnCancel = (Button) findViewById(R.id.ActivityChangePassword_btnCancel);
    }
    private void addEvents()
    {
        btnUpdate.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.ActivityChangePassword_btnUpdate :
                changePassword();
                break;
            case R.id.ActivityChangePassword_btnCancel :
                finish();
                break;
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    String email,oldPass,newPass;
    private void changePassword() {
        email = edtEmail.getText().toString().trim();
        oldPass = edtOldPassword.getText().toString().trim();
        newPass = edtNewPassword.getText().toString().trim();
        if(email.equals("")||oldPass.equals("")||newPass.equals(""))
        {
            Toast.makeText(this, "Bạn phải nhập đầy đủ dữ liệu!", Toast.LENGTH_SHORT).show();
            //    Toast.makeText(this, testAccountType, Toast.LENGTH_SHORT).show();
            // return;
        }
        else
        {
            if(oldPass.equals(newPass))
            {
                Toast.makeText(this, "Mật khẩu mới phải khác mật khẩu cũ", Toast.LENGTH_SHORT).show();
            }
            else
            {
//                    FirebaseUser user = mAuth.getCurrentUser();
//                    String userEmail = user.getEmail();
//                    changeUserFirebase(userEmail,newPass,user);
                firebaseAuth.signInWithEmailAndPassword(email, oldPass)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    String userEmail = user.getEmail();
                                    updateNewPassword(userEmail,newPass,user);
                                    // Toast.makeText(DoiMatKhauActivity.this, "Login Successfully !", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ActivityChangePassword.this, "Email và password không tồn tại.Vui lòng thử lại !",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }
    }

    private void updateNewPassword(String userEmail, final String newPass, final FirebaseUser user) {
        progressDialog = new ProgressDialog(ActivityChangePassword.this);
        progressDialog.setMessage("Thay đổi mật khẩu .......");
        progressDialog.show();
        AuthCredential credential = EmailAuthProvider.getCredential(email,oldPass);
        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    user.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ActivityChangePassword.this, "Thay đổi mật khẩu thành công !", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(ActivityChangePassword.this,Login_Customer.class));
                            }
                            else {
                                Toast.makeText(ActivityChangePassword.this, "Thay đổi mật khẩu thất bại.Vui lòng thử lại !", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                    //  Toast.makeText(DoiMatKhauActivity.this, "Change password not successfully !", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(ActivityChangePassword.this, "Email và password không tồn tại.Vui lòng thử lại !",
                            Toast.LENGTH_SHORT).show();
                }
                progressDialog.cancel();
            }
        });
    }
}