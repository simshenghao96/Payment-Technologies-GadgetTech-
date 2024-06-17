package e63c.shenghao.ga;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	public MembershipDetailsService memberDetailsService() {
		return new MembershipDetailsService();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(memberDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
				.requestMatchers(
						"/gadgets/add",
						"/gadgets/edit/*", 
						"/gadgets/save", 
						"/gadgets/delete/*", 
						"/brands/add",
						"/brands/edit/*",
						"/brands/save",
						"/brands/delete/*",
						"/categories/add",
						"/categories/edit/*", 
						"/categories/save", 
						"/categories/delete/*", 
						"/members",
						"/members/add",
						"/members/edit/*", 
						"/members/save", 
						"/members/delete/*", "/members/purchase_history/*").hasRole("ADMIN")
				.requestMatchers("/gadgets", "/gadgets/*",
						"/categories", "/brands", "/aboutUs", "/contactUs", "/contactUs/save", "/register", "/register/save").permitAll()
				.requestMatchers("/").permitAll() // Home page is visible without logging in
				.requestMatchers("/bootstrap/*/*").permitAll() // for static resources, visible to all
				.requestMatchers("/images/*").permitAll() // for static resources, visible to all
				.requestMatchers("/uploads/**").permitAll()
				.anyRequest().authenticated())// Any other requests not specified earlier
		.formLogin((login) -> login.loginPage("/login").permitAll().defaultSuccessUrl("/")) // Goes to homepage
		.logout((logout) -> logout.logoutSuccessUrl("/"))// Goes to homepage upon logout
		.exceptionHandling((exceptionHandling) -> exceptionHandling.accessDeniedPage("/403"));
		return http.build();
	}
}