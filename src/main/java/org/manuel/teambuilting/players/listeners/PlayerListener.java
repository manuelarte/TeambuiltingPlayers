package org.manuel.teambuilting.players.listeners;

import lombok.AllArgsConstructor;
import org.manuel.teambuilting.messages.PlayerDeletedEvent;
import org.manuel.teambuilting.messages.PlayerRegisteredEvent;
import org.manuel.teambuilting.players.model.entities.Player;
import org.manuel.teambuilting.players.repositories.PlayerGeocodingRepository;
import org.manuel.teambuilting.players.repositories.PlayerToTeamRepository;
import org.manuel.teambuilting.players.services.geocoding.PlayerGeocodingService;
import org.manuel.teambuilting.players.services.query.PlayerQueryService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.manuel.teambuilting.players.listeners.PlayerListener.LISTENER_ID;

/**
 * Listener for the player topic
 *
 * @author Manuel Doncel Martos
 * @since 31/12/2016.
 */
@RabbitListener(id = LISTENER_ID, bindings = @QueueBinding(
        value = @Queue(value = "${messaging.amqp.player.queue.name}",
            durable = "${messaging.amqp.player.queue.durable}", autoDelete = "${messaging.amqp.player.queue.autodelete}"),
        exchange = @Exchange(value = "${messaging.amqp.player.exchange.name}", type = ExchangeTypes.TOPIC,
            durable = "${messaging.amqp.player.exchange.durable}", autoDelete = "${messaging.amqp.player.exchange.autodelete}"),
        key = "${messaging.amqp.player.queue.binding}"))
@Component
@AllArgsConstructor
public class PlayerListener {

    public static final String LISTENER_ID = "PlayerListenerId";

    private final PlayerGeocodingService playerGeocodingService;
    private final PlayerQueryService playerQueryService;
    private final PlayerToTeamRepository playerToTeamRepository;
    private final PlayerGeocodingRepository playerGeocodingRepository;

    @RabbitHandler
    public void handle(final PlayerDeletedEvent event) {
        playerToTeamRepository.delete(playerToTeamRepository.findByPlayerId(event.getPlayerId()));
        playerGeocodingRepository.delete(playerGeocodingRepository.findByPlayerId(event.getPlayerId()));
    }

    @RabbitHandler
    public void handle(final PlayerRegisteredEvent event) {
        final Optional<Player> player = playerQueryService.findOne(event.getPlayerId());
        if (player.isPresent() && Optional.ofNullable(player.get().getBornAddress()).isPresent()) {
            playerGeocodingService.asyncReq(player.get());
        }
    }

}
