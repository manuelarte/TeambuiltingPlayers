package org.manuel.teambuilting.players.services.geocoding.handlers;

import com.google.maps.PendingResult.Callback;
import com.google.maps.model.GeocodingResult;
import lombok.extern.slf4j.Slf4j;
import org.manuel.teambuilting.players.model.entities.PlayerGeocoding;
import org.manuel.teambuilting.players.repositories.PlayerGeocodingRepository;
import org.manuel.teambuilting.players.util.PlayerUtils;
import org.springframework.util.Assert;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Optional;

/**
 * @author manuel.doncel.martos
 * @since 14-3-2017
 */
@Slf4j
public class PlayerGeocodingResultHandler implements Callback<GeocodingResult[]> {

	private final BigInteger playerId;
	private final PlayerGeocodingRepository repository;
	private final PlayerUtils playerUtils;

	public PlayerGeocodingResultHandler(final BigInteger playerId, final PlayerGeocodingRepository repository, final PlayerUtils playerUtils) {
		Assert.notNull(playerId);
		Assert.notNull(repository);
		this.playerId = playerId;
		this.repository = repository;
		this.playerUtils = playerUtils;
	}

	@Override
	public void onResult(final GeocodingResult[] results) {
		Assert.notNull(results, "The results cannot be null");
		final Collection<PlayerGeocoding> geocodingForPlayer = repository.findByPlayerId(playerId);
		final Optional<PlayerGeocoding> previousEntry = !geocodingForPlayer.isEmpty() ? Optional.of(geocodingForPlayer.iterator().next()) : Optional.empty();
		final PlayerGeocoding playerGeocodingFrom = playerUtils.getPlayerGeocodingFrom(playerId, results);
		if (previousEntry.isPresent()) {
			playerGeocodingFrom.setId(previousEntry.get().getId());
			playerGeocodingFrom.setLockVersion(previousEntry.get().getLockVersion());
		}
        repository.save(playerGeocodingFrom);
	}

	@Override
	public void onFailure(final Throwable e) {
		log.error("Not able to store the geocoding data for", e);
	}
}
