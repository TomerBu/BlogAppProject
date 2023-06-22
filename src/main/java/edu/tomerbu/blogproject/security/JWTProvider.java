package edu.tomerbu.blogproject.security;


import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;

@Component
public class JWTProvider {
    //read values from application.properties:

    @Value("${edu.tomerbu.blog.secret}")
    private String secret;

    @Value("${edu.tomerbu.blog.expires}")
    private Long expires;

    private static Key mSecretKey;

    @PostConstruct
    private void init() {
        mSecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }
    //2) generate JWT
    //3) read from JWT

}
