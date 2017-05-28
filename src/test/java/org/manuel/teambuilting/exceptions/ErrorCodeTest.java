package org.manuel.teambuilting.exceptions;

import org.junit.jupiter.api.Test;
import org.manuel.teambuilting.players.model.entities.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * @author manuel.doncel.martos
 * @since 12-3-2017
 */
public class ErrorCodeTest {

	@Test
	public void testGetMessageOfIdNotFound() {
		final String expected = "Entity Player with id playerId not found";
		final String actual = ErrorCode.ID_NOT_FOUND.getMessage(Player.class.getSimpleName(), "playerId");
		assertEquals(actual, expected);
	}

}
