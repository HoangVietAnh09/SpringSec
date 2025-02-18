package com.example.springSec.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/csrf")
public class CSRFController {

    @GetMapping
    public String csrf() {
        return "form";
    }

}
