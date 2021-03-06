package com.example.rest_demo;
import com.example.rest_demo.service.AuthorityUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public UserDetailsService userDetailsService() {
        return new AuthorityUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/").hasAnyAuthority("USER",  "ADMIN")
                .antMatchers("/restaurants").hasAnyAuthority("ADMIN")
                .antMatchers("/meals").hasAnyAuthority("ADMIN")
                .antMatchers("/users").hasAnyAuthority("ADMIN")
                .antMatchers("/restaurants/create").hasAnyAuthority("ADMIN")
                .antMatchers("/restaurants/show").hasAnyAuthority("ADMIN")
                .antMatchers("/restaurants/index").hasAnyAuthority("ADMIN")
                .antMatchers("/restaurants/edit").hasAnyAuthority("ADMIN")
                .antMatchers("/restaurants/voting").hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/orders/votes").hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/orders/voting").hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/orders/user_vote").hasAnyAuthority("ADMIN", "USER")
                .antMatchers("/meals/**").hasAnyAuthority("ADMIN")
                .antMatchers("/users/**").hasAnyAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .and()
                .logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/403")
        ;
    }
}
