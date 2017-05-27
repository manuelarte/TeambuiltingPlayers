/**
 * 
 */
package org.manuel.teambuilting.players.config;

import com.auth0.Auth0Client;
import com.auth0.Auth0ClientImpl;
import com.auth0.spring.security.api.JwtWebSecurityConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author Manuel Doncel Martos
 *
 */
@EnableWebSecurity(debug = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final String clientId;
	private final String issuer;
	private final String clientSecret;

	public SecurityConfig(@Value("${auth0.clientId}") final String clientId,
						  @Value("${auth0.issuer}") final String issuer,
						  @Value("${auth0.clientSecret}") final String clientSecret) {
		this.clientId = clientId;
		this.issuer = issuer;
		this.clientSecret = clientSecret;
	}

	@Bean
	public Auth0Client auth0Client() {
		return new Auth0ClientImpl(clientId, clientSecret, "manuelarte.eu.auth0.com");
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		JwtWebSecurityConfigurer
				.forHS256WithBase64Secret(clientId, issuer, clientSecret)
				.configure(http)
				.authorizeRequests()
				//.antMatchers("/users/**").authenticated()
				.anyRequest().permitAll();
	}

}
