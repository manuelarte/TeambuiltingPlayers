/**
 * 
 */
package org.manuel.teambuilting.players.services.query;

import org.manuel.teambuilting.core.controllers.query.PlayerDependentQueryService;
import org.manuel.teambuilting.players.model.entities.Player;
import org.manuel.teambuilting.players.model.entities.PlayerToTeam;
import org.manuel.teambuilting.players.repositories.PlayerRepository;
import org.manuel.teambuilting.players.repositories.PlayerToTeamRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Manuel Doncel Martos
 *
 */
@Service
public class PlayerToTeamQueryService implements PlayerDependentQueryService<PlayerToTeam, BigInteger> {

	private final PlayerToTeamRepository playerToTeamRepository;
	private final PlayerRepository playerRepository;

	@Inject
	public PlayerToTeamQueryService(final PlayerToTeamRepository playerToTeamRepository,
			final PlayerRepository playerRepository) {
		this.playerToTeamRepository = playerToTeamRepository;
		this.playerRepository = playerRepository;
	}

	public Set<Player> getPlayersFor(final String teamId, final LocalDate time) {
		final Collection<PlayerToTeam> playersForTeam = playerToTeamRepository
				.findByToDateAfterOrToDateIsNullAndTeamId(time, teamId);
		return playersForTeam.stream()
				.map(playerId -> playerRepository.findOne(playerId.getPlayerId())).collect(Collectors.toSet());
	}

	@Override
	public Collection<PlayerToTeam> findByPlayerId(final BigInteger playerId) {
		return playerToTeamRepository.findByPlayerId(playerId);
	}
}
