package com.wzx.web;

import com.wzx.domain.Role;
import com.wzx.domain.User;
import com.wzx.repository.RoleRepository;
import com.wzx.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import java.util.LinkedHashMap;

/**
 * Created by arthurwang on 17/3/29.
 */
@Controller
@RequestMapping(value = "/admin")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Inject
    public AdminController(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String helloAdmin(Model model) {
        LinkedHashMap<Long, String> list = new LinkedHashMap<>();
        for (Role role : roleRepository.findAll()) {
            if (!role.getName().equals("ADMIN")) {
                for (User user : role.getUsers()) {
                    list.put(user.getId(), user.getUsername());
                }
            }
        }
        model.addAttribute("users", list.entrySet());
        return "admin";
    }

    @ResponseBody
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id, Model model) {
        if (userRepository.findOne(id) == null) {
            //throw new ObjectNotFoundException(id, User.class.toString());
            model.addAttribute("error", true);
        } else {
            model.addAttribute("error", false);
            userRepository.delete(id);
        }
    }
}