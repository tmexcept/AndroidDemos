package com.tmexcept.ui

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.ConditionVariable
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.tmexcept.JniAidlLoad
import com.tmexcept.aidlnativeclient.R
import com.tmexcept.aidlservice.AidlInterface
import com.tmexcept.findView
import com.tmexcept.findView2


class MainActivity : AppCompatActivity() {

    lateinit var bindClientService : TextView
    private val testClientService by findView2<TextView>(R.id.testClientService)

    val jniAidlLoad by lazy {
        JniAidlLoad()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindClientService = findView(R.id.bindClientService)

        bindClientService.setOnClickListener {
            bindService()
        }
        testClientService.setOnClickListener {
            bindService()
        }
    }

    var proxy: AidlInterface? = null
    private val connectionClient: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, iBinder: IBinder) {
            proxy = AidlInterface.Stub.asInterface(iBinder)
            jniAidlLoad.onServiceConnected(iBinder)
            Log.w(TAG, "aidl client onServiceConnected onBind $iBinder   $proxy")
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            jniAidlLoad.onServiceDisconnected()
            Log.d(TAG, "[App] [java] onServiceDisconnected")
        }
    }

    /**
     * 绑定服务
     */
    private fun bindService() {
        val intent = Intent()
        intent.setClassName("com.tmexcept.aidlnativeclient", "com.tmexcept.aidlservice.ClientService")
        val ret = bindService(intent, connectionClient, BIND_AUTO_CREATE)
        Log.d(TAG, "bindService: ret = $ret  packageName=${packageName}")
    }

    companion object {
        const val TAG = "AIDL-Native-MainActivity"
    }
}
