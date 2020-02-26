package com.andersenlab.bookstorageweb.service;

import com.andersenlab.bookstorageweb.entity.Role;
import com.andersenlab.bookstorageweb.entity.User;
import com.andersenlab.bookstorageweb.repository.UserRepository;
import com.andersenlab.bookstorageweb.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Qualifier("custom")
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    PasswordEncoder encoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public Collection<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(String username, String password, Set<Role> roles) {
        User user = new User(username, encoder.encode(password), roles);
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return new UserPrincipal(
                user,
                user.getRoles().stream().map(
                        role -> new SimpleGrantedAuthority(role.getRole())
                ).collect(Collectors.toSet())
        );
    }
}
