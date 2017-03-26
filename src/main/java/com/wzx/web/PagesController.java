package com.wzx.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by arthurwang on 16/12/30.
 */
@Controller
public class PagesController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    String index() {
        return "index";
    }

    @RequestMapping(value = "/todo", method = RequestMethod.GET)
    String todos() {
        return "todos";
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    String accessDenied() {
        return "403";
    }

    @RequestMapping(value = "/parser", method = RequestMethod.GET)
    String parser() {
        return "parser";
    }
}
