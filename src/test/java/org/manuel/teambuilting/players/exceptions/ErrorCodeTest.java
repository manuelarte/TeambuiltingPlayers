package org.manuel.teambuilting.players.exceptions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.manuel.teambuilting.players.model.entities.Player;

/**
 * @author manuel.doncel.martos
 * @since 12-3-2017
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class ErrorCodeTest {

	@Test
	public void testGetMessageOfIdNotFound() {
		final String expected = "Entity Player with id playerId not found";
		final String actual = ErrorCode.ID_NOT_FOUND.getMessage(Player.class.getSimpleName(), "playerId");
		assertEquals(actual, expected);
	}

}
