package org.manuel.teambuilting.players.controllers.query;

import org.manuel.teambuilting.exceptions.ErrorCode;
import org.manuel.teambuilting.exceptions.ValidationRuntimeException;
import org.manuel.teambuilting.players.model.entities.PlayerToTeamSportDetails;
import org.manuel.teambuilting.players.services.query.PlayerToTeamSportDetailsQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/players/{playerId}/details")
public class PlayerToTeamSportDetailsQueryController {

	private final PlayerToTeamSportDetailsQueryService queryService;

	@Inject
	public PlayerToTeamSportDetailsQueryController(final PlayerToTeamSportDetailsQueryService queryService) {
		this.queryService = queryService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public Collection<PlayerToTeamSportDetails> findAllPlayerSportDetails(@PathVariable("playerId") final BigInteger playerId) {
		Assert.notNull(playerId);
		return queryService.findByPlayerId(playerId);
	}

	@RequestMapping(path = "/{sport}", method = RequestMethod.GET)
	public ResponseEntity<PlayerToTeamSportDetails> findPlayerSportDetailsForSport(@PathVariable("playerId") final BigInteger playerId, @PathVariable("sport") final String sport) {
		Assert.notNull(playerId);
		Assert.hasLength(sport);
		final Optional<PlayerToTeamSportDetails> playerToTeamSportDetails = queryService.findPlayerDetailsForSport(playerId, sport);
		if (playerToTeamSportDetails.isPresent()) {
			return ResponseEntity.ok(playerToTeamSportDetails.get());
		}
		throw new ValidationRuntimeException(ErrorCode.PLAYER_DETAIL_FOR_SPORT_NOT_FOUND, playerId, sport);
	}

}
