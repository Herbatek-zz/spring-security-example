package com.piotrke.myexamplesecurity.security;

import com.piotrke.myexamplesecurity.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty())
            throw new UsernameNotFoundException("No user found with email: " + email);
        var user = userOptional.get();
        return new User(user.getEmail(), user.getPassword(), user.isEnabled(), true,
                true, true, getAuthorities(user.getRoles()));
    }

    private static List<GrantedAuthority> getAuthorities(List<String> roles) {
        var authorities = new ArrayList<GrantedAuthority>();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
        return authorities;
    }
}
