package com.urosmitrasinovic61017.planinarski_klub_webapp.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    //konfigursanje autentifikacije da bude JPA autentifikacija sa mysql bazom
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }

    //konfigurisanje autorizacije
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/user", "/korisnik/**").hasAuthority("KORISNIK")
                .antMatchers("/admin", "/admin/**").hasAuthority("ADMIN")
                .antMatchers("/registracija", "/newKorisnik/save").anonymous()
                .antMatchers("/manager", "/manager/**").hasAuthority("MENADZER")
                .antMatchers("/", "/pregledAranzmana/**", "/vrstaPutovanja/**", "/prodavnica/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/loginPage")
                .loginProcessingUrl("/autentifikujUsera")
                .successHandler(myAuthenticationSuccessHandler())
                .and()
                .logout()
                .logoutUrl("/perform_logout") /*ovo je url za link koji na klik pokrece logout*/
                .logoutSuccessUrl("/loginPage?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");
    }



    @Bean
    public PasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public DaoAuthenticationProvider authProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new MySimpleUrlAuthenticationSuccessHandler();
    }

}
