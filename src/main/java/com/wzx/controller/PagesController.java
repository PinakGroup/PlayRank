package com.wzx.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by arthurwang on 16/12/30.
 */
@Controller
public class PagesController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Boolean> index() {
        String granted = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        Map<String, Boolean> map = new HashMap<>();

        switch (granted) {
            case "[ADMIN]":
                map.put("admin", true);
                break;
            case "[ROLE_ANONYMOUS]":
                map.put("anonymous", true);
                break;
            case "[USER]":
                map.put("user", true);
                break;
        }
        return map;
    }

    @RequestMapping(value = "/todo", method = RequestMethod.GET)
    public String todos() {
        return "todos";
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied() {
        return "403";
    }
}
