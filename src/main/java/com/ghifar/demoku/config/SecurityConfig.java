package com.ghifar.demoku.config;

import com.ghifar.demoku.AuthenticationFilter;
import com.ghifar.demoku.LoginFilter;
import com.ghifar.demoku.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

    //this configuration for non-auth/ security. (allow all without auth)
     //   http.cors().and().authorizeRequests().anyRequest().permitAll().and().csrf().disable();


        // this configuration for activating security
            http.cors().and()
                .authorizeRequests().antMatchers(HttpMethod.POST, "/login").permitAll().anyRequest().authenticated()
                .and()
                // Filter for the api/login requests
                .addFilterBefore(new LoginFilter("/login",authenticationManager()),
                        UsernamePasswordAuthenticationFilter.class)
                //filter for other requests to check JWT
                .addFilterBefore(new AuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable();//todo should be learn more about enabling and disabling csrf (https://stackoverflow.com/questions/19468209/spring-security-403-error)
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        UrlBasedCorsConfigurationSource source= new UrlBasedCorsConfigurationSource();

        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedOrigins(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("*"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowCredentials(true);
        config.applyPermitDefaultValues();

        source.registerCorsConfiguration("/**",config);

        return source;
    }

// berguna untuk di in-memory saja. sudah didefiniskan di service yang barunya.
//    @Bean
//    @Override
//    protected UserDetailsService userDetailsService() {
//        UserDetails userDetails= User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("passwrod")
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(userDetails);
//    }
}
