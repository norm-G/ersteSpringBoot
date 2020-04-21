package ng.uebungen.erste.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import ng.uebungen.erste.service.NutzerDetailsService;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	

	
	@Autowired
	private NutzerDetailsService nutzerDetailsService;
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(this.nutzerDetailsService);
		provider.setPasswordEncoder(this.passwordEncoder());
		return provider;
		
	}
		
	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(this.authProvider());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests()
			//		.antMatchers("/welcome").access("hasRole('"+ nutzerDetailsService.+')").
					.antMatchers("/","/welcome").permitAll()
					.antMatchers("/rollen").hasAuthority("Admin")
					.anyRequest()
						.authenticated()
						.and()
			//		.httpBasic()	popup Abfrage
			//			.and()
					.formLogin()
			//		.defaultSuccessUrl("/welcome", true)
						.and()
					.logout()
						.permitAll();
			
			http.csrf().disable();
			http.headers().frameOptions().disable();
	}
	
	
}
