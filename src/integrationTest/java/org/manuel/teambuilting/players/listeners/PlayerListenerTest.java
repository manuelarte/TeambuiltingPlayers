package org.manuel.teambuilting.players.listeners;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigInteger;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.manuel.teambuilting.messages.PlayerDeletedEvent;
import org.manuel.teambuilting.players.model.entities.Player;
import org.manuel.teambuilting.players.model.entities.PlayerToTeam;
import org.manuel.teambuilting.players.repositories.PlayerToTeamRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.test.RabbitListenerTest;
import org.springframework.amqp.rabbit.test.RabbitListenerTestHarness;
import org.springframework.amqp.rabbit.test.RabbitListenerTestHarness.InvocationData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author manuel.doncel.martos
 * @since 11-3-2017
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@RabbitListenerTest(capture = true)
public class PlayerListenerTest {

	@Value("${messaging.amqp.player.exchange.name}")
	private String playerExchange;

	@Inject
	private PlayerToTeamRepository playerToTeamRepository;

	@Inject
	private RabbitTemplate rabbitTemplate;

	@Inject
	private RabbitListenerTestHarness harness;

	@Test
	public void deletePlayerTest() throws InterruptedException {
		final Player player = Player.builder().id(BigInteger.ONE).build();
		savePlayerToTeam(player);

		final PlayerDeletedEvent event = PlayerDeletedEvent.builder().playerId(player.getId()).date(Instant.now()).userId("userId").build();

		rabbitTemplate.convertAndSend(playerExchange, PlayerDeletedEvent.ROUTING_KEY, event);

		final InvocationData data = harness.getNextInvocationDataFor(PlayerListener.LISTENER_ID, 5, TimeUnit.SECONDS);
		assertNotNull(data);
		assertEquals(1, data.getArguments().length);
		assertEquals(event, data.getArguments()[0]);
		assertEquals(0, playerToTeamRepository.findAll().size());
	}

	private void savePlayerToTeam(final Player player) {
		final PlayerToTeam playerToTeam = PlayerToTeam.builder().playerId(player.getId()).teamId("teamId").fromDate(new Date()).toDate(new Date()).build();
		playerToTeamRepository.save(playerToTeam);
	}

}
