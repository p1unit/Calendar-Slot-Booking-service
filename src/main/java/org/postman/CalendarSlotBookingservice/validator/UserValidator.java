package org.postman.CalendarSlotBookingservice.validator;

import org.postman.CalendarSlotBookingservice.model.User;
import org.postman.CalendarSlotBookingservice.repository.UserRepository;
import org.postman.CalendarSlotBookingservice.resource.StringResoures;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class UserValidator implements Validator {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
        if (user.getUsername().length() > 32 ) {
            errors.rejectValue("username", StringResoures.USERNAME_IS_LARGE);
            System.out.println("here 1");
        }

        Optional<User> userExist = userRepository.findByUsername(user.getUsername());

        if (userExist.isPresent()) {
            errors.reject("username", StringResoures.DUPLICATE_USER);
            System.out.println("here 2");
        }


        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.reject("password", StringResoures.PASSWORD_LENGTH_NOT_MATCHED);
            System.out.println("here 3");
        }

        if (!user.getConfirmPassword().equals(user.getPassword())) {
            errors.reject("confirmPassword", StringResoures.CONFIRM_PASSWORD_ERROR);
            System.out.println("here 4");
        }
    }
}