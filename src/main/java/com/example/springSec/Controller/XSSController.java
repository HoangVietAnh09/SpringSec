package com.example.springSec.Controller;

import com.example.springSec.Service.FeedBackService;
import com.example.springSec.dto.Request.FeedBackRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/xss")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class XSSController {

    FeedBackService feedBackService;

    @RequestMapping("/reflect")
    @ResponseBody
    public String reflect(String ten) {
        return ten;
    }

    @RequestMapping("/store")
    @ResponseBody
    public String store(String ten, HttpServletResponse cookie) {
        Cookie ck = new Cookie("xss_Cookie", ten);
        cookie.addCookie(ck);
        return "setted cookie";
    }

    @GetMapping("/feedback")
    public String getFeedback(Model model) {
        model.addAttribute("feedback", new FeedBackRequest());
        model.addAttribute("feedbackList", feedBackService.getAllFeedBack());
        return "feedback";
    }

    @PostMapping("/feedback")
    public String addFeedBack(@ModelAttribute FeedBackRequest feedBackRequest) {
        feedBackService.addFeedBack(feedBackRequest);
        return "redirect:/xss/feedback";
    }
}
