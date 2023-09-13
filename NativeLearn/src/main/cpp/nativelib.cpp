#include "inc/Register.h"
#include <string>
#include <jni.h>



/*
 * Class:     com_tmexcept_nativelib_NativeLib
 * Method:    getDataFromNative
 * Signature: ()Lcom/tmexcept/nativelib/DataBean;
 */

extern "C"
JNIEXPORT jobject JNICALL
Java_com_tmexcept_nativelib_NativeLib_getDataFromNative(JNIEnv *env, jobject thiz) {
    return NULL;
}

/*
 * Class:     com_tmexcept_nativelib_NativeLib
 * Method:    transferDataToNative
 * Signature: (Lcom/tmexcept/nativelib/DataBean;)V
 */
extern "C"
JNIEXPORT void JNICALL
Java_com_tmexcept_nativelib_NativeLib_transferDataToNative(JNIEnv *env, jobject thiz,
                                                          jobject data_bean) {

}

/**
 * JNI 加载动态库的时候就会自动调用 JNI_OnLoad 方法
 */
jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env = NULL;
    jint result = JNI_ERR;
    if (vm->GetEnv((void **) &env, JNI_VERSION_1_6) != JNI_OK) {
        return result;
    }
    register_classes(env); // 静态注册所有的类
    return JNI_VERSION_1_6;
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_tmexcept_nativelib_NativeLib_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}