//
// Created by pirate
//
#include <android/binder_ibinder.h>
#include "jni.h"
#include "LogUtil.h"
#include <android/binder_ibinder_jni.h>
#include "aidl/com/tmexcept/aidlservice/AidlInterface.h"

using namespace std;
using aidl::com::tmexcept::aidlservice::AidlInterface;
std::shared_ptr<AidlInterface> g_spMyService;

extern "C" JNIEXPORT void JNICALL
Java_com_tmexcept_JniAidlLoad_onServiceConnected(
        JNIEnv* env,
        jobject /* this */,
        jobject binder)
{
    if( g_spMyService == nullptr){
        AIBinder* pBinder = AIBinder_fromJavaBinder(env, binder);
        const ::ndk::SpAIBinder spBinder(pBinder);
        g_spMyService = AidlInterface::fromBinder(spBinder);
        LOGCATD("[AIDL-Client] [cpp] onServiceConnected");
    } else{
        LOGCATD("please disconnect first");
    }

}

extern "C" JNIEXPORT void JNICALL
Java_com_tmexcept_JniAidlLoad_onServiceDisconnected(
        JNIEnv* env,
        jobject /* this */)
{
    if (g_spMyService != nullptr){
        g_spMyService = nullptr;
    }
    LOGCATD("[AIDL-Client] [cpp] onServiceDisconnected");
}

extern "C" JNIEXPORT jint JNICALL
Java_com_tmexcept_JniAidlLoad_operation(
        JNIEnv* env,
        jobject /* this */,
        jint command)
{
    int result = 0;
    if (g_spMyService != nullptr){
        LOGCATD("[AIDL-Client] [cpp] operate command = %d", command);
        if(command == 1) {
            g_spMyService->add(3, 5, &result);
            LOGCATD("[AIDL-Client] [cpp] add result = %d", result);
        }
    }
    return result;
}