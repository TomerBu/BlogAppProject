package edu.tomerbu.blogproject;

import edu.tomerbu.blogproject.error.BlogException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
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
