/**
 * 
 */
package org.manuel.teambuilting.players.services.query;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.manuel.teambuilting.core.controllers.query.PlayerDependentQueryService;
import org.manuel.teambuilting.core.services.query.AbstractQueryService;
import org.manuel.teambuilting.players.model.entities.PlayerToTeam;
import org.manuel.teambuilting.players.repositories.PlayerRepository;
import org.manuel.teambuilting.players.repositories.PlayerToTeamRepository;
import org.springframework.stereotype.Service;

/**
 * @author Manuel Doncel Martos
 *
 */
@Service
public class PlayerToTeamQueryService extends AbstractQueryService<PlayerToTeam, BigInteger, PlayerToTeamRepository> implements PlayerDependentQueryService<PlayerToTeam, BigInteger> {

	private final PlayerRepository playerRepository;

	@Inject
	public PlayerToTeamQueryService(final PlayerToTeamRepository playerToTeamRepository,
			final PlayerRepository playerRepository) {
		super(playerToTeamRepository);
		this.playerRepository = playerRepository;
	}

	public Set<PlayerToTeam> getPlayersFor(final String teamId, final Date date) {
		return repository
				.findByTeamIdAndToDateAfterOrToDateIsNull(teamId, date).stream().collect(Collectors.toSet());
	}

	@Override
	public Collection<PlayerToTeam> findByPlayerId(final BigInteger playerId) {
		return repository.findByPlayerId(playerId);
	}
}
