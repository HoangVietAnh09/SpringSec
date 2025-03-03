package com.example.springSec.Controller;

import com.example.springSec.Entity.Person;
import com.example.springSec.Handle.ProxyTackle;
import com.example.springSec.Repository.Service;
import com.example.springSec.Service.ServiceIml;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.lang.reflect.Proxy;

@RestController
@RequestMapping("/invocation")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InvocationHandlerController {


    @GetMapping
    public void get(){
        Service realService = new ServiceIml();
        Service proxtService = (Service) Proxy.newProxyInstance(
                Service.class.getClassLoader(),
                new Class[]{Service.class},
                new ProxyTackle(realService)
        );

        proxtService.TestInvocationHandler();
//        Person person = new Person("aland", "han");
//        Person proxyPerson = (Person) Proxy.newProxyInstance(
//          Service.class.getClassLoader(),
//          new Class[]{Person.class},
//          new ProxyTackle(person)
//        );
//        System.out.println(proxyPerson);
    }
}
