package org.manuel.teambuilting.players.services.query;

import org.manuel.teambuilting.players.model.entities.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigInteger;

/**
 * @author manuel.doncel.martos
 * @since 14-3-2017
 */
public interface PlayerQueryService extends BaseQueryService<Player, BigInteger> {

	Page<Player> findPlayerByName(final Pageable pageable, final String name);

}
