package org.manuel.teambuilting.players.controllers.command;

import lombok.AllArgsConstructor;
import org.manuel.teambuilting.players.model.entities.Player;
import org.manuel.teambuilting.players.services.command.PlayerCommandService;
import org.manuel.teambuilting.players.services.geocoding.PlayerGeocodingService;
import org.manuel.teambuilting.players.services.query.PlayerQueryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.Optional;

@RestController
@RequestMapping("/players")
@AllArgsConstructor
public class PlayerCommandController {

	private final PlayerCommandService playerCommandService;
	private final PlayerGeocodingService playerGeocodingService;
	private final PlayerQueryService playerQueryService;

	@RequestMapping(method = RequestMethod.POST, produces = "application/json")
	public Player savePlayer(@Valid @RequestBody final Player player) {
		final Player saved = playerCommandService.save(player);
		if (Optional.ofNullable(saved.getBornAddress()).isPresent()) {
			playerGeocodingService.asyncReq(saved);
		}
		return saved;
	}

	@RequestMapping(path = "/{playerId}", method = RequestMethod.DELETE)
	public ResponseEntity<Player> deletePlayer(@PathVariable("playerId") final BigInteger playerId) {
		final Optional<Player> player = playerQueryService.findOne(playerId);
		if (!player.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		playerCommandService.delete(playerId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
