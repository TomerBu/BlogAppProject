package edu.tomerbu.blogproject.repository;

import edu.tomerbu.blogproject.entity.Comment;
import edu.tomerbu.blogproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    //Derived Query Methods:
    List<Comment> findCommentsByPostId(long postId);
    List<Comment> findCommentsByUser(User user);
}