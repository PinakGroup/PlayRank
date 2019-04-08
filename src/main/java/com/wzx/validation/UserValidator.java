package com.wzx.validation;

import com.wzx.domain.User;
import com.wzx.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import javax.inject.Inject;

/**
 * Created by arthurwang on 17/2/22.
 */
@Component
public class UserValidator implements Validator {
    private UserService userService;

    @Inject
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (user.getUsername().length() < 2 || user.getUsername().length() > 32) {
            errors.rejectValue("username", "Size.userForm.username", "Username must be between 2 and 32 characters long.");
        }
        if (userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username", "This username has been taken.");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        if (user.getPassword().length() < 2 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password", "Use 8 characters or more for your password.");
        }

        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm", "Two passwords didn't match.");
        }
    }
}
