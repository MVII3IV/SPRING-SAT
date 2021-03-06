package com.mvii3iv.sat.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//https://www.codementor.io/gtommee97/rest-authentication-with-spring-security-and-mongodb-j8wgh8kg7
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    MongoUserDetailsService userDetailsService;

    /*
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }*/


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/resources/static/**").permitAll()
                .antMatchers("/admin").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()

                .formLogin().successHandler(customAuthenticationSuccessHandler)
                .loginPage("/login")
                .permitAll()
                .and()

                .logout()
                .permitAll()
                .and()

                .sessionManagement()
                .sessionFixation().migrateSession()
                .invalidSessionUrl("/invalidSession.html")
                .maximumSessions(3)
                .and()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()

                .exceptionHandling()
                .accessDeniedPage("/403");
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService);

        /*
        auth
                //.userDetailsService(userDetailsService)
                //.and()
                .inMemoryAuthentication()
                .withUser("LULR860821MTA").password("goluna21").roles("USER")
                .and()
                .withUser("CASA8412202SA").password("17201720").roles("USER")
                .and()
                .withUser("admin").password("admin").roles("ADMIN");
        auth.eraseCredentials(false);*/
    }
}