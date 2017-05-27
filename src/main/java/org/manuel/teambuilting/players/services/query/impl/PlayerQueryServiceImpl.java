package org.manuel.teambuilting.players.services.query.impl;

import com.auth0.Auth0User;
import org.manuel.teambuilting.core.services.query.AbstractQueryService;
import org.manuel.teambuilting.messages.PlayerVisitedEvent;
import org.manuel.teambuilting.players.model.entities.Player;
import org.manuel.teambuilting.players.repositories.PlayerRepository;
import org.manuel.teambuilting.players.services.query.PlayerQueryService;
import org.manuel.teambuilting.players.util.Util;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigInteger;
import java.time.Instant;
import java.util.Optional;

/**
 * @author manuel.doncel.martos
 * @since 16-12-2016
 */
@Service
class PlayerQueryServiceImpl extends AbstractQueryService<Player, BigInteger, PlayerRepository> implements PlayerQueryService {

	/**
	 * Exchange name for the players
	 */
	private final String playerExchangeName;
	private final RabbitTemplate rabbitTemplate;
	private final Util util;

	public PlayerQueryServiceImpl(final @Value("${messaging.amqp.player.exchange.name}") String playerExchangeName,
		final PlayerRepository playerRepository, final RabbitTemplate rabbitTemplate, final Util util) {
		super(playerRepository);
		this.playerExchangeName = playerExchangeName;
		this.rabbitTemplate = rabbitTemplate;
		this.util = util;
	}

	@Override
	protected void postFindOne(final Optional<Player> player) {
		Assert.notNull(player, "Player cannot be null");
		if (player.isPresent()) {
			sendPlayerVisitedMessage(player.get());
		}
	}

	@Override
	public Page<Player> findPlayerByName(final Pageable pageable, final String name) {
		return repository.findByNameLikeIgnoreCase(pageable, name);
	}

	private void sendPlayerVisitedMessage(final Player visitedPlayer) {
		final Optional<Auth0User> userProfile = util.getUserProfile();
		final String userId = userProfile.isPresent() ? userProfile.get().getUserId() : null;
		final PlayerVisitedEvent event = new PlayerVisitedEvent(visitedPlayer.getId(), userId, Instant.now());
		// rabbitTemplate.convertAndSend(playerExchangeName, event.getRoutingKey(), event);
	}

}
