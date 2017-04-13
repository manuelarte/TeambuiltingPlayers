package org.manuel.teambuilting.players.services.geocoding;

import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.AddressType;
import com.google.maps.model.Bounds;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.Geometry;
import com.google.maps.model.LatLng;
import com.google.maps.model.LocationType;

/**
 * @author manuel.doncel.martos
 * @since 14-3-2017
 */
public final class GeocodingExamples {

	public static GeocodingResult[] ubeda() {
		final GeocodingResult result = new GeocodingResult();

		final AddressComponent ubeda = create("Úbeda", types(AddressComponentType.LOCALITY, AddressComponentType.POLITICAL));
		final AddressComponent jaen = create("Jaén", types(AddressComponentType.ADMINISTRATIVE_AREA_LEVEL_2, AddressComponentType.POLITICAL));
		final AddressComponent andalusia = create("Andalusia", "AL", types(AddressComponentType.ADMINISTRATIVE_AREA_LEVEL_1, AddressComponentType.POLITICAL));
		final AddressComponent spain = create("Spain", "ES", types(AddressComponentType.COUNTRY, AddressComponentType.POLITICAL));
		final AddressComponent postalCode = create("23400", types(AddressComponentType.POSTAL_CODE));

		final Geometry geometry = new Geometry();
		geometry.bounds = new Bounds();
		geometry.bounds.northeast = new LatLng(38.0293562, -3.3581279);
		geometry.bounds.southwest = new LatLng(38.0045262, -3.3870422);
		geometry.location = new LatLng(38.0114236, -3.3712457);
		geometry.locationType = LocationType.APPROXIMATE;
		geometry.viewport = new Bounds();
		geometry.viewport.northeast = new LatLng(38.0293562, -3.3581279);
		geometry.viewport.southwest = new LatLng(38.0045262, -3.3870422);


		result.addressComponents = new AddressComponent[] {ubeda, jaen, andalusia, spain, postalCode};
		result.formattedAddress = "23400 Úbeda, Jaén, Spain";
		result.geometry = geometry;
		result.placeId = "ChIJJXZOkHj0bg0RIttkL2LFLEc";
		result.types = new AddressType[]{ AddressType.LOCALITY, AddressType.POLITICAL };

		return new GeocodingResult[]{result};
	}

	private static AddressComponent create(final String longName, final String shortName, final AddressComponentType[] types) {
		final AddressComponent addressComponent = new AddressComponent();
		addressComponent.longName = longName;
		addressComponent.shortName = shortName;
		addressComponent.types = types;
		return addressComponent;
	}

	private static AddressComponent create(final String longName, final AddressComponentType[] types) {
		return create(longName, longName, types);
	}

	private static AddressComponentType[] types(AddressComponentType... types) {
		return types;
	}
}
