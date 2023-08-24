package com.tmexcept;

import android.os.IBinder;

public class JniAidlLoad {
    static {
        try {
            System.loadLibrary("aidltest");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public native void onServiceConnected(IBinder binder);
    public native void onServiceDisconnected();
    public native int operation(int command);

    int b = 0;
    static int a = 0;
    static{
        a = 4;
//        b = 3;
    }
}
