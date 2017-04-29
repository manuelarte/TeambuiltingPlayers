package org.manuel.teambuilting.players.controllers.command;

import java.util.Optional;

import javax.validation.Valid;

import org.manuel.teambuilting.players.model.entities.Player;
import org.manuel.teambuilting.players.services.command.PlayerCommandService;
import org.manuel.teambuilting.players.services.geocoding.PlayerGeocodingService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/players")
@AllArgsConstructor
public class PlayerCommandController {

	private final PlayerCommandService playerCommandService;
	private final PlayerGeocodingService playerGeocodingService;

	@RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT}, produces = "application/json")
	public Player updatePlayer(@Valid @RequestBody final Player player) {
		final Player saved = playerCommandService.update(player);
		if (Optional.ofNullable(saved.getBornAddress()).isPresent()) {
			playerGeocodingService.asyncReq(saved);
		}
		return saved;
	}

}
