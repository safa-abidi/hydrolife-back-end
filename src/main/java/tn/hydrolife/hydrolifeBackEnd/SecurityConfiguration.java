package tn.hydrolife.hydrolifeBackEnd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tn.hydrolife.hydrolifeBackEnd.filters.JwtRequestFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    //AUTHENTICATION
    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    //AUTHORIZATION
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/authenticate").permitAll()

                .antMatchers("/api/client/add").permitAll()
                .antMatchers("/api/client/all").permitAll()
                .antMatchers("/api/client/find/{id}").permitAll()
                .antMatchers("/api/client/get/{email}").permitAll()

                .antMatchers("/api/centre/add").permitAll()
                .antMatchers("/api/centre/all").permitAll()
                .antMatchers("/api/centre/find/{id}").permitAll()
                .antMatchers("/api/centre/get/{email}").permitAll()

                .antMatchers("/api/service/**").permitAll()


                .antMatchers("/api/promotion/**").permitAll()

                .antMatchers("/api/photo/**").permitAll()
                .anyRequest().authenticated() //for any request it needs authentication
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); //don't manage sessions, bcz i'm using Json Web Tokens
        //.and().formLogin(); //form (of spring security) authentication


        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }


    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
