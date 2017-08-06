package org.manuel.teambuilting.players.services.command.impl;

import com.auth0.Auth0User;
import org.manuel.teambuilting.core.services.command.AbstractCommandService;
import org.manuel.teambuilting.messages.PlayerDeletedEvent;
import org.manuel.teambuilting.messages.PlayerRegisteredEvent;
import org.manuel.teambuilting.players.model.entities.Player;
import org.manuel.teambuilting.players.repositories.PlayerRepository;
import org.manuel.teambuilting.players.services.command.PlayerCommandService;
import org.manuel.teambuilting.core.utils.Util;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigInteger;
import java.util.Date;

@Service
class PlayerCommandServiceImpl extends AbstractCommandService<Player, BigInteger, PlayerRepository> implements PlayerCommandService {

	private final String playerExchangeName;
	private final RabbitTemplate rabbitTemplate;
	private final Util util;

	public PlayerCommandServiceImpl(final @Value("${messaging.amqp.player.exchange.name}") String playerExchangeName,
		final PlayerRepository repository, final RabbitTemplate rabbitTemplate, final Util util) {
		super(repository);
		this.playerExchangeName = playerExchangeName;
		this.rabbitTemplate = rabbitTemplate;
		this.util = util;
	}

	@Override
	protected void afterSaved(final Player player) {
		sendPlayerRegisteredEvent(player);
	}

	@Override
	protected void afterDeleted(final BigInteger playerId) {
		sendPlayerDeletedEvent(playerId);
	}

	@Override
	public Player update(final Player player) {
		Assert.notNull(player, "The entity cannot be null");
		Assert.notNull(player.getId(), "The id cannot be null");
		return repository.save(player);
	}

	private void sendPlayerRegisteredEvent(final Player player) {
		final Auth0User userProfile = util.getUserProfile().get();
		final PlayerRegisteredEvent event = new PlayerRegisteredEvent(player.getId(), userProfile.getUserId(), new Date());
		rabbitTemplate.convertAndSend(playerExchangeName, event.getRoutingKey(), event);
	}

	private void sendPlayerDeletedEvent(final BigInteger playerId) {
		final Auth0User userProfile = util.getUserProfile().get();
		final PlayerDeletedEvent event = new PlayerDeletedEvent(playerId, userProfile.getUserId(), new Date());
		rabbitTemplate.convertAndSend(playerExchangeName, event.getRoutingKey(), event);
	}

}