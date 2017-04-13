package org.manuel.teambuilting.players.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import com.google.maps.model.AddressComponent;
import com.google.maps.model.GeocodingResult;

import java.io.IOException;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

/**
 * @author manuel.doncel.martos
 * @since 12-3-2017
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class GeocodingTest {

	@Test
	public void testFormat() throws IOException {
		//final ClassPathResource resource = new ClassPathResource("/geocoding/ubeda-jaen-spain.json");
		//final String jsonInString = new String(Files.readAllBytes(Paths.get(resource.getURI())), Charset.forName("ISO-8859-15"));
		final GeocodingResult[] geocodingResult = GeocodingExamples.ubeda();
		assertEquals(5, geocodingResult[0].addressComponents.length);
		assertThat(geocodingResult[0].addressComponents, hasTheseAddresses("Úbeda", "Jaén", "Andalusia", "Spain", "23400"));
	}

	private BaseMatcher<AddressComponent[]> hasTheseAddresses(final String... longNames) {
		return new TypeSafeMatcher<AddressComponent[]>() {

			@Override
			public void describeTo(final Description description) {

			}

			@Override
			protected boolean matchesSafely(final AddressComponent[] item) {
				for (int i = 0; i < longNames.length; i++) {
					if (!longNames[i].equals(item[i].longName)) {
						return false;
					}
				}
				return true;
			}

		};
	}

}
