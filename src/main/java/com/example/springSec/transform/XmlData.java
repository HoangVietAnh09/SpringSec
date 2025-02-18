package com.example.springSec.transform;


import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "root")
public class XmlData {
    private String data;

    public String getData(){
        return data;
    }

    @XmlElement(name = "data")
    public void setData(String data){
        this.data = data;
    }
}
