package com.coder.springjwt.security;

import com.coder.springjwt.security.jwt.AuthEntryPointJwt;
import com.coder.springjwt.security.jwt.AuthTokenFilter;
import com.coder.springjwt.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		// securedEnabled = true,
		// jsr250Enabled = true,
		prePostEnabled = true)
public class WebSecurityConfig  { //extends WebSecurityConfigurerAdapter
	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

//  @Override
//  public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
//    authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//  }

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

//  @Bean
//  @Override
//  public AuthenticationManager authenticationManagerBean() throws Exception {
//    return super.authenticationManagerBean();
//  }

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//    http.cors().and().csrf().disable()
//      .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//      .authorizeRequests().antMatchers("/api/auth/**").permitAll()
//      .antMatchers("/api/test/**").permitAll()
//      .anyRequest().authenticated();
//
//    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//  }

	@Bean
		public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
			http.csrf(csrf -> csrf.disable())
					.exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
					.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
					.authorizeHttpRequests(auth ->
							auth.requestMatchers("/api/auth/**").permitAll()
									.requestMatchers("/api/test/**").permitAll()

									.requestMatchers("/shopping/api/customer/auth/**").permitAll()
									.requestMatchers("/customer/api/v1/**").permitAll()

									//Admin Authorize API URI START
									.requestMatchers("/shopping/api/admin/auth/**").permitAll()
									.requestMatchers("/shopping/api/admin/flying/v1/**").permitAll()
									//Admin Authorize API URI ENDING

									//Seller Authorize API URI START
									.requestMatchers("/shopping/api/seller/v1/**").permitAll()
									.requestMatchers("/shopping/api/seller/fly/**").permitAll()
									//Seller Authorize API URI ENDING

									.requestMatchers("/admin/checkDelete/iAmPost/**").permitAll()

									// Only allow users with the SUPER_USER role to access DELETE APIs
									.requestMatchers(HttpMethod.DELETE, "/admin/checkDelete/**").hasRole("SUPER_ADMIN")

							.requestMatchers(
											"/v3/api-docs/**",
											"/swagger-ui/**",
											"/api/auth/**",
											"/api/test/**`").permitAll()
									.anyRequest().authenticated()
					);

			http.authenticationProvider(authenticationProvider());

			http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

			return http.build();
		}


	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
