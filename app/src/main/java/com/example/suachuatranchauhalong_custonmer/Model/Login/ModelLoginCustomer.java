package com.example.suachuatranchauhalong_custonmer.Model.Login;

import android.app.ProgressDialog;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ModelLoginCustomer {
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    //Khai báo interface ModelResponeToPresenterListener để trả kết quả xử lý cho lớp presenter
    ModelResponeToPresenterListener callback;
    public ModelLoginCustomer(ModelResponeToPresenterListener callback)
    {
        this.callback = callback;
    }
    //Lắng nghe được thông báo từ lớp presenter
    public void handleLogin(String email, String password)
    {
        //Xử lý logic chức năng Login
        //Nếu phone và pass đều rỗng
        if(email.equals("") || password.equals(""))
        {
            //kết quả rỗng được response cho lớp presenter
            callback.onLoginEmprty();
        }
        else if(!email.equals("") && ! password.equals(""))
        {
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        //kết quả đúng được response cho lớp presenter
                        callback.onLoginSuccess();
                    }
                    else if(! task.isSuccessful())
                    {
                        //kết quả sai được response cho lớp presenter
                        callback.onLoginFaile();
                    }
                }
            });


        }
        else
        {

        }
    }

}
