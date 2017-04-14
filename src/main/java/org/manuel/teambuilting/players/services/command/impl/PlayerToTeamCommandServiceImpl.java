/**
 * 
 */
package org.manuel.teambuilting.players.services.command.impl;

import org.manuel.teambuilting.core.services.command.AbstractCommandService;
import org.manuel.teambuilting.exceptions.ErrorCode;
import org.manuel.teambuilting.exceptions.ValidationRuntimeException;
import org.manuel.teambuilting.players.model.entities.PlayerToTeam;
import org.manuel.teambuilting.players.repositories.PlayerToTeamRepository;
import org.manuel.teambuilting.players.services.command.PlayerToTeamCommandService;
import org.manuel.teambuilting.players.util.Util;
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

	private final Util util;

	public PlayerToTeamCommandServiceImpl(final PlayerToTeamRepository repository, Util util) {
		super(repository);
		this.util = util;
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
				&& util.isOverlapping(beforeSaveEntity, entry);
	}

}
