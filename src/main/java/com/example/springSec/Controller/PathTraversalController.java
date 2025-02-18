package com.example.springSec.Controller;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

@RestController
@RequestMapping("/pathtraversal")
@RequiredArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PathTraversalController {

    public boolean validate(String file){
        String whiteList = "[a-zA-Z0-9]+";
        if(!file.matches(whiteList)) return false;
        return true;
    }

    @GetMapping
    public String pathTraversal(@RequestParam String file) throws IOException {


        //Vuln Code
//        String default_url_path = "D:\\Spring\\springSec\\springSec\\src\\main\\resources\\static\\";
//        File f = new File(default_url_path + file);
//        try (FileInputStream fis = new FileInputStream(f)) {
//            byte[] buffer = new byte[1024]; // Đọc từng khối 1024 byte
//            int bytesRead;
//
//            System.out.println("Nội dung file (theo byte):");
//            while ((bytesRead = fis.read(buffer)) != -1) {
//                for (int i = 0; i < bytesRead; i++) {
//                    System.out.printf("%02X ", buffer[i]);
//                }
//                System.out.println();
//            }
//
//        } catch (IOException e) {
//            System.out.println("Lỗi đọc file: " + e.getMessage());
//        }
//        return "";


        //Vulnerable code
        String default_url_path = "D:\\Spring\\springSec\\springSec\\src\\main\\resources\\static\\";
        Scanner sc = new Scanner(new File(default_url_path + file));
        String content = "";
        while (sc.hasNext()) {
            content += " " + sc.next();
        }
        return content;


        //Safe code: sử dụng regex
//        String default_url_path = "D:\\Spring\\springSec\\springSec\\src\\main\\resources\\static\\";
//        if(validate(file)){
//            Scanner sc = new Scanner(new File(default_url_path + file + ".txt"));
//            String content = "";
//            while (sc.hasNext()) {
//                content += " " + sc.next();
//            }
//            return content;
//        }else{
//            return "Hack";
//        }

        //safe code: sử dụng canonicalPath() để lấy đường dẫn trực tiếp
//        String default_url_path = "D:\\Spring\\springSec\\springSec\\src\\main\\resources\\static\\";
//        File f = new File(default_url_path + file);
//        File f1 = new File(default_url_path);
//        String c1 = f1.getCanonicalPath();
//        String canonicalPath = f.getCanonicalPath();
//        if(!canonicalPath.startsWith(c1)){
//            return "Hack";
//        }
//        Scanner sc = new Scanner(new File(default_url_path + file + ".txt"));
//        String content = "";
//        while (sc.hasNext()) {
//            content += " " + sc.next();
//        }
//        return content;


        //safe code: sử dụng Path.resolve() để giới hạn dường dẫn
//        String default_url_path = "D:\\Spring\\springSec\\springSec\\src\\main\\resources\\static\\";
//        Path basePath = Paths.get(default_url_path);
//        Path safepath = basePath.resolve(basePath).normalize();
//        if(!safepath.startsWith(basePath)){
//            return "Hack";
//        }
//        Scanner sc = new Scanner(new File(default_url_path + file + ".txt"));
//        String content = "";
//        while (sc.hasNext()) {
//            content += " " + sc.next();
//        }
//        return content;


    }
}
