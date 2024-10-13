package com.revature.user.config;

import com.revature.user.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf(csrf -> csrf.disable()) // Disable CSRF
//            .authorizeHttpRequests(authz -> authz
//                .requestMatchers("/", "/registration", "/login", "/home").permitAll() // Allow access to home, registration, login
//                .requestMatchers(HttpMethod.POST, "/api/users").permitAll()  // Allow anyone to register
//                .requestMatchers("/home").permitAll()
//                .requestMatchers("/api/admin/**").hasRole("ADMIN")
//                .requestMatchers("/api/buyer/**").hasRole("BUYER")
//                .requestMatchers("/api/seller/**").hasRole("SELLER")
//                .anyRequest().authenticated() // Require authentication for other requests
//            )
//            .formLogin(form -> form
//                .loginPage("/login") // Custom login page
//                .defaultSuccessUrl("/home", true) // Redirect to /home after login
//            )
//            .logout(logout -> logout
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/login?logout") // Redirect to login page after logout
//            );
//
//        return http.build();
//    }






    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeHttpRequests((authz) -> authz
                // Temporarily disable security by allowing all requests
                .anyRequest().permitAll()
            )
           // .httpBasic();  // You can also comment out this line if you want to completely disable httpBasic authentication
        .logout()
        .logoutUrl("/logout")  // Custom logout URL
        .logoutSuccessUrl("http://localhost:8080/")  // Redirect here after logout
        .invalidateHttpSession(true)  // Invalidate the session on logout
        .deleteCookies("JSESSIONID")  // Delete session cookies
        .permitAll();
        return http.build();
    }
}



/*
 package com.revature.user.config;

import com.revature.user.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Disable CSRF
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/", "/registration", "/login").permitAll() // Allow access to home, registration, and login
                .requestMatchers(HttpMethod.POST, "/api/users").permitAll()  // Allow anyone to register

                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/buyer/**").hasRole("BUYER")
                .requestMatchers("/api/seller/**").hasRole("SELLER")
                .anyRequest().authenticated() // Require authentication for other requests
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/home", true)
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
            )
            .httpBasic(Customizer.withDefaults()); // Fix for httpBasic with default settings

        return http.build();
    }



}


 
 */

/*

 @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeHttpRequests((authz) -> authz
                // Temporarily disable security by allowing all requests
                .anyRequest().permitAll()
            )
            .httpBasic();  // You can also comment out this line if you want to completely disable httpBasic authentication

        return http.build();
    }



 */



/*

 @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeHttpRequests((authz) -> authz
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/buyer/**").hasRole("BUYER")
                .requestMatchers("/api/seller/**").hasRole("SELLER")
                .anyRequest().authenticated()
            )
            .httpBasic();

        return http.build();
    }

*/