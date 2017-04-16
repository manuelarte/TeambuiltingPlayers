package org.manuel.teambuilting.core.repositories;

import org.manuel.teambuilting.core.model.PlayerDependentEntity;

import java.math.BigInteger;
import java.util.Collection;

/**
 * @author manuel.doncel.martos
 * @since 5-4-2017
 */
public interface PlayerDependentRepository<Entity extends PlayerDependentEntity, ID> {

	Collection<Entity> findByPlayerId(BigInteger playerId);
}
