package com.example.springSec.Controller;


import com.example.springSec.Payload.DemoSerialize;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.hibernate.boot.archive.internal.ByteArrayInputStreamAccess;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.Base64;

@RestController
@RequestMapping("/deserialize")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DeserializeController {

    @GetMapping("/vuln")
    public void deserialize(HttpServletRequest request) throws IOException, ClassNotFoundException {

//        String authHeader = request.getHeader("Authorization");
//
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            throw new BadRequestException("Authorization header is incorrect");
//        }
//
//        String jwt = authHeader.substring(7);
//
//        StringBuilder jwtEncoded = new StringBuilder();
//        jwtEncoded.append(Base64.getEncoder().encodeToString(jwt.getBytes()));
//        System.out.println(jwtEncoded.toString());
//
//        byte[] bytes = Base64.getDecoder().decode(jwtEncoded.toString());
//        ByteArrayInputStream fis = new ByteArrayInputStream(bytes);
//        ObjectInputStream ois = new ObjectInputStream(fis);
//        ois.readObject();
//        ois.close();
//        fis.close();
//





        DemoSerialize demoSerialize = new DemoSerialize("test");
        FileOutputStream fos = new FileOutputStream("D:\\Spring\\springSec\\springSec\\src\\main\\resources\\static\\out.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(demoSerialize);
        oos.close();
        fos.close();
        System.out.println("[Serialize]................");
        DemoSerialize demoSerialize2 = null;
        FileInputStream fis = new FileInputStream("D:\\Spring\\springSec\\springSec\\src\\main\\resources\\static\\out.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        demoSerialize2 = (DemoSerialize) ois.readObject();
        ois.close();
        fis.close();
        System.out.println(demoSerialize2);
        System.out.println("[Deserialize]................");
    }
}
