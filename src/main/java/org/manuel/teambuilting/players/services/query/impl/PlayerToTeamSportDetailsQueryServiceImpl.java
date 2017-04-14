package org.manuel.teambuilting.players.services.query.impl;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Optional;

import javax.inject.Inject;

import org.manuel.teambuilting.core.services.query.AbstractQueryService;
import org.manuel.teambuilting.players.model.entities.PlayerToTeamSportDetails;
import org.manuel.teambuilting.players.repositories.PlayerToTeamSportDetailsRepository;
import org.manuel.teambuilting.players.services.query.PlayerToTeamSportDetailsQueryService;
import org.springframework.stereotype.Service;

/**
 * @author Manuel Doncel Martos
 *
 */
@Service
class PlayerToTeamSportDetailsQueryServiceImpl extends AbstractQueryService<PlayerToTeamSportDetails, BigInteger, PlayerToTeamSportDetailsRepository> implements
	PlayerToTeamSportDetailsQueryService {

	@Inject
	public PlayerToTeamSportDetailsQueryServiceImpl(final PlayerToTeamSportDetailsRepository playerToTeamSportDetailsRepository) {
		super(playerToTeamSportDetailsRepository);
	}

	@Override
	public Collection<PlayerToTeamSportDetails> findByPlayerId(final BigInteger playerId) {
		return repository.findByPlayerId(playerId);
	}

	public Optional<PlayerToTeamSportDetails> findPlayerDetailsForSport(final BigInteger playerId, final String sport) {
		return Optional.ofNullable(repository.findByPlayerIdAndSportIgnoringCase(playerId, sport));
	}

}
