/**
 * 
 */
package org.manuel.teambuilting.players.config;

import com.auth0.spring.security.api.JwtWebSecurityConfigurer;
import org.springframework.beans.factory.annotation.Value;
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

	private final String apiAudience;
	private final String issuer;
	private final String clientSecret;

	public SecurityConfig(@Value("${auth0.apiAudience}") final String apiAudience,
						  @Value("${auth0.issuer}") final String issuer,
						  @Value("${auth0.clientSecret}") final String clientSecret) {
		this.apiAudience = apiAudience;
		this.issuer = issuer;
		this.clientSecret = clientSecret;
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		JwtWebSecurityConfigurer
				.forRS256(apiAudience, issuer)
				.configure(http)
				.authorizeRequests()
				//.antMatchers("/users/**").authenticated()
				.anyRequest().permitAll();
	}

}
