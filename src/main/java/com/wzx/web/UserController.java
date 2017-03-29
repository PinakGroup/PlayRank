package com.wzx.web;

import com.wzx.UserValidator;
import com.wzx.domain.User;
import com.wzx.service.UserService;
import com.wzx.service.security.SecurityService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;

/**
 * Created by arthurwang on 17/2/22.
 */
@Controller
public class UserController {
    private UserService userService;
    private SecurityService securityService;

    @Inject
    public UserController(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @InitBinder
    protected void initBinder(final WebDataBinder binder) {
        binder.addValidators(new UserValidator(userService));
    }

    @ResponseBody
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public String helloUser() {
        return "hello, USER!";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        model.addAttribute("errors", "");
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@Valid User userForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getFieldError().getDefaultMessage());
            return "registration";
        }
        userService.save(userForm, "USER");
        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());
        return "redirect:/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout",	required = false) String logout,
                        Model model) {
        model.addAttribute("error", error);
        model.addAttribute("logout", logout);

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        String granted = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        model.addAttribute("role", granted);
        if(!granted.equals("[ROLE_ANONYMOUS]")) {
            model.addAttribute("logged", false);
            model.addAttribute("name", name);
        } else {
            model.addAttribute("logged", true);
        }
        return "login";
    }
}
