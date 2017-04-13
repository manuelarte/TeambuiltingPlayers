package org.manuel.teambuilting.players.config;

import com.google.maps.GeoApiContext;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author manuel.doncel.martos
 * @since 14-3-2017
 */
@Configuration
public class GoogleMapsApiConfig {

	private final String googleKey;

	public GoogleMapsApiConfig(final @Value("${google.api.key}") String googleKey) {
		this.googleKey = googleKey;
	}

	@Bean
	public GeoApiContext geoApiContext(){
		return new GeoApiContext().setApiKey(googleKey);
	}
}
