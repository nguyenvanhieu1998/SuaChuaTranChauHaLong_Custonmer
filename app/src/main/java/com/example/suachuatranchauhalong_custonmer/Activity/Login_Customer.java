package com.example.suachuatranchauhalong_custonmer.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
    private CheckBox cbRememberPassword;
    private SharedPreferences shap;
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
        shap = getSharedPreferences("dataLogin",MODE_PRIVATE);
    }
    public void addControls()
    {
        edtEmail = (EditText) findViewById(R.id.ActivityLoginCustomer_edtEmail);
        //edtEmail.setText("trungthanh4@gmail.com");
        edtPass = (EditText) findViewById(R.id.ActivityLoginCustomer_edtPass);
      //  edtPass.setText("12345678");
        btnLogin = (Button) findViewById(R.id.ActivityLoginCustomer_btnLogin);
        txtRedirectRegister = (TextView) findViewById(R.id.ActivityLoginCustomer_txtRedirectRegister);
        //txtForgotPass = (TextView) findViewById(R.id.ActivityginCustomer_txtQuenPass);
        cbRememberPassword = (CheckBox) findViewById(R.id.ActivityginCustomer_txtRememberPassword);
    }
    public void addEvents()
    {
        btnLogin.setOnClickListener(this);
        txtRedirectRegister.setOnClickListener(this);
        getDataSharedPreferences();
    }

    private void getDataSharedPreferences() {
        edtEmail.setText(shap.getString("key_email",""));
        edtPass.setText(shap.getString("key_pass",""));
        cbRememberPassword.setChecked(shap.getBoolean("key_checkbox",false));
    }

    @Override
    public void onLoginEmprty() {
        progressDialog.cancel();
        Toast.makeText(this, "Hãy nhập email và mật khẩu", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onLoginSuccess() {
        if(cbRememberPassword.isChecked())
        {
            //Khai báo 1 biến kiểu editor để chỉnh chỉnh sửa biến shap
            SharedPreferences.Editor edit = shap.edit();
            //gán user vào bộ lưu trữ shap đã khai báo ở trên
            edit.putString("key_email",edtEmail.getText().toString());
            //gán pass vào bộ lưu trữ shap đã khai báo ở trên
            edit.putString("key_pass",edtPass.getText().toString());
            //gán trạng thái của checkbox vào bộ lưu trữ shap đã khai báo ở trên
            edit.putBoolean("key_checkbox",true);
            //commit() để xác nhận rồi đóng bộ chỉnh sửa
            edit.commit();
        }
        else
        {
            //lại mở bộ soạn thảo để chỉnh sửa
            SharedPreferences.Editor edit = shap.edit();
            edit.remove("key_email");
            edit.remove("key_pass");
            edit.remove("key_checkbox");
            edit.commit();
        }
        progressDialog.cancel();
        Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
        Intent intentRedirectActivityMainCustomer = new Intent(Login_Customer.this,ActivityMain_Customer.class);
        startActivity(intentRedirectActivityMainCustomer);
    }

    @Override
    public void onLoginFaile() {
        progressDialog.cancel();
        Toast.makeText(this, "Email và mật khẩu không đúng", Toast.LENGTH_SHORT).show();
    }
    ProgressDialog progressDialog;
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
                progressDialog = new ProgressDialog(Login_Customer.this);
                progressDialog.setMessage("Đang đăng nhập....");
                progressDialog.show();
                break;
            case R.id.ActivityLoginCustomer_txtRedirectRegister :
                Intent intent = new Intent(Login_Customer.this,Register_Customer.class);
                startActivity(intent);
                break;
        }
    }
}
