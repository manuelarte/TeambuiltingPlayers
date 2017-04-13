package org.manuel.teambuilting.players.services.command.impl;

import org.manuel.teambuilting.players.exceptions.ValidationRuntimeException;
import org.manuel.teambuilting.players.model.entities.PlayerToTeamSportDetails;
import org.manuel.teambuilting.players.repositories.PlayerToTeamSportDetailsRepository;
import org.manuel.teambuilting.players.services.command.PlayerToTeamSportDetailsCommandService;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Optional;

import static org.manuel.teambuilting.players.exceptions.ErrorCode.SPORT_DETAIL_DUPLICATED;

/**
 * @author Manuel Doncel Martos
 *
 */
@Service
class PlayerToTeamSportDetailsCommandServiceImpl extends AbstractCommandService<PlayerToTeamSportDetails, BigInteger, PlayerToTeamSportDetailsRepository> implements
	PlayerToTeamSportDetailsCommandService {

	public PlayerToTeamSportDetailsCommandServiceImpl(final PlayerToTeamSportDetailsRepository playerToTeamSportDetailsRepository) {
		super(playerToTeamSportDetailsRepository);
	}

	@Override
	void beforeSave(final PlayerToTeamSportDetails beforeSaveEntity) {
		final Optional<PlayerToTeamSportDetails> previousEntity = Optional.ofNullable(repository.findByPlayerIdAndSportIgnoringCase(beforeSaveEntity.getPlayerId(), beforeSaveEntity.getSport()));
		if (previousEntity.isPresent() && !previousEntity.get().getId().equals(beforeSaveEntity.getId())) {
			throw new ValidationRuntimeException(SPORT_DETAIL_DUPLICATED, beforeSaveEntity.getSport());
		}
	}

}
