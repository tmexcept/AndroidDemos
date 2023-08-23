package com.tmexcept.aidlservice

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.RemoteException
import android.util.Log

class RemoteService : Service() {
    var i = 0

    override fun onBind(intent: Intent): IBinder {
        Log.e(TAG, "aidl RemoteService onBind $iBinder")
        println()
        return iBinder
    }

    private val iBinder: IBinder = object : AidlInterface.Stub() {
        @Throws(RemoteException::class)
        override fun add(num1: Int, num2: Int): Int {
            Log.e(TAG, "aidl RemoteService add thread=" + Thread.currentThread())
            try {
                Thread.sleep(2000) //                wait(500);
            } catch (e: InterruptedException) {
                throw RuntimeException(e)
            }
            Log.e(TAG, "aidl RemoteService add $num1   $num2")
            return num1 + num2
        }

        @Throws(RemoteException::class)
        override fun getNum(): Int {
            return 0
        }

        @Throws(RemoteException::class)
        override fun minus(num1: Int, num2: Int): Int {
            Log.e(TAG, "aidl RemoteService minus thread=" + Thread.currentThread())
            try {
                Thread.sleep(2000) //                wait(500);
            } catch (e: InterruptedException) {
                throw RuntimeException(e)
            }
            Log.e(TAG, "aidl RemoteService end minus $num1   $num2")
            return num1 - num2
        }

        @Throws(RemoteException::class)
        override fun testParam(num1: Int, num2: Int, num3: Int, num4: Int): Int {
            Log.e(TAG, "aidl RemoteService testParam $num1   $num2   $num3   $num4")
            return 999
        }

        @Throws(RemoteException::class)
        override fun minus2(num1: Int, num2: Int) {
            Log.e(TAG, "aidl RemoteService minus2 thread=" + Thread.currentThread())
            try {
                Thread.sleep(2000) //                wait(500);
            } catch (e: InterruptedException) {
                throw RuntimeException(e)
            }
            Log.e(TAG, "aidl RemoteService minus2 end $num1   $num2") //            return num1 - num2;
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand startId=$startId")

        Thread{
            while (true) {
                try {
                    i ++
                    Thread.sleep(1000)
                } catch (e : InterruptedException) {
                    e.printStackTrace()
                }
                Log.d(TAG, "onStartCommand thread loop i=$i")
            }
        }.start()
        return super.onStartCommand(intent, flags, startId)
    }

    companion object {
        private const val TAG = "AIDL-MyService"
    }
}