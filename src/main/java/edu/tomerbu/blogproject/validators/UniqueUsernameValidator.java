package edu.tomerbu.blogproject.validators;

import edu.tomerbu.blogproject.entity.User;
import edu.tomerbu.blogproject.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        Optional<User> user = userRepository.findByUsernameIgnoreCase(username);

        //if user does not exist -> VALID
        return user.isEmpty();
    }
}