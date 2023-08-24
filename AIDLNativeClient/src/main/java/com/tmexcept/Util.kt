package com.tmexcept

import android.view.View
import androidx.appcompat.app.AppCompatActivity


//真泛型
inline fun <reified T> AppCompatActivity.findView(id : Int) : T {
    return findViewById(id)
}

inline fun <reified V> AppCompatActivity.findView2(id: Int) : Lazy<V> = lazy {
    findViewById(id)
}

fun <V : View> AppCompatActivity.findView3(id: Int) : Lazy<V> = lazy {
    findViewById(id)
}

class Presenter

class View<T>(val cla : Class<T>){
    val presenter by lazy { cla.newInstance() }
    companion object {
        inline operator fun <reified T> invoke() = View(T::class.java)
    }
}

class View2<T>(val cla : T){
    val presenter by lazy {
        /*cla.newInstance()*/ //错误 无newInstance()方法
    }

    companion object {
        inline operator fun <reified T> invoke() = View(T::class.java)
    }
}

class Comp {
    private fun change(name: String, func: (String) -> Unit) {
        func(name)
    }

    fun executeCapturing() {
        var i = 2
        change("捕获式Lambda") {
            println(it)
            println(i)
        }
    }

    fun executeNoCapturing() {
        change("非捕获式Lambda") {
            val i = 2
            println(it)
            println(i)
        }
    }
}
