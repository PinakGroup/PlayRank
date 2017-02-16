package com.wzx;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

/**
 * Created by arthurwang on 16/12/30.
 */
@Controller
public class UrlController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    String index() {
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    String login(Model model) {
        model.addAttribute("time", new Date());
        return "login";
    }

    @RequestMapping(value = "/todo", method = RequestMethod.GET)
    String todos() {
        return "todos";
    }
}
