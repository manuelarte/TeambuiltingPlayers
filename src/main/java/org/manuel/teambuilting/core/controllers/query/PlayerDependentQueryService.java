package org.manuel.teambuilting.core.controllers.query;

import java.math.BigInteger;
import java.util.Collection;

/**
 * @author manuel.doncel.martos
 * @since 5-4-2017
 */
public interface PlayerDependentQueryService<Entity, ID> {

	/**
	 * Returns the entities that belong to the player desired
	 *
	 * @param playerId
	 * @return all the entities that belong to the player
	 */
	Collection<Entity> findByPlayerId(BigInteger playerId);
}
