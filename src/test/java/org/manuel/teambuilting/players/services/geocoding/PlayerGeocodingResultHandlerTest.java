package org.manuel.teambuilting.players.services.geocoding;

import com.google.maps.model.GeocodingResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.manuel.teambuilting.players.model.entities.PlayerGeocoding;
import org.manuel.teambuilting.players.repositories.PlayerGeocodingRepository;
import org.manuel.teambuilting.players.services.geocoding.handlers.PlayerGeocodingResultHandler;
import org.manuel.teambuilting.players.util.Util;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigInteger;

import static org.mockito.Mockito.verify;

/**
 * @author manuel.doncel.martos
 * @since 15-3-2017
 */
@RunWith(MockitoJUnitRunner.class)
public class PlayerGeocodingResultHandlerTest {

	@Mock
	private PlayerGeocodingRepository playerGeocodingRepository;

	@Test
	public void savePlayerGeocoding() {
		final Util util = new Util(null);
		final PlayerGeocodingResultHandler handler = new PlayerGeocodingResultHandler(new BigInteger("123"), playerGeocodingRepository, util);
		final GeocodingResult[] results = GeocodingExamples.ubeda();
		handler.onResult(results);
		final PlayerGeocoding expected = util.getPlayerGeocodingFrom(new BigInteger("123"), results);
		verify(playerGeocodingRepository).save(expected);
	}
}
