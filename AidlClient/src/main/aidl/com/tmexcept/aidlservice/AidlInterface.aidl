package com.tmexcept.aidlservice;

// Declare any non-default types here with import statements

interface AidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    int add(int num1,int num2);
    int getNum();
    oneway void minus(int num1,int num2);//与服务端返回值、oneway修饰符不一致，仍然可以正常调用
    int minus2(int num1,int num2);
    int testParam(int num1,int num2, int num3);//参数数量不一致情况能正常调用，如果服务端有返回值，且客户端可以接收，则能正常接收
//    int testParam(int num1);//无法重载，编译不通过
    int testParam2(int num1);//客户端可以调用，但无服务端回应
}