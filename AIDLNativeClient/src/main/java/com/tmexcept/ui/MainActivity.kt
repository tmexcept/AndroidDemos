package com.tmexcept.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.tmexcept.aidlnativeclient.R


class MainActivity : AppCompatActivity() {
    lateinit var bindClientService : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindClientService = findViewById(R.id.bindClientService)
    }

    companion object {
        const val TAG = "AIDL-Native-MainActivity"
    }
}
