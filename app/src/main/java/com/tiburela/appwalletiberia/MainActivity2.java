package com.tiburela.appwalletiberia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.safetynet.SafetyNetAppCheckProviderFactory;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class MainActivity2 extends AppCompatActivity {

    //variable for FirebaseAuth class
    private FirebaseAuth mAuth;
    //variable for our text input field for phone and OTP.
    private EditText edtPhone,edtOTP;
    //buttons for generating OTP and verifying OTP
    private Button verifyOTPBtn,generateOTPBtn;
    //string for storing our verification ID
    private String verificationId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //below line is for getting instance of our FirebaseAuth.
  //      inicializaesto();

        mAuth = FirebaseAuth.getInstance();
        //initializing variables for button and Edittext.
        edtPhone = findViewById(R.id.idEdtPhoneNumber);
        edtOTP = findViewById(R.id.idEdtOtp);
        verifyOTPBtn = findViewById(R.id.idBtnVerify);
        generateOTPBtn = findViewById(R.id.idBtnGetOtp);




        //setting onclick listner for generate OTP button.
        generateOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //below line is for checking weather the user has entered his mobile number or not.
                if (TextUtils.isEmpty(edtPhone.getText().toString()) ){
                    //when mobile number text field is empty displaying a toast message.
                    Toast.makeText(MainActivity2.this, "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();

                }else{
                    //if the text field is not empty we are calling our send OTP method for gettig OTP from Firebase.
                    String phone ="+593"+edtPhone.getText().toString();
                    sendVerificationCode(phone);
                }
            }
        });

        //initializing on click listner for verify otp button
        verifyOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validating if the OTP text field is empty or not.
                if (TextUtils.isEmpty(edtOTP.getText().toString())){
                    //if the OTP text field is empty display a message to user to enter OTP
                    Toast.makeText(MainActivity2.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
                }else{
                    //if OTP field is not empty calling method to verify the OTP.
                    verifyCode(edtOTP.getText().toString());
                }

            }
        });



    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        //inside this method we are checking if the code entered is correct or not.
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //if the code is correct and the task is succesful we are sending our user to new activity.

                            Intent i =new Intent(MainActivity2.this,HomeActivity.class);
                            startActivity(i);
                            finish();

                        } else {
                            //if the code is not correct then we are displaying an error message to the user.
                            Toast.makeText(MainActivity2.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }




    private void sendVerificationCode(String number) {
        //this method is used for getting OTP on user phone number.

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,//first parameter is user's mobile number
                60,//second parameter is time limit for OTP verification which is 60 seconds in our case.
                TimeUnit.SECONDS,// third parameter is for initializing units for time period which is in seconds in our case.
                (Activity) TaskExecutors.MAIN_THREAD,//this task will be excuted on Main thread.
                mCallBack//we are calling callback method when we recieve OTP for auto verification of user.
        );

    }

    //callback method is called on Phone auth provider.
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            //initializing our callbacks for on verification callback method.
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        //below method is used when OTP is sent from Firebase
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            //when we recieve the OTP it contains a unique id wich we are storing in our string which we have already created.
            verificationId = s;

            Toast.makeText(MainActivity2.this, "se envio el ,mensaje", Toast.LENGTH_SHORT).show();
        }

        //this method is called when user recieve OTP from Firebase.
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            //below line is used for getting OTP code which is sent in phone auth credentials.
            final String code = phoneAuthCredential.getSmsCode();
            //checking if the code is null or not.
            if (code != null) {
                //if the code is not null then we are setting that code to our OTP edittext field.
                edtOTP.setText(code);
                //after setting this code to OTP edittext field we are calling our verifycode method.
                verifyCode(code);
                Toast.makeText(MainActivity2.this, "excelente", Toast.LENGTH_SHORT).show();


            }

        }

        //thid method is called when firebase doesnot sends our OTP code due to any error or issue.
        @Override
        public void onVerificationFailed(FirebaseException e) {
            //displaying error message with firebase exception.
            Toast.makeText(MainActivity2.this, "seprodujo un error", Toast.LENGTH_SHORT).show();

            Toast.makeText(MainActivity2.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    //below method is use to verify code from Firebase.
    private void verifyCode(String code) {
        //below line is used for getting getting credentials from our verification id and code.
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        //after getting credential we are calling sign in method.
        signInWithCredential(credential);
    }



    private void inicializaesto(){

        FirebaseApp.initializeApp(MainActivity2.this);
        FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
        firebaseAppCheck.installAppCheckProviderFactory(
                SafetyNetAppCheckProviderFactory.getInstance());


    }










}