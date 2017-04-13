package org.manuel.teambuilting.players.controllers;

import org.manuel.teambuilting.players.model.entities.PlayerToTeam;
import org.manuel.teambuilting.players.services.command.PlayerToTeamCommandService;
import org.manuel.teambuilting.players.services.query.PlayerToTeamQueryService;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.math.BigInteger;
import java.util.Collection;

@RestController
@RequestMapping("/players/{playerId}/teams")
public class PlayerToTeamController {

	private final PlayerToTeamQueryService playerToTeamQueryService;
	private final PlayerToTeamCommandService playerToTeamCommandService;

	@Inject
	public PlayerToTeamController(final PlayerToTeamQueryService playerToTeamQueryService, final PlayerToTeamCommandService playerToTeamCommandService) {
		this.playerToTeamQueryService = playerToTeamQueryService;
		this.playerToTeamCommandService = playerToTeamCommandService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public Collection<PlayerToTeam> findPlayerHistory(@PathVariable("playerId") final BigInteger playerId) {
		Assert.notNull(playerId);
		return playerToTeamQueryService.findByPlayerId(playerId);
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public PlayerToTeam savePlayerToTeam(@PathVariable("playerId") final BigInteger playerId,
			@Valid @RequestBody final PlayerToTeam playerToTeam) {
		Assert.notNull(playerToTeam);
		Assert.isTrue(playerToTeam.getPlayerId().equals(playerId));
		return playerToTeamCommandService.save(playerToTeam);
	}

	@RequestMapping(value = "/{playerToTeamId}", method = RequestMethod.DELETE, produces = "application/json")
	public void deletePlayerToTeam(@PathVariable("playerId") final BigInteger playerId,
										   @PathVariable("playerToTeamId") final BigInteger playerToTeamId) {
		playerToTeamCommandService.delete(playerToTeamId);
	}

}
