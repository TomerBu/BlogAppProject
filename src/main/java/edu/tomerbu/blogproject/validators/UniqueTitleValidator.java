package edu.tomerbu.blogproject.validators;

import edu.tomerbu.blogproject.entity.Post;
import edu.tomerbu.blogproject.repository.PostRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UniqueTitleValidator implements ConstraintValidator<UniqueTitle, String> {


    @Override
    public void initialize(UniqueTitle constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    private final PostRepository postRepository;

    @Override
    public boolean isValid(String title, ConstraintValidatorContext context) {
        Optional<Post> post = postRepository.findByTitle(title);

        //if user does not exist -> VALID
        return post.isEmpty();
    }
}