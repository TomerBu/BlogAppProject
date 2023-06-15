package edu.tomerbu.blogproject.service;

import edu.tomerbu.blogproject.dto.SignUpRequestDto;
import edu.tomerbu.blogproject.dto.SignUpResponseDto;
import edu.tomerbu.blogproject.entity.User;
import edu.tomerbu.blogproject.error.BadRequestException;
import edu.tomerbu.blogproject.error.BlogException;
import edu.tomerbu.blogproject.repository.RoleRepository;
import edu.tomerbu.blogproject.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    //props:
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignUpResponseDto signUp(SignUpRequestDto dto) {
        //1) get the user role from role repository:
        val userRole = roleRepository.findByNameIgnoreCase("ROLE_USER")
                .orElseThrow(() -> new BlogException("Please contact admin"));
        //2) if email/username exists -> Go Sign in (Exception)
        val byUser = userRepository.findByUsernameIgnoreCase(dto.getUsername().trim());
        val byEmail = userRepository.findByEmailIgnoreCase(dto.getEmail().trim());

        if (byEmail.isPresent()) {
            throw new BadRequestException("email", "Email already exists");
        } else if (byUser.isPresent()) {
            throw new BadRequestException("username", "Username already exists");
        }

        //3) val user = new User(... encoded-password )
        var user = new User(
                null,
                dto.getUsername(),
                dto.getEmail(),
                passwordEncoder.encode(dto.getPassword().trim()),
                Set.of(userRole)
        );

        var savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, SignUpResponseDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //fetch our user entity from our database
        var user = userRepository
                .findByUsernameIgnoreCase(username).orElseThrow(() -> new UsernameNotFoundException(username));

        var roles = user.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getName())).toList();

        //return new org.springframework.security.core.userdetails.User
        //spring User implements UserDetails
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), roles);
    }
}
