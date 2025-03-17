package com.example.springSec.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("message", "Chào mừng bạn đến với JSP trong Spring Boot!");
        System.out.println("here");
        return "index";
    }
}
