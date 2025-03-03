package com.example.springSec.Handle;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyTackle implements InvocationHandler {
    private Object target;

    public ProxyTackle(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("At ProxyTackle");
        return method.invoke(target, args);
    }
}
