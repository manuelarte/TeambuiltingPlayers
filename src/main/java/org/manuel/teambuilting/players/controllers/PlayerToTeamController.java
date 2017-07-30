package org.manuel.teambuilting.players.controllers;

import org.manuel.teambuilting.core.controllers.query.AbstractQueryController;
import org.manuel.teambuilting.players.model.entities.PlayerToTeam;
import org.manuel.teambuilting.players.services.command.PlayerToTeamCommandService;
import org.manuel.teambuilting.players.services.query.PlayerToTeamQueryService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

@RestController
@RequestMapping("/playersToTeams")
public class PlayerToTeamController extends AbstractQueryController<PlayerToTeam, BigInteger, PlayerToTeamQueryService> {

	private final PlayerToTeamCommandService playerToTeamCommandService;

	public PlayerToTeamController(final PlayerToTeamQueryService playerToTeamQueryService, final PlayerToTeamCommandService playerToTeamCommandService) {
		super(playerToTeamQueryService);
		this.playerToTeamCommandService = playerToTeamCommandService;
	}

	@GetMapping
	public Collection<PlayerToTeam> findPlayerHistory(@RequestParam(value = "playerId", defaultValue = "") final BigInteger playerId) {
		Assert.notNull(playerId, "The playerId cannot be null");
		return queryService.findByPlayerId(playerId);
	}

	@PostMapping(produces = "application/json")
	public PlayerToTeam savePlayerToTeam(@Valid @RequestBody final PlayerToTeam playerToTeam) {
		Assert.notNull(playerToTeam, "The entry cannot be null");
		return playerToTeamCommandService.save(playerToTeam);
	}

	@DeleteMapping(value = "/{playerToTeamId}", produces = "application/json")
	public void deletePlayerToTeam(@PathVariable("playerToTeamId") final BigInteger playerToTeamId) {
		playerToTeamCommandService.delete(playerToTeamId);
	}

	@GetMapping(path = "/teams/{teamId}")
	public Set<PlayerToTeam> getPlayersForTeam(@PathVariable final String teamId,
										 @RequestParam(value = "date", required = false, defaultValue = "1900-01-01") @DateTimeFormat(pattern = "yyyy-MM-dd") final Date date) {
		Assert.hasLength(teamId, "The teamId cannot be null");
		return queryService.getPlayersFor(teamId, date);
	}

}
