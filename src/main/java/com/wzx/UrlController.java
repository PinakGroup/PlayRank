package com.wzx;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

/**
 * Created by arthurwang on 16/12/30.
 */
@Controller
public class UrlController {
    @RequestMapping("/")
    String index() {
        return "index";
    }
    @RequestMapping("/login")
    String login(Model model) {
        model.addAttribute("time", new Date());
        return "login";
    }
}
