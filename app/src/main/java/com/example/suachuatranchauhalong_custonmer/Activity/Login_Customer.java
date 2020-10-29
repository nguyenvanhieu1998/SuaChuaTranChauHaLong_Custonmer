package com.example.suachuatranchauhalong_custonmer.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suachuatranchauhalong_custonmer.Presenter.Login.PresenterLoginCustomer;
import com.example.suachuatranchauhalong_custonmer.R;
import com.example.suachuatranchauhalong_custonmer.View.Login.ViewLoginListener;

public class Login_Customer extends AppCompatActivity implements ViewLoginListener, View.OnClickListener {
    private EditText edtEmail,edtPass;
    private Button btnLogin;
    private TextView txtForgotPass,txtRedirectRegister;
    private PresenterLoginCustomer presenterLoginCustomer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__customer);
        initObject();
        addControls();
        addEvents();
    }
    public void initObject()
    {
        presenterLoginCustomer = new PresenterLoginCustomer(this);
    }
    public void addControls()
    {
        edtEmail = (EditText) findViewById(R.id.ActivityLoginCustomer_edtEmail);
        edtEmail.setText("trungthanh3@gmail.com");
        edtPass = (EditText) findViewById(R.id.ActivityLoginCustomer_edtPass);
        edtPass.setText("12345678");
        btnLogin = (Button) findViewById(R.id.ActivityLoginCustomer_btnLogin);
        txtRedirectRegister = (TextView) findViewById(R.id.ActivityLoginCustomer_txtRedirectRegister);
        txtForgotPass = (TextView) findViewById(R.id.ActivityginCustomer_txtQuenPass);
    }
    public void addEvents()
    {
        btnLogin.setOnClickListener(this);
        txtRedirectRegister.setOnClickListener(this);
    }
    @Override
    public void onLoginEmprty() {
        Toast.makeText(this, "Hãy nhập email và mật khẩu", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginSuccess() {
        Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
        Intent intentRedirectActivityMainCustomer = new Intent(Login_Customer.this,ActivityMain_Customer.class);
        startActivity(intentRedirectActivityMainCustomer);
    }

    @Override
    public void onLoginFaile() {
        Toast.makeText(this, "Email và mật khẩu không đúng", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.ActivityLoginCustomer_btnLogin :
                String email = edtEmail.getText().toString();
                String password = edtPass.getText().toString();
                //Báo cho lớp Presenter biết là shipper đã tương tác click vào nút Login
                //Truyền tham số phone,password từ view cho lớp presenter
                presenterLoginCustomer.receivedHandleLogin(email,password);
                break;
            case R.id.ActivityLoginCustomer_txtRedirectRegister :
                Intent intent = new Intent(Login_Customer.this,Register_Customer.class);
                startActivity(intent);
                break;
        }
    }
}
