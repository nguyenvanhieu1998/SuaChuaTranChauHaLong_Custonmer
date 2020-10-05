package com.example.suachuatranchauhalong_custonmer.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

//import com.example.suachuatranchauhalong_custonmer.ActivityMain_Customer;
//import com.example.suachuatranchauhalong_custonmer.R;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.android.gms.tasks.TaskExecutors;
//import com.google.firebase.FirebaseException;
//import com.google.firebase.auth.AuthResult;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.PhoneAuthCredential;
//import com.google.firebase.auth.PhoneAuthProvider;
//
//import java.util.concurrent.TimeUnit;
//
//public class OTP_Customer extends AppCompatActivity {
//    String verificationBySystem;
//    Button btnVerify;
//    EditText edtOTP;
//    TextView txtLabel;
//    ProgressBar progressBarVerify;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_otp__customer);
//        addControls();
//        addEvents();
//    }
//    public void addControls()
//    {
//        btnVerify = (Button) findViewById(R.id.ActivityOTPCustomer_btnTiepTuc);
//        edtOTP = (EditText) findViewById(R.id.ActivityOTPCustomer_edtOTP);
//        progressBarVerify = (ProgressBar) findViewById(R.id.ActivityOTPCustomer_progressBarVerify);
//        txtLabel = (TextView) findViewById(R.id.ActivityOTPCustomer_txtLabel);
//    }
//    public void addEvents()
//    {
//        String phoneNo = getIntent().getStringExtra("phoneNo");
//        senVerifivationCode(phoneNo);
//    }
//
//    private void senVerifivationCode(String phoneNo) {
//       // PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
//
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                 "+84"+phoneNo,        // Phone number to verify
//                60,                 // Timeout duration
//                TimeUnit.SECONDS,   // Unit of timeout
//                TaskExecutors.MAIN_THREAD,               // Activity (for callback binding)
//                mCallbacks);        // OnVerificationStateChangedCallbacks
//    }
//
//   private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//       @Override
//       public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//           verificationBySystem = s;
//           super.onCodeSent(s, forceResendingToken);
//       }
//
//       @Override
//       public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//           String code = phoneAuthCredential.getSmsCode();
//           if(code!=null)
//           {
//               progressBarVerify.setVisibility(View.VISIBLE);
//               verifyCode(code);
//           }
//       }
//
//       @Override
//       public void onVerificationFailed(@NonNull FirebaseException e) {
//           txtLabel.setText(e.getMessage());
//           //Toast.makeText(OTP_Customer.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//       }
//   };
//    private void verifyCode(String codeByUser)
//    {
//            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationBySystem,codeByUser);
//            signInTheUserByCredential(credential);
//
//    }
//
//    private void signInTheUserByCredential(PhoneAuthCredential credential) {
//        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(OTP_Customer.this,new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isSuccessful())
//                {
//                    Intent intent = new Intent(getApplicationContext(), ActivityMain_Customer.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
//                }
//                else
//                {
//                    txtLabel.setText(task.getException().toString());
//                    //Toast.makeText(OTP_Customer.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//
//}
