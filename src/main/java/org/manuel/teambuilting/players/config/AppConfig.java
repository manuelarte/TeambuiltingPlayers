/**
 * 
 */
package org.manuel.teambuilting.players.config;

import com.auth0.spring.security.api.Auth0SecurityConfig;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * @author Manuel Doncel Martos
 *
 */
@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class AppConfig extends Auth0SecurityConfig {

	/**
	 * Provides Auth0 API access
	 */
	@Bean
	public Auth0Client auth0Client() {
		return new Auth0Client(clientId, issuer);
	}

	@Override
	protected void authorizeRequests(final HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/users/**").authenticated()
						.anyRequest().permitAll();
	}

}
