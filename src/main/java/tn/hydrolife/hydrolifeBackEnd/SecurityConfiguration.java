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
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tn.hydrolife.hydrolifeBackEnd.filters.JwtRequestFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    //authentication
    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService);
                //.passwordEncoder(bCryptPasswordEncoder());
    }

    //authorisation
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/centre/update").hasRole("CENTRE")
//                .antMatchers("/delete/{id}").hasRole("CENTRE")
//                .antMatchers("/centre/add").permitAll()
//                .antMatchers("/centre/all").permitAll()
//                .antMatchers("/find/{id}").permitAll()
//                .and().formLogin();

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/centre/authenticate").permitAll()
                .antMatchers("/centre/add").permitAll()
                .antMatchers("/centre/all").permitAll()
                .antMatchers("/find/{id}").permitAll()
                .antMatchers("/centre/update").hasRole("CENTRE")
                .antMatchers("/centre/delete/{id}").hasRole("CENTRE")
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); //don't manage sessions, bcz i'm using JWT
                //.and().formLogin(); //form (of spring security) authentication

                //.anyRequest().authenticated(); //for any request it needs authentication

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }



    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Bean
    //password encoder
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    //    @Bean public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }


}
