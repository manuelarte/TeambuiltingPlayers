package org.manuel.teambuilting.players.listeners;

import lombok.AllArgsConstructor;
import org.manuel.teambuilting.messages.PlayerDeletedEvent;
import org.manuel.teambuilting.players.repositories.PlayerGeocodingRepository;
import org.manuel.teambuilting.players.repositories.PlayerToTeamRepository;
import org.manuel.teambuilting.players.repositories.PlayerToTeamSportDetailsRepository;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

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

    private final PlayerToTeamRepository playerToTeamRepository;
    private final PlayerToTeamSportDetailsRepository playerToTeamSportDetailsRepository;
    private final PlayerGeocodingRepository playerGeocodingRepository;

    @RabbitHandler
    public void handle(final PlayerDeletedEvent event) {
        playerToTeamRepository.delete(playerToTeamRepository.findByPlayerId(event.getPlayerId()));
        playerToTeamSportDetailsRepository.delete(playerToTeamSportDetailsRepository.findByPlayerId(event.getPlayerId()));
        playerGeocodingRepository.delete(playerGeocodingRepository.findByPlayerId(event.getPlayerId()));
    }

}
