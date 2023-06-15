package edu.tomerbu.blogproject.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
@EnableWebSecurity(debug = true)
public class BlogSecurityConfig {

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/v1/auth/**").permitAll();
                    auth.requestMatchers("/api/v1/**").authenticated();
                    auth.anyRequest().permitAll();
                })
                .httpBasic(a -> {
                })
                .build();
    }

//    @Bean
//    UserDetailsService userDetailsService() {
//        //in memory users:
//        UserDetails user1 = User.builder()
//                .username("user")
//                .password(passwordEncoder().encode("password"))
//                .roles("USER").build();
//
//        UserDetails user2 = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN").build();
//
//
//
//
//        return new InMemoryUserDetailsManager(
//                user1, user2
//        );
//    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
        //return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
