package com.example.suachuatranchauhalong_custonmer.Model.Register;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ModelRegisterCustomer {
    ModelResponeToPresenterListener callback;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    public ModelRegisterCustomer(ModelResponeToPresenterListener callback)
    {
        this.callback = callback;
    }
    public void handleRegister(String email,String pass, String rePass)
    {
        if(email.equals("") || pass.equals("") || rePass.equals(""))
        {
            callback.onRegisterEmprty();
        }
        else if (!email.equals("") && ! pass.equals(rePass))
            {
            callback.onRegisterPasswordDifficult();
        }
        else if(!email.equals("") && pass.equals(rePass))
        {
            auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        callback.onRegisterSuccess();
                    }
                    else if(!task.isSuccessful())
                    {
                        callback.onRegisterFaile();
                    }
                }
            });


        }


    }
}
