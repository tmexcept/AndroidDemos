package com.tmexcept.nativelib

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class NativeActivity: AppCompatActivity() {
    private var mDataBean: DataBean? = null

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.btnGetNative).setOnClickListener {
            mDataBean = NativeLib.dataFromNative();
            Log.d("tmexcept", "getDataFromNative: " + mDataBean);
        }

        findViewById<View>(R.id.setDataToNative).setOnClickListener {
            mDataBean?.mInner?.mMessage = "data from java";
            Log.d("tmexcept", "transferDataToNative: " + mDataBean);
            NativeLib.transferDataToNative(mDataBean);
            Log.d("tmexcept", "getDataFromNative: " + mDataBean);
        }
    }
}