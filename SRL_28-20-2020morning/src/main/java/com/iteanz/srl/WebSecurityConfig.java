package com.iteanz.srl;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.authorizeRequests().antMatchers("/getApproval").permitAll();
    	 http.csrf().disable();
		/*
		 * http.authorizeRequests().antMatchers("/",
		 * "/home").permitAll().anyRequest().authenticated() .and() .formLogin()
		 * .loginPage("/login") .permitAll() .and() .logout() .permitAll();
		 * http.authorizeRequests().antMatchers("/CreateUser").permitAll();
		 */
    }
    @Bean
	public JavaMailSender getMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

		// Using gmail
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		mailSender.setUsername("mail.iteanz@gmail.com");
		mailSender.setPassword("iteanz1234");

		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.starttls.enable", "true");
		javaMailProperties.put("mail.smtp.auth", "true");
		javaMailProperties.put("mail.transport.protocol", "smtp");
		javaMailProperties.put("mail.debug", "true");// Prints out everything on
														// screen

		mailSender.setJavaMailProperties(javaMailProperties);
		return mailSender;
	}
	/*
	 * @Bean public MultipartResolver multipartResolver() { CommonsMultipartResolver
	 * multipartResolver = new CommonsMultipartResolver();
	 * multipartResolver.setMaxUploadSize(5242880); return multipartResolver; }
	 */

	/*
	 * @Bean(name = "multipartResolver") public CommonsMultipartResolver
	 * getCommonsMultipartResolver() { CommonsMultipartResolver multipartResolver =
	 * new CommonsMultipartResolver();
	 * multipartResolver.setMaxUploadSize(100971520); // 100MB
	 * multipartResolver.setMaxInMemorySize(1048576); // 1MB return
	 * multipartResolver; }
	 */
    
	/*
	 * @Bean public EmbeddedServletContainerFactory servletContainer() {
	 * TomcatEmbeddedServletContainerFactory factory = new
	 * TomcatEmbeddedServletContainerFactory(); return factory; }
	 */
	/*
	 * @Bean
	 * 
	 * @Override public UserDetailsService userDetailsService() { UserDetails user =
	 * User.withDefaultPasswordEncoder() .username("user") .password("password")
	 * .roles("USER") .build();
	 * 
	 * return new InMemoryUserDetailsManager(user); }
	 */
}
