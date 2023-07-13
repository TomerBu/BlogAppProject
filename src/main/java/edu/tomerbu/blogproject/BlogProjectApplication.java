package edu.tomerbu.blogproject;

import edu.tomerbu.blogproject.config.BlogJWTConfig;
import edu.tomerbu.blogproject.entity.Role;
import edu.tomerbu.blogproject.error.BlogException;
import edu.tomerbu.blogproject.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@RequiredArgsConstructor
@EnableConfigurationProperties(BlogJWTConfig.class)
public class BlogProjectApplication implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public static void main(String[] args) {

        //catch all my exceptions
        try {
            SpringApplication.run(BlogProjectApplication.class, args);
        } catch (BlogException b) {
            //send to log server
        }
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        //check the size
        if (roleRepository.findAll().size() == 0) {
            //add roles to role repo:
            //insert
            roleRepository.save(new Role(1L, "ROLE_ADMIN"));
        }
    }
}
