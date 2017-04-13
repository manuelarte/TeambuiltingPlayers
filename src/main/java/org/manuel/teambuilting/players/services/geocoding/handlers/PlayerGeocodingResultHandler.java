package org.manuel.teambuilting.players.services.geocoding.handlers;

import com.google.maps.PendingResult.Callback;
import com.google.maps.model.GeocodingResult;
import lombok.extern.slf4j.Slf4j;
import org.manuel.teambuilting.players.repositories.PlayerGeocodingRepository;
import org.manuel.teambuilting.players.util.Util;
import org.springframework.util.Assert;

import java.math.BigInteger;

/**
 * @author manuel.doncel.martos
 * @since 14-3-2017
 */
@Slf4j
public class PlayerGeocodingResultHandler implements Callback<GeocodingResult[]> {

	private final BigInteger playerId;
	private final PlayerGeocodingRepository repository;
	private final Util util;

	public PlayerGeocodingResultHandler(final BigInteger playerId, final PlayerGeocodingRepository repository, final Util util) {
		Assert.notNull(playerId);
		Assert.notNull(repository);
		this.playerId = playerId;
		this.repository = repository;
		this.util = util;
	}

	@Override
	public void onResult(final GeocodingResult[] results) {
		Assert.notNull(results);
		repository.save(util.getPlayerGeocodingFrom(playerId, results));
	}

	@Override
	public void onFailure(final Throwable e) {
		log.error("Not able to store the geocoding data for", e);
	}
}
