package com.revature.user.service;

import com.revature.user.model.User;
import com.revature.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.info("Attempting to load user by email: {}", email);

        try {
            // Fetch user from the database
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> {
                        logger.warn("User not found with email: {}", email);
                        return new UsernameNotFoundException("User not found with email: " + email);
                    });

            // Convert Set<Role> to Set<GrantedAuthority>
            Set<GrantedAuthority> authorities = user.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))  // Prefix roles with "ROLE_"
                    .collect(Collectors.toSet());

            logger.info("User loaded successfully with email: {}", email);

            // Return the Spring Security User object with authorities
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    authorities  // Pass authorities instead of HashSet<Role>
            );

        } catch (UsernameNotFoundException e) {
            logger.error("Error loading user by email: {}", email, e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error while loading user by email: {}", email, e);
            throw new RuntimeException("Failed to load user.");
        }
    }
}
