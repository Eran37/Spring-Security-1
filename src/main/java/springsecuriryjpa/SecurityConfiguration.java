package springsecuriryjpa;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//------------------------------------------------------------------------------------
//Authentication & Authorization WITH "WebSecurityConfigurerAdapter";
//------------------------------------------------------------------------------------
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    //------------------------------------------------------------------------------------
    //CONFIGURING THE AUTHENTICATION OF USERS TO THE APP;
    //------------------------------------------------------------------------------------
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //set your configuration on the auth Object
        auth.inMemoryAuthentication()
                .withUser("Eran")
                .password("12345")
                .roles("ADMIN")
                //NOW iF i WANT TO ADD SOME MORE USERS i USE and()... aND ADDING MORE USERS;
                .and()
                .withUser("Dor")
                .password("12345")
                .roles("USER")
                // aND ANOTHER ONE;
                .and()
                .withUser("Dog")
                .password("12345")
                .roles("ADMIN");
    }

//hERE i CHOOSE A PASSWORD ENCODER FOR MY USERS!!
    //SPRING RECOGNIZING IT BECAUSE IT'S A @Bean.
    //THE ONE I CHOSE DOES NOTHING IT'S JUST FOR AN EXAMPLE;
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    //------------------------------------------------------------------------------------
    //CONFIGURING THE AUTHENTICATION OF USERS TO THE APP;
    //------------------------------------------------------------------------------------


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                //the SLASH STAR STAR basically matches all paths
                // (all paths in the current level ant any levels below this level)
                //In this method the Hierarchy is important (admin above user for example);
//                .antMatchers("/**").hasRole("ADMIN") <-> DO NOT USE THIS ON TOP
//                (/** -> gives permission to anything and create bugs when on top)!!!
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/user").hasAnyRole("USER", "ADMIN")
                .antMatchers("/").permitAll()
                .and().formLogin(); //here I added the "formLogin()" - (default)

    }
}
