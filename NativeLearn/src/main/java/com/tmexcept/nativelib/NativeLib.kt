@file:Suppress("KotlinJniMissingFunction")

package com.tmexcept.nativelib

class NativeLib {

    external fun stringFromJNI(): String?

    companion object {
        init {
            System.loadLibrary("nativelib")
        }

        external fun dataFromNative(): DataBean?
        external fun transferDataToNative(dataBean: DataBean?)
    }
}