package com.wzx.validation;

import com.wzx.model.User;
import com.wzx.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by arthurwang on 17/2/22.
 */
@Component
public class UserValidator implements Validator {
    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(@NotNull Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(@NotNull Object obj, @NotNull Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "field.required", "Username should not be empty.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "field.required", "Password should not be empty.");

        User user = (User) obj;

        if (user.getUsername().length() < 2 || user.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username", "Username must be between 2 and 32 characters long.");
        }

        if (userService.findByUsername(user.getUsername()).isPresent()) {
            errors.rejectValue("username", "Duplicate.userForm.username", "This username has been taken.");
        }

        if (user.getPassword().length() < 2 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password", "Use 8 characters or more for your password.");
        }

        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm", "Two passwords didn't match.");
        }
    }
}
