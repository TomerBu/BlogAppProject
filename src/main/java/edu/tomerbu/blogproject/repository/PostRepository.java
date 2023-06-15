package edu.tomerbu.blogproject.repository;

import edu.tomerbu.blogproject.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    //derived query methods:
    Optional<Post> findByTitle(String title);
}
