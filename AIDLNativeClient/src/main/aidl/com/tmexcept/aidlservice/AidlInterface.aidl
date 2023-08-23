package com.tmexcept.aidlservice;

// Declare any non-default types here with import statements

interface AidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    int add(int num1,int num2);
    int getNum();
    oneway void minus(int num1,int num2);//todo oneway 修饰的方法不能有返回值；RemoteService是int返回值
    int minus2(int num1,int num2);
    int testParam(int num1,int num2, int num3);//参数数量不一致情况能正常调用，如果服务端有返回值，且客户端可以接收，则能正常接收
//    int testParam(int num1);//无法重载，编译不通过
    int testParam2(int num1);//客户端可以调用；但RemoteService无回应，是oneway修饰

    //todo 客户端与RemoteService中的AIDL方法个数不一致不会影响调用，只是与客户端调用的预期结果不一致
}