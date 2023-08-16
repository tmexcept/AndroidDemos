// AidlInterface.aidl
package com.tmexcept.aidlservice;

// Declare any non-default types here with import statements

interface AidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    int add(int num1,int num2);
    int getNum();
    int minus(int num1,int num2);//与服务端返回值、oneway修饰符不一致，仍然可以正常调用
    int testParam(int num1,int num2, int num3, int num4);//参数数量不一致情况能正常调用，如果服务端有返回值，且客户端可以接收，则能正常接收
    oneway void minus2(int num1,int num2);
}