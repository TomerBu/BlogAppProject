package edu.tomerbu.blogproject.service;


import edu.tomerbu.blogproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    //props:
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //fetch our user entity from our database
        var user = userRepository
                .findByUsernameIgnoreCase(username).orElseThrow(() -> new UsernameNotFoundException(username));

        var roles = user.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getName())).toList();

        //return new org.springframework.security.core.userdetails.User
        //spring User implements UserDetails
        return new User(user.getUsername(), user.getPassword(), roles);
    }
}
