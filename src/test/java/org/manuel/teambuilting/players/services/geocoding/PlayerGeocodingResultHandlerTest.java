package org.manuel.teambuilting.players.services.geocoding;

import com.google.maps.model.GeocodingResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.manuel.teambuilting.players.model.entities.PlayerGeocoding;
import org.manuel.teambuilting.players.repositories.PlayerGeocodingRepository;
import org.manuel.teambuilting.players.services.geocoding.handlers.PlayerGeocodingResultHandler;
import org.manuel.teambuilting.players.util.PlayerUtils;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigInteger;

import static org.mockito.Mockito.verify;

/**
 * @author manuel.doncel.martos
 * @since 15-3-2017
 */
public class PlayerGeocodingResultHandlerTest {

	@Mock
	private PlayerGeocodingRepository playerGeocodingRepository;

	@BeforeEach
	public void beforeAll() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void savePlayerGeocoding() {
		final PlayerUtils playerUtils = new PlayerUtils();
		final PlayerGeocodingResultHandler handler = new PlayerGeocodingResultHandler(new BigInteger("123"),
			playerGeocodingRepository, playerUtils);
		final GeocodingResult[] results = GeocodingExamples.ubeda();
		handler.onResult(results);
		final PlayerGeocoding expected = playerUtils.getPlayerGeocodingFrom(new BigInteger("123"), results);
		verify(playerGeocodingRepository).save(expected);
	}
}
