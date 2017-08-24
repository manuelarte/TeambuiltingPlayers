package org.manuel.teambuilting.players.util;

import com.google.maps.model.AddressComponent;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import lombok.AllArgsConstructor;
import org.manuel.teambuilting.players.model.TimeSlice;
import org.manuel.teambuilting.players.model.entities.PlayerGeocoding;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * @author manuel.doncel.martos
 * @since 11-3-2017
 */
@Component
@AllArgsConstructor
public class PlayerUtils {

	public PlayerGeocoding getPlayerGeocodingFrom(final BigInteger playerId, final GeocodingResult[] results) {
		Assert.notNull(results, "The results cannot be null");
		Assert.isTrue(results.length > 0, "The results cannot be null");
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
		Assert.isTrue(entryOne != null && entryTwo != null, "None of the entries cannot be null");
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
