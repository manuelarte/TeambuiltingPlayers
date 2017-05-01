package org.manuel.teambuilting.players.services.geocoding.handlers;

import com.google.maps.PendingResult.Callback;
import com.google.maps.model.GeocodingResult;
import lombok.extern.slf4j.Slf4j;
import org.manuel.teambuilting.players.model.entities.PlayerGeocoding;
import org.manuel.teambuilting.players.repositories.PlayerGeocodingRepository;
import org.manuel.teambuilting.players.util.Util;
import org.springframework.util.Assert;

import java.math.BigInteger;
import java.util.Collection;

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
		final Collection<PlayerGeocoding> geocodingForPlayer = repository.findByPlayerId(playerId);
		final BigInteger id = !geocodingForPlayer.isEmpty() ? geocodingForPlayer.iterator().next().getId() : null;
        final PlayerGeocoding playerGeocodingFrom = util.getPlayerGeocodingFrom(playerId, results);
        playerGeocodingFrom.setId(id);
        repository.save(playerGeocodingFrom);
	}

	@Override
	public void onFailure(final Throwable e) {
		log.error("Not able to store the geocoding data for", e);
	}
}
