package edu.tomerbu.blogproject;

import edu.tomerbu.blogproject.config.BlogJWTConfig;
import edu.tomerbu.blogproject.error.BlogException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(BlogJWTConfig.class)
public class BlogProjectApplication {

    public static void main(String[] args) {

        //catch all my exceptions
        try {
            SpringApplication.run(BlogProjectApplication.class, args);
        } catch (BlogException b) {
            //send to log server
        }
    }
}
