/**
 * 
 */
package org.manuel.teambuilting.players.services.command.impl;

import org.manuel.teambuilting.core.exceptions.ErrorCode;
import org.manuel.teambuilting.core.exceptions.ValidationRuntimeException;
import org.manuel.teambuilting.core.services.command.AbstractCommandService;
import org.manuel.teambuilting.players.model.entities.PlayerToTeam;
import org.manuel.teambuilting.players.repositories.PlayerToTeamRepository;
import org.manuel.teambuilting.players.services.command.PlayerToTeamCommandService;
import org.manuel.teambuilting.players.util.PlayerUtils;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Manuel Doncel Martos
 *
 */
@Service
class PlayerToTeamCommandServiceImpl extends AbstractCommandService<PlayerToTeam, BigInteger, PlayerToTeamRepository> implements
	PlayerToTeamCommandService {

	private final PlayerUtils playerUtils;

	public PlayerToTeamCommandServiceImpl(final PlayerToTeamRepository repository, final PlayerUtils playerUtils) {
		super(repository);
		this.playerUtils = playerUtils;
	}

	@Override
	protected void beforeSave(final PlayerToTeam playerToTeam) {
		final Collection<PlayerToTeam> historyOfThePlayerInTheTeamOverlapped = repository
			.findByPlayerIdAndTeamId(playerToTeam.getPlayerId(), playerToTeam.getTeamId())
				.stream().filter(overlapped(playerToTeam)).collect(Collectors.toList());
		if (!historyOfThePlayerInTheTeamOverlapped.isEmpty()) {
			throw new ValidationRuntimeException(ErrorCode.ENTRY_OVERLAPS, playerToTeam);
		}
	}

	private Predicate<PlayerToTeam> overlapped(final PlayerToTeam beforeSaveEntity) {
		return (entry) -> !entry.getId().equals(beforeSaveEntity.getId())
				&& playerUtils.isOverlapping(beforeSaveEntity, entry);
	}

}
