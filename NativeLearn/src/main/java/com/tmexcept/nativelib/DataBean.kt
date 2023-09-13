package com.tmexcept.nativelib

import android.graphics.PointF
import android.graphics.Rect


data class DataBean(var mRect: Rect?,
                             var mPoints : Array<PointF>,
                             var mInner: Inner?,
                             var mID: Int,
                             var mScore: Float,
                             var mData: ByteArray,
                             var mDoubleDimenArray: Array<IntArray>) {
     // 其它类数组

     // 静态内部类
     // 整型
    // 浮点型
    // 基本类型数组
    // 二维数组
}
data class Inner(var mMessage: String?) {
}