package com.tmexcept.ui;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tmexcept.R;
import com.tmexcept.aidlservice.AidlInterface;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "AIDL-MainActivity";
    private volatile boolean mIsServiceConnected = false;
    private final ConditionVariable mServiceConnectionWaitLock = new ConditionVariable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    protected void onResume() {
        super.onResume();
        initView();

        new Thread() {
            @Override
            public void run() {
                super.run();
                Looper.prepare();//没有会崩溃
                new AlertDialog.Builder(MainActivity.this).setTitle("测试")
                        .show();
                Looper.loop();//没有不显示Dialog
            }
        }.start();
    }


    private void initView() {
        findViewById(R.id.bindClientService).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bindClientService();
            }
        });
        findViewById(R.id.bindRemoteService).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bindRemoteService();
            }
        });
        findViewById(R.id.testClientService).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (proxy == null) {
                    Toast.makeText(MainActivity.this, "未绑定ClientService", Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.d(TAG, "testClientService  start==================");
                try {
//                    int addR = proxy.add(6, 2);
//                    Log.d(TAG, "add: result = "+ addR);
                    Log.d(TAG, "minus: start==================");
                    proxy.minus(6, 2);
                    Log.d(TAG, "minus: end");

                    Log.d(TAG, "testParam: start==================");
                    int paramR = proxy.testParam(3, 4, 5);
                    Log.d(TAG, "testParam: end " + paramR);

                    Log.d(TAG, "testParam2: start==================");
                    int paramR2 = proxy.testParam2(3);
                    Log.d(TAG, "testParam2: end " + paramR2);

                    Log.d(TAG, "minus2: start==================");
                    int minus2 = proxy.minus2(3, 4);//服务端延时造成卡住
                    Log.d(TAG, "minus2: end " + minus2);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.testRemoteService).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (proxyRemote == null) {
                    Toast.makeText(MainActivity.this, "未绑定RemoteService", Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.d(TAG, "testClientService  start==================");
                try {
//                    int addR = proxy.add(6, 2);
//                    Log.d(TAG, "add: result = "+ addR);
                    Log.d(TAG, "minus: start==================");
                    proxyRemote.minus(6, 2);
                    Log.d(TAG, "minus: end");

                    Log.d(TAG, "testParam: start==================");
                    int paramR = proxyRemote.testParam(3, 4, 5);
                    Log.d(TAG, "testParam: end " + paramR);

                    Log.d(TAG, "testParam2: start==================");
                    int paramR2 = proxyRemote.testParam2(3);
                    Log.d(TAG, "testParam2: end " + paramR2);

                    Log.d(TAG, "minus2: start==================");
                    int minus2 = proxyRemote.minus2(3, 4);//服务端延时造成卡住
                    Log.d(TAG, "minus2: end " + minus2);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 绑定服务
     */
    private void bindClientService() {
        Intent intent = new Intent();
        intent.setClassName(this.getBaseContext(), "com.tmexcept.aidlservice.ClientService");
        boolean ret = bindService(intent, connectionClient, Context.BIND_AUTO_CREATE);
        Log.d(TAG, "bindService: ret = " + ret);
    }

    /**
     * 绑定服务
     */
    private void bindRemoteService() {
        Intent intent = new Intent();
        intent.setClassName("com.tmexcept.aidlservice", "com.tmexcept.aidlservice.RemoteService");
        boolean ret = bindService(intent, connectionRemote, Context.BIND_AUTO_CREATE);
        Log.d(TAG, "bindService: ret = " + ret);
        if(!ret) {
            Toast.makeText(this, "bindRemoteService失败", Toast.LENGTH_SHORT).show();
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "bindService: Thread=" + this);
                runOnUiThread(new SetTextRunnable("Waiting to talk to IMyService..."));
                // Not connected to service yet?
                while (!mIsServiceConnected) {
                    mServiceConnectionWaitLock.block(); // waits for service connection
                }

                runOnUiThread(new SetTextRunnable("Talked to IMyService. Returned : .............."));
            }
        }).start();
    }

    private class SetTextRunnable implements Runnable {
        final String mText;

        SetTextRunnable(String text) {
            mText = text;
        }

        @Override
        public void run() {
            Log.d(TAG, mText);
        }
    }

    AidlInterface proxy;
    AidlInterface proxyRemote;
    private ServiceConnection connectionClient = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            proxy = AidlInterface.Stub.asInterface(iBinder);
            Log.w(TAG, "aidl client onServiceConnected onBind " + iBinder + "   " + proxy);
            Log.d(TAG, "[App] [java] onServiceConnected");
            mIsServiceConnected = true;
            mServiceConnectionWaitLock.open(); // breaks service connection waits
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mIsServiceConnected = false;
            Log.d(TAG, "[App] [java] onServiceDisconnected");
        }
    };

    private ServiceConnection connectionRemote = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            proxyRemote = AidlInterface.Stub.asInterface(iBinder);
            Log.w(TAG, "aidl clientRemote onServiceConnected onBind " + iBinder + "   " + proxyRemote);
            Log.d(TAG, "[App] [java] onServiceConnected");
            mIsServiceConnected = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mIsServiceConnected = false;
            Log.d(TAG, "[App] [java] onServiceDisconnected");
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIsServiceConnected = false;
    }
}