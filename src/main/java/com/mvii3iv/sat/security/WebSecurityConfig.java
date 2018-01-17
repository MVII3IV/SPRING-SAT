package com.mvii3iv.sat.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        BeanDefinitionBuilder providerManagerBldr = BeanDefinitionBuilder
                .rootBeanDefinition(ProviderManager.class);
        providerManagerBldr.addPropertyValue("eraseCredentialsAfterAuthentication",
                false);

        http
            .authorizeRequests()
                //.antMatchers("/").permitAll()
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
        .exceptionHandling().accessDeniedPage("/403");
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("LULR860821MTA").password("goluna21").roles("USER")
                .and()
                .withUser("CASA8412202SA").password("17201720").roles("USER")
                .and()
                .withUser("admin").password("admin").roles("ADMIN");
        auth.eraseCredentials(false);
    }
}