package org.manuel.teambuilting.players.util;

import com.auth0.Auth0User;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import org.manuel.teambuilting.players.model.TimeSlice;
import org.manuel.teambuilting.players.model.entities.PlayerGeocoding;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author manuel.doncel.martos
 * @since 11-3-2017
 */
@Component
public class Util {

	public Optional<Auth0User> getUserProfile() {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object principal = auth.getPrincipal();
		return principal instanceof Auth0User ? Optional.of((Auth0User)principal) : Optional.empty();
	}

	public PlayerGeocoding getPlayerGeocodingFrom(final BigInteger playerId, final GeocodingResult[] results) {
		Assert.notNull(results);
		Assert.isTrue(results.length > 0);
		final Map<String, String> map = new HashMap<>(results[0].addressComponents.length);
		for (final AddressComponent addressComponent : results[0].addressComponents) {
			map.put(addressComponent.types[0].toCanonicalLiteral(), addressComponent.longName);
		}
		final LatLng location = results[0].geometry.location;
		return PlayerGeocoding.builder().playerId(playerId)
			.lat(location.lat).lng(location.lng).build();
	}

	/**
	 * Returns if there is an overlap between two dates. FromDate is inclusive, ToDate is exclusive
	 * @param entryOne
	 * @param entryTwo
	 * @param <T>
	 * @return
	 */
	public <T extends TimeSlice> boolean isOverlapping(final T entryOne, final T entryTwo) {
		Assert.notNull(entryOne);
		Assert.notNull(entryTwo);
		boolean toReturn;
		if (entryOne.getFromDate().equals(entryTwo.getFromDate())) {
			toReturn = true;
		}
		else if (entryOne.getFromDate().after(entryTwo.getFromDate())) {
			toReturn = isOverlapping(entryTwo, entryOne);
		} else {
			final boolean entryTwoFromDateBetweenEntryOneDates = entryOne.getToDate() == null || entryTwo.getFromDate().before(entryOne.getToDate());
			toReturn = entryTwoFromDateBetweenEntryOneDates;
		}
		return toReturn;
	}
}
