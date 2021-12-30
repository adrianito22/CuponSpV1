package com.tiburela.appwalletiberia;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import androidx.appcompat.app.AppCompatActivity;

public class MyBaseActivity extends AppCompatActivity {

    public static final long DISCONNECT_TIMEOUT = 60000; // 5 min = 5 * 60 * 1000 ms


    private static Handler disconnectHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            // todo
            return true;
        }
    });

    private  Runnable disconnectCallback = new Runnable() {
        @Override
        public void run() {


            goToLoginActivity(MyBaseActivity.this);

         //   AuxiliarCallback auxiliarCallback=new AuxiliarCallback();
        //    AuxiliarCallback.calledFromMain();



        }
    };

    public void resetDisconnectTimer(){
        disconnectHandler.removeCallbacks(disconnectCallback);
        disconnectHandler.postDelayed(disconnectCallback, DISCONNECT_TIMEOUT);
    }

    public void stopDisconnectTimer(){
        disconnectHandler.removeCallbacks(disconnectCallback);
    }

    @Override
    public void onUserInteraction(){
        resetDisconnectTimer();
    }

    @Override
    public void onResume() {
        super.onResume();
        resetDisconnectTimer();
    }

    @Override
    public void onStop() {
        super.onStop();
        stopDisconnectTimer();
    }


    private void gg(){


     //  axuob =new AuxiliarCallback(interfaz);

    }


    public static void goToLoginActivity(Context mContext) {
        Intent login = new Intent(mContext, ActivityLogin.class);
        mContext.startActivity(login);
    }


}
