package com.tmexcept.ui

import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.tmexcept.R

private const val TAG = "TestCreateTimeActivity"

class TestCreateTimeActivity : AppCompatActivity() {

    @JvmField
    var view: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_testcreatetime)
        view = findViewById(R.id.view)

        //会生成一个单独的class文件
        view!!.viewTreeObserver.addOnWindowFocusChangeListener(object :
            ViewTreeObserver.OnWindowFocusChangeListener {
            override fun onWindowFocusChanged(hasFocus: Boolean) {
                Log.e(TAG, "onWindowFocusChanged")
                view!!.viewTreeObserver.removeOnWindowFocusChangeListener(this)
            }
        })
        //不会生成一个单独的class文件
        view!!.viewTreeObserver.addOnWindowFocusChangeListener {
            Log.e(TAG, "onWindowFocusChanged")
        }

        view!!.viewTreeObserver.addOnWindowAttachListener(object :
            ViewTreeObserver.OnWindowAttachListener {
            override fun onWindowAttached() {
                Log.e(TAG, "onWindowAttached")
            }

            override fun onWindowDetached() {
                Log.e(TAG, "onWindowDetached")
            }
        })

        view!!.viewTreeObserver.addOnDrawListener {
            Log.e(TAG, "addOnDrawListener")
        }

        view!!.setOnClickListener {
            object : Thread() {
                override fun run() {
                    super.run()
                    Looper.prepare() //没有会崩溃
                    AlertDialog.Builder(this@TestCreateTimeActivity).setTitle("测试").show()
                    Looper.loop() //没有不显示Dialog
                }
            }.start()

            Thread { Log.e(TAG, "TestCreateTimeActivity  Thread 1") }.start()

            Thread() {
                Log.e(TAG, "TestCreateTimeActivity  Thread 2")
            }.start()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Log.e(TAG, "onAttachedToWindow")
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        Log.e(TAG, "app onWindowFocusChanged")
    }

    override fun onPostResume() {
        super.onPostResume()
        Log.e(TAG, "onPostResume")
    }
}