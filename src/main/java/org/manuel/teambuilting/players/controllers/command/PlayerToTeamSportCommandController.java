package org.manuel.teambuilting.players.controllers.command;

import lombok.AllArgsConstructor;
import org.manuel.teambuilting.players.model.entities.PlayerToTeamSportDetails;
import org.manuel.teambuilting.players.services.command.PlayerToTeamSportDetailsCommandService;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;

@RestController
@RequestMapping("/players/{playerId}/details")
@AllArgsConstructor
public class PlayerToTeamSportCommandController {

	private final PlayerToTeamSportDetailsCommandService playerToTeamSportDetailsService;

	@RequestMapping(method = RequestMethod.POST)
	public PlayerToTeamSportDetails savePlayerDetails(@PathVariable("playerId") final BigInteger playerId,
			@Valid @RequestBody final PlayerToTeamSportDetails playerToTeamSportDetails) {
		Assert.notNull(playerId);
		Assert.isTrue(playerId.equals(playerToTeamSportDetails.getPlayerId()));
		return playerToTeamSportDetailsService.save(playerToTeamSportDetails);
	}

}
