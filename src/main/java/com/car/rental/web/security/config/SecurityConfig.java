package com.car.rental.web.security.config;

import com.car.rental.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserRepository userRepository;

    private final UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers()
                .frameOptions().disable()
                .and()
                    .authorizeRequests()
                    .antMatchers("/web/users/login", "/web/users/register", "/resources/**", "/h2-console/**", "/static/**", "/css/**", "/js/**", "/images/**")
                    .permitAll()
                .and()
                    .authorizeRequests().antMatchers("/web/cars/offer/success/{id}", "/web/users/account",
                        "/web/users/account/rents", "/web/users/account/rent/{id}", "/web/users//account/rent/delete/{id}",
                        "/web/users/account/edit")
                    .authenticated()
                .and()
                    .authorizeRequests().antMatchers("web/cars/add", "/web/users/**")
                    .hasAnyRole("ROLE_EMPLOYEE", "ROLE_ADMIN")
                .and()
                    .authorizeRequests().antMatchers("web/cars/add")
                    .hasAnyRole("ROLE_ADMIN")
                .and()
                    .formLogin()
                    .loginPage("/web/users/login")
                    .usernameParameter("email")
                    .passwordParameter("password")
                .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessUrl("/");
    }

}
