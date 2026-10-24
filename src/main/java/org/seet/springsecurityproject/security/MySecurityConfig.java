package org.seet.springsecurityproject.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class MySecurityConfig {
    @Bean
    UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager userDetailsService = new InMemoryUserDetailsManager();
        UserDetails user = User.withUsername("Seetha").password(bCryptPasswordEncoder()
                .encode("12345")).authorities("read").build();
        userDetailsService.createUser(user);
        return userDetailsService;
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http){
        //http.httpBasic(Customizer.withDefaults());
        http.formLogin(Customizer.withDefaults());
        //for all the request it will allow access
       //http.authorizeHttpRequests(authorize->authorize.anyRequest().authenticated());
        http.authorizeHttpRequests(
                authorize->authorize.requestMatchers("/hello").authenticated()
                        .anyRequest().denyAll());
        return http.build();

    }
}
