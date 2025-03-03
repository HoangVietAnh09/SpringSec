package com.example.springSec.Service;

import com.example.springSec.Repository.Service;

public class ServiceIml implements Service {

    @Override
    public void TestInvocationHandler(){
        System.out.println("TestInvocationHandler");
    }
}
