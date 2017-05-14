package org.manuel.teambuilting.players.controllers;

import org.manuel.teambuilting.core.controllers.query.AbstractQueryController;
import org.manuel.teambuilting.players.model.entities.PlayerToTeam;
import org.manuel.teambuilting.players.services.command.PlayerToTeamCommandService;
import org.manuel.teambuilting.players.services.query.PlayerToTeamQueryService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

@RestController
@RequestMapping("/playersToTeams")
public class PlayerToTeamController extends AbstractQueryController<PlayerToTeam, BigInteger, PlayerToTeamQueryService> {

	private final PlayerToTeamCommandService playerToTeamCommandService;

	@Inject
	public PlayerToTeamController(final PlayerToTeamQueryService playerToTeamQueryService, final PlayerToTeamCommandService playerToTeamCommandService) {
		super(playerToTeamQueryService);
		this.playerToTeamCommandService = playerToTeamCommandService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public Collection<PlayerToTeam> findPlayerHistory(@RequestParam(value = "playerId", defaultValue = "") final BigInteger playerId) {
		Assert.notNull(playerId);
		return queryService.findByPlayerId(playerId);
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public PlayerToTeam savePlayerToTeam(@Valid @RequestBody final PlayerToTeam playerToTeam) {
		Assert.notNull(playerToTeam, "The entry cannot be null");
		return playerToTeamCommandService.save(playerToTeam);
	}

	@RequestMapping(value = "/{playerToTeamId}", method = RequestMethod.DELETE, produces = "application/json")
	public void deletePlayerToTeam(@PathVariable("playerToTeamId") final BigInteger playerToTeamId) {
		playerToTeamCommandService.delete(playerToTeamId);
	}

	@RequestMapping(path = "/teams/{teamId}", method = RequestMethod.GET)
	public Set<PlayerToTeam> getPlayersForTeam(@PathVariable final String teamId,
										 @RequestParam(value = "date", required = false, defaultValue = "1900-01-01") @DateTimeFormat(pattern = "yyyy-MM-dd") final Date date) {
		Assert.hasLength(teamId);
		return queryService.getPlayersFor(teamId, date);
	}

}
