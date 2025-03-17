package com.example.springSec.Controller;


import com.example.springSec.dto.Request.OSCommandRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@RestController
@RequestMapping("/oscommand")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OSCommandController {
    @PostMapping("/vuln1")
    public void ping_vuln_1(@RequestBody OSCommandRequest oscmd) throws IOException, InterruptedException {
        try {
            String comm = "cmd.exe /c ping " + oscmd.getTarget();
            Process process = Runtime.getRuntime().exec(comm);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String s = null;
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
            }
        } catch (IOException e) {
            System.out.println("Error executing command");
        }
    }

    @PostMapping("vuln2")
    public void ping_vuln_2(@RequestBody OSCommandRequest oscmd) throws IOException, InterruptedException {
        try{
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("cmd.exe", "/c", "ping " + oscmd.getTarget());
            Process process = processBuilder.start();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @PostMapping("/safe1")
    public void ping_safe_1(@RequestBody OSCommandRequest oscmd) throws IOException, InterruptedException {
        try{
            String commnad[] = {"cmd.exe", "/c", "ping", oscmd.getTarget()};
            Process process = Runtime.getRuntime().exec(commnad);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            int exitCode = process.waitFor();
            System.out.println("Exit Code: " + exitCode);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @PostMapping("/safe2")
    public void ping_safe_2(@RequestBody OSCommandRequest oscmd) throws IOException, InterruptedException {
        try{
            String commnad[] = {"cmd.exe", "/c", "ping", oscmd.getTarget()};
            Process process = Runtime.getRuntime().exec(commnad);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            int exitCode = process.waitFor();
            System.out.println("Exit Code: " + exitCode);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @PostMapping("/safe3")
    public void ping_safe_3(@RequestBody OSCommandRequest oscmd) throws IOException, InterruptedException {
        try{
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("cmd.exe", "/c", "ping", oscmd.getTarget());
            Process process = processBuilder.start();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }

            int exitCode = process.waitFor();
            System.out.println("Ping kết thúc với mã: " + exitCode);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
