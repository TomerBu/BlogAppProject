package edu.tomerbu.blogproject.repository;

import edu.tomerbu.blogproject.entity.Post;
import edu.tomerbu.blogproject.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
