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
import com.tmexcept.aidlservice.ClientService;

public class MainActivity extends AppCompatActivity implements ServiceConnection{
    private static final String TAG = "AIDL-MainActivity";
    private volatile boolean mIsServiceConnected = false;
    private final ConditionVariable mServiceConnectionWaitLock = new ConditionVariable();
    private Button testClientService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    protected void onResume() {
        super.onResume();
        initView();
        bindService();

        new Thread(){
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
        testClientService = findViewById(R.id.testClientService);

        testClientService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(proxy == null) {
                    Toast.makeText(MainActivity.this, "未绑定Service", Toast.LENGTH_SHORT).show();
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
                    Log.d(TAG, "testParam: end "+paramR);

                    Log.d(TAG, "testParam2: start==================");
                    int paramR2 = proxy.testParam2(3);
                    Log.d(TAG, "testParam2: end "+paramR2);

                    Log.d(TAG, "minus2: start==================");
                    int minus2 = proxy.minus2(3, 4);//服务端延时造成卡住
                    Log.d(TAG, "minus2: end "+minus2);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 绑定服务
     */
    private void bindService() {
        Intent intent = new Intent();
        intent.setClassName(this.getBaseContext(), "com.tmexcept.aidlservice.ClientService");
        boolean ret = bindService(intent, this, Context.BIND_AUTO_CREATE);
        Log.d(TAG, "bindService: ret = "+ ret);
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                Log.d(TAG, "bindService: Thread="+ this);
                runOnUiThread(new SetTextRunnable("Waiting to talk to IMyService..."));
                // Not connected to service yet?
                while(!mIsServiceConnected)
                {
                    mServiceConnectionWaitLock.block(); // waits for service connection
                }

                runOnUiThread(new SetTextRunnable("Talked to IMyService. Returned : .............." ));
            }
        }).start();
    }

    private class SetTextRunnable implements Runnable
    {
        final String mText;

        SetTextRunnable(String text)
        {
            mText = text;
        }

        @Override
        public void run()
        {
            Log.d(TAG, mText);
        }
    }

    AidlInterface proxy;
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        proxy = AidlInterface.Stub.asInterface(service);
        Log.w(TAG, "aidl client onServiceConnected onBind "+service +"   "+proxy);
        Log.d(TAG, "[App] [java] onServiceConnected");
        mIsServiceConnected = true;
        mServiceConnectionWaitLock.open(); // breaks service connection waits
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        mIsServiceConnected = false;
        Log.d(TAG, "[App] [java] onServiceDisconnected");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mIsServiceConnected = false;
    }
}