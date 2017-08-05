package org.manuel.teambuilting.players.controllers.query;

import org.manuel.teambuilting.core.controllers.query.AbstractQueryController;
import org.manuel.teambuilting.players.model.entities.Player;
import org.manuel.teambuilting.players.services.query.PlayerQueryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

@RestController
@RequestMapping("/players")
public class PlayerQueryController extends AbstractQueryController<Player, BigInteger, PlayerQueryService> {

	public PlayerQueryController(final PlayerQueryService playerQueryService) {
		super(playerQueryService);
	}

	@RequestMapping(method = RequestMethod.GET)
	public Page<Player> findPlayerByName(@PageableDefault(page = 0, size = 20) final Pageable pageable,
										 @RequestParam(value = "name", defaultValue = "") final String name) {
		Assert.notNull(name, "The name of the player cannot be null");
		return queryService.findPlayerByName(pageable, name);
	}

}
