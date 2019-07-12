package com.wzx.controller;

import com.wzx.middleware.Sender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Created by arthurwang on 16/12/30.
 */
@Controller
public class PagesController {

    private final Sender sender;

    public PagesController(Sender sender) {
        this.sender = sender;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        String granted = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        switch (granted) {
            case "[ADMIN]":
                model.addAttribute("admin", true);
                break;
            case "[ROLE_ANONYMOUS]":
                model.addAttribute("anonymous", true);
                break;
            case "[USER]":
                model.addAttribute("user", true);
                break;
        }
        return "index";
    }

    @RequestMapping(value = "/todo", method = RequestMethod.GET)
    public String todos() {
        return "todos";
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied() {
        return "403";
    }

    @RequestMapping(value = "/parser", method = RequestMethod.GET)
    public String parser() {
        return "parser";
    }

    @ResponseBody
    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public void send() {
        String msg = new Date().toString();
        sender.send(msg);
    }
}
