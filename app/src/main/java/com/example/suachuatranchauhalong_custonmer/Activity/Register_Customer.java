package com.example.suachuatranchauhalong_custonmer.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suachuatranchauhalong_custonmer.Presenter.Register.PresenterRegisterCustomer;
import com.example.suachuatranchauhalong_custonmer.R;
import com.example.suachuatranchauhalong_custonmer.View.Register.ViewRegisterListener;

public class Register_Customer extends AppCompatActivity implements View.OnClickListener, ViewRegisterListener {
    EditText edtEmail,edtPassword,edtRepassword;
    TextView txtRedirectLogin;
    Button btnRegister;
    PresenterRegisterCustomer presenterRegisterCustomer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__customer);
        initObject();
        addControls();
        addEvents();
    }
    public void initObject()
    {
        presenterRegisterCustomer = new PresenterRegisterCustomer(this);
    }
    public void addControls()
    {
        edtEmail = (EditText) findViewById(R.id.ActivityRegisterCustomer_edtPhone);
        edtPassword = (EditText) findViewById(R.id.ActivityRegisterCustomer_edtPass);
        edtRepassword = (EditText) findViewById(R.id.ActivityRegisterCustomer_edtRePass);
        txtRedirectLogin = (TextView) findViewById(R.id.ActivityRegisterCustomer_txtRedirectLogin);
        btnRegister = (Button) findViewById(R.id.ActivityRegisterCustomer_btnRegister);
    }
    public void registerUser()
    {
        //String phone = edtPhone.getText().toString().trim();
        //String password = edtPassword.getText().toString().trim();
        //String rePassword = edtRepassword.getText().toString().trim();
      //  Intent intent = new Intent(Register_Customer.this,);
       // intent.putExtra("phoneNo",phone);
     //   startActivity(intent);
    }
    public void addEvents()
    {
        txtRedirectLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.ActivityRegisterCustomer_btnRegister :
                String email = edtEmail.getText().toString().trim();
                String pass  = edtPassword.getText().toString().trim();
                String rePass = edtRepassword.getText().toString().trim();
                presenterRegisterCustomer.receivedHandleRegister(email,pass,rePass);
                // Toast.makeText(this, "Register", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ActivityRegisterCustomer_txtRedirectLogin :
                Intent intent = new Intent(Register_Customer.this, Login_Customer.class);
                startActivity(intent);
                // Toast.makeText(this, "Redirect Login", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onRegisterEmprty() {
        Toast.makeText(this, "Hãy nhập đầy đủ dữ liệu", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRegisterSuccess() {
        Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Register_Customer.this,SetInformationForUserRegister.class);
        startActivity(intent);
    }

    @Override
    public void onRegisterFaile() {
        Toast.makeText(this, "Đăng ký không thành công", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRegisterPasswordDifficult() {
        Toast.makeText(this, "Nhập lại mật khẩu phải giống nhau", Toast.LENGTH_SHORT).show();
    }
}
