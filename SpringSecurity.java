package net.javaguides.springboot.security;


import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SpringSecurity extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("meder")
                .password("meder")
                .roles("USER")
                .and()
                .withUser("feliks")
                .password("feliks")
                .roles("USER")
                .and()
                .withUser("admin")
                .password("admin")
                .roles("ADMIN");
    }
    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/takeMoney").permitAll()
                .antMatchers("/ingredient").permitAll()
                .antMatchers("/","/showNewEmployeeForm","/saveEmployee1","/saveEmployee","/showFormForUpdate/{id}","/deleteEmployee/{id}","/page/{pageNo}","/hm","/adminPanel").hasAnyRole("USER","ADMIN")
                .antMatchers("/**").hasRole("ADMIN")
                .and().formLogin();
    }
}
