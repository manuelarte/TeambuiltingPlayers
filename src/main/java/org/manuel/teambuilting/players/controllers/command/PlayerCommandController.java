package org.manuel.teambuilting.players.controllers.command;

import lombok.AllArgsConstructor;
import org.manuel.teambuilting.exceptions.ValidationRuntimeException;
import org.manuel.teambuilting.players.model.entities.Player;
import org.manuel.teambuilting.players.services.command.PlayerCommandService;
import org.manuel.teambuilting.players.services.geocoding.PlayerGeocodingService;
import org.manuel.teambuilting.players.services.query.PlayerQueryService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/players")
@AllArgsConstructor
public class PlayerCommandController {

	private final PlayerCommandService playerCommandService;
	private final PlayerQueryService playerQueryService;
	private final PlayerGeocodingService playerGeocodingService;

	@RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT}, produces = "application/json")
	public Player updatePlayer(@Valid @RequestBody final Player player) {
		if (player.getId() == null || !playerQueryService.findOne(player.getId()).isPresent()) {
			throw new ValidationRuntimeException("Players cannot be created with a rest endpoint",
					"Players cannot be created with a rest endpoint");
		}
		final Player saved = playerCommandService.update(player);
		if (Optional.ofNullable(saved.getBornAddress()).isPresent()) {
			playerGeocodingService.asyncReq(saved);
		}
		return saved;
	}

}
