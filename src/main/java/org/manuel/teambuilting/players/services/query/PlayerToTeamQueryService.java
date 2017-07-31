/**
 * 
 */
package org.manuel.teambuilting.players.services.query;

import org.manuel.teambuilting.core.services.query.PlayerDependentQueryService;
import org.manuel.teambuilting.core.services.query.AbstractQueryService;
import org.manuel.teambuilting.players.model.entities.PlayerToTeam;
import org.manuel.teambuilting.players.repositories.PlayerToTeamRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Manuel Doncel Martos
 *
 */
@Service
public class PlayerToTeamQueryService extends AbstractQueryService<PlayerToTeam, BigInteger, PlayerToTeamRepository> implements PlayerDependentQueryService<PlayerToTeam> {

	@Inject
	public PlayerToTeamQueryService(final PlayerToTeamRepository playerToTeamRepository) {
		super(playerToTeamRepository);
	}

	public Set<PlayerToTeam> getPlayersFor(final String teamId, final Date date) {
		return new HashSet<>(repository
				.findByTeamIdAndToDateAfterOrToDateIsNull(teamId, date));
	}

	@Override
	public Collection<PlayerToTeam> findByPlayerId(final BigInteger playerId) {
		return repository.findByPlayerId(playerId);
	}
}
