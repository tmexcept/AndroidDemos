package com.tmexcept.ui

import android.app.AlertDialog
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.ConditionVariable
import android.os.IBinder
import android.os.Looper
import android.os.RemoteException
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tmexcept.R
import com.tmexcept.aidlservice.AidlInterface
import com.tmexcept.aidlservice.ClientService

class MainActivity : AppCompatActivity() {
    @Volatile
    private var mIsServiceConnected = false
    private val mServiceConnectionWaitLock = ConditionVariable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        initView()
//        object : Thread() {
//            override fun run() {
//                super.run()
//                Looper.prepare() //没有会崩溃
//                AlertDialog.Builder(this@MainActivity).setTitle("测试").show()
//                Looper.loop() //没有不显示Dialog
//            }
//        }.start()
//        Thread { Log.e(TAG, "aidl Service testParam ") }.start()
//
//        Thread(){
//            Log.e(TAG, "aidl Service testParam ")
//        }.start()
    }

    private fun initView() {
        proxy.let {  }
        findViewById<View>(R.id.bindClientService).setOnClickListener { bindClientService() }
        findViewById<View>(R.id.bindRemoteService).setOnClickListener { bindRemoteService() }
        findViewById<View>(R.id.testClientService).setOnClickListener(View.OnClickListener {
            if (proxy == null) {
                Toast.makeText(this@MainActivity, "未绑定ClientService", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            Log.d(TAG, "testClientService  start==================")
            try { //                    int addR = proxy.add(6, 2);
                //                    Log.d(TAG, "add: result = "+ addR);
                Log.d(TAG, "minus: start==================")
                proxy!!.minus(6, 2)
                Log.d(TAG, "minus: end")
                Log.d(TAG, "testParam: start==================")
                val paramR = proxy!!.testParam(3, 4, 5)
                Log.d(TAG, "testParam: end $paramR")
                Log.d(TAG, "testParam2: start==================")
                val paramR2 = proxy!!.testParam2(3)
                Log.d(TAG, "testParam2: end $paramR2")
                Log.d(TAG, "minus2: start==================")
                val minus2 = proxy!!.minus2(3, 4) //服务端延时造成卡住
                Log.d(TAG, "minus2: end $minus2")
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        })
        findViewById<View>(R.id.testRemoteService).setOnClickListener(View.OnClickListener {
            if (proxyRemote == null) {
                Toast.makeText(this@MainActivity, "未绑定RemoteService", Toast.LENGTH_SHORT).show()
                return@OnClickListener
            }
            Log.d(TAG, "testClientService  start==================")
            try { //                    int addR = proxy.add(6, 2);
                //                    Log.d(TAG, "add: result = "+ addR);
                Log.d(TAG, "minus: start==================")
                proxyRemote!!.minus(6, 2)
                Log.d(TAG, "minus: end")
                Log.d(TAG, "testParam: start==================")
                val paramR = proxyRemote!!.testParam(3, 4, 5)
                Log.d(TAG, "testParam: end $paramR")
                Log.d(TAG, "testParam2: start==================")
                val paramR2 = proxyRemote!!.testParam2(3)
                Log.d(TAG, "testParam2: end $paramR2")
                Log.d(TAG, "minus2: start==================")
                val minus2 = proxyRemote!!.minus2(3, 4) //服务端延时造成卡住
                Log.d(TAG, "minus2: end $minus2")
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        })
    }

    /**
     * 绑定服务
     */
    private fun bindClientService() {
        val intent = Intent()
        intent.setClassName(this.baseContext, "com.tmexcept.aidlservice.ClientService")
        val ret = bindService(intent, connectionClient, BIND_AUTO_CREATE)
        Log.d(TAG, "bindService: ret = $ret")
    }

    /**
     * 绑定服务
     */
    private fun bindRemoteService() {
        val intent = Intent()
        intent.setClassName("com.tmexcept.aidlservice", "com.tmexcept.aidlservice.RemoteService")
        val ret = bindService(intent, connectionRemote, BIND_AUTO_CREATE)
        Log.d(TAG, "bindService: ret = $ret")
        if (!ret) {
            Toast.makeText(this, "bindRemoteService失败", Toast.LENGTH_SHORT).show()
            return
        }
        Thread(object : Runnable {
            override fun run() {
                Log.d(TAG, "bindService: Thread=$this")
                runOnUiThread(SetTextRunnable("Waiting to talk to IMyService...")) // Not connected to service yet?
                while (!mIsServiceConnected) {
                    mServiceConnectionWaitLock.block() // waits for service connection
                }
                runOnUiThread(SetTextRunnable("Talked to IMyService. Returned : .............."))
            }
        }).start()
    }

    private inner class SetTextRunnable internal constructor(val mText: String) : Runnable {
        override fun run() {
            Log.d(TAG, mText)
        }
    }

    var proxy: AidlInterface? = null
    var proxyRemote: AidlInterface? = null
    private val connectionClient: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
            proxy = AidlInterface.Stub.asInterface(iBinder)
            Log.w(TAG, "aidl client onServiceConnected onBind $iBinder   $proxy")
            Log.d(TAG, "[App] [java] onServiceConnected")
            mIsServiceConnected = true
            mServiceConnectionWaitLock.open() // breaks service connection waits
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            mIsServiceConnected = false
            Log.d(TAG, "[App] [java] onServiceDisconnected")
        }
    }
    private val connectionRemote: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
            proxyRemote = AidlInterface.Stub.asInterface(iBinder)
            Log.w(TAG, "aidl clientRemote onServiceConnected onBind $iBinder   $proxyRemote")
            Log.d(TAG, "[App] [java] onServiceConnected")
            mIsServiceConnected = true
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            mIsServiceConnected = false
            Log.d(TAG, "[App] [java] onServiceDisconnected")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mIsServiceConnected = false
    }

    companion object {
        private const val TAG = "AIDL-MainActivity"
    }
}