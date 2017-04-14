package org.manuel.teambuilting.players.services.command.impl;

import com.auth0.authentication.result.UserProfile;
import org.manuel.teambuilting.core.services.command.AbstractCommandService;
import org.manuel.teambuilting.messages.PlayerDeletedEvent;
import org.manuel.teambuilting.players.aspects.UserDataDeletePlayer;
import org.manuel.teambuilting.players.model.entities.Player;
import org.manuel.teambuilting.players.repositories.PlayerRepository;
import org.manuel.teambuilting.players.services.command.PlayerCommandService;
import org.manuel.teambuilting.players.util.Util;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.math.BigInteger;
import java.util.Date;

@Service
class PlayerCommandServiceImpl extends AbstractCommandService<Player, BigInteger, PlayerRepository> implements PlayerCommandService {

	private final String playerExchangeName;
	private final RabbitTemplate rabbitTemplate;
	private final Util util;

	@Inject
	public PlayerCommandServiceImpl(final @Value("${messaging.amqp.player.exchange.name}") String playerExchangeName,
		final PlayerRepository repository, final RabbitTemplate rabbitTemplate, final Util util) {
		super(repository);
		this.playerExchangeName = playerExchangeName;
		this.rabbitTemplate = rabbitTemplate;
		this.util = util;
	}

	@Override
    @UserDataDeletePlayer
	protected void afterDeleted(final BigInteger playerId) {
		sendPlayerDeletedEvent(playerId);
	}

	private void sendPlayerDeletedEvent(final BigInteger playerId) {
		final UserProfile userProfile = util.getUserProfile().get();
		final PlayerDeletedEvent event = new PlayerDeletedEvent(playerId, userProfile.getId(), new Date());
		rabbitTemplate.convertAndSend(playerExchangeName, event.getRoutingKey(), event);
	}

}