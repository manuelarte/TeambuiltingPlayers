package org.manuel.teambuilting.players.services.command;

import java.math.BigInteger;

import org.manuel.teambuilting.core.services.command.BaseCommandService;
import org.manuel.teambuilting.players.model.entities.Player;

/**
 * @author manuel.doncel.martos
 * @since 14-3-2017
 */
public interface PlayerCommandService extends BaseCommandService<Player, BigInteger> {

	/**
	 * @return the updated player
	 */
	Player update(Player player);

}
