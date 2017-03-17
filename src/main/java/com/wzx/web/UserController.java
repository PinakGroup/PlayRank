package com.wzx.web;

import com.wzx.UserValidator;
import com.wzx.domain.User;
import com.wzx.repository.RoleRepository;
import com.wzx.repository.UserRepository;
import com.wzx.service.SecurityService;
import com.wzx.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by arthurwang on 17/2/22.
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private UserDetailsService userDetailsService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserRepository userRepository;

    @Resource
    RoleRepository roleRepository;

    @ResponseBody
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('ADMIN')")
    public String helloAdmin() {
        return "hello, admin!";
    }

    @ResponseBody
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public String helloUser() {
        return "hello, user!";
    }

    @ResponseBody
    @RequestMapping(value = "/listuser", method = RequestMethod.GET)
    public List<User> listuser() {
        return this.userRepository.findAll();
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@Valid User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "error";
        }

        userService.save(userForm);
        userService.setRole(userForm, "USER");

        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout",	required = false) String logout,
                        Model model) {
        model.addAttribute("error", error);
        model.addAttribute("logout", logout);

        return "login";
    }
}
