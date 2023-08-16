package com.tmexcept.aidlservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class RemoteService extends Service {
    private static final String TAG = "AIDL-MyService";

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "aidl Service onBind "+iBinder);
        return iBinder;
    }

    private IBinder iBinder = new AidlInterface.Stub() {

        @Override
        public int add(int num1, int num2) throws RemoteException {
            Log.e(TAG, "aidl Service add thread="+Thread.currentThread());
            try {
                Thread.sleep(2000);
//                wait(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Log.e(TAG, "aidl Service add "+num1+"   "+num2);
            return num1 + num2;
        }

        @Override
        public int getNum() throws RemoteException {
            return 0;
        }

        @Override
        public int minus(int num1, int num2) throws RemoteException {
            Log.e(TAG, "aidl Service minus thread="+Thread.currentThread());
            try {
                Thread.sleep(2000);
//                wait(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Log.e(TAG, "aidl Service end minus "+num1+"   "+num2);
            return num1 - num2;
        }

        @Override
        public int testParam(int num1, int num2, int num3, int num4) throws RemoteException {
            Log.e(TAG, "aidl Service testParam "+num1+"   "+num2+"   "+num3+"   "+num4);
            return 999;
        }

        @Override
        public void minus2(int num1, int num2) throws RemoteException {
            Log.e(TAG, "aidl Service minus2 thread="+Thread.currentThread());
            try {
                Thread.sleep(2000);
//                wait(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Log.e(TAG, "aidl Service minus2 end "+num1+"   "+num2);
//            return num1 - num2;
        }
    };


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand startId="+startId);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    i++;
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    Log.d(TAG, "i: " + i);
//                }
//            }
//        }).start();

        return super.onStartCommand(intent, flags, startId);
    }
}
