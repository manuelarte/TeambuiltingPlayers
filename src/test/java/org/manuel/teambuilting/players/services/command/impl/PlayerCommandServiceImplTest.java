package org.manuel.teambuilting.players.services.command.impl;

import com.auth0.Auth0User;
import com.auth0.authentication.result.UserProfile;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.manuel.teambuilting.messages.PlayerRegisteredEvent;
import org.manuel.teambuilting.players.model.entities.Player;
import org.manuel.teambuilting.players.repositories.PlayerRepository;
import org.manuel.teambuilting.players.util.Util;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

import static org.mockito.Mockito.*;

/**
 * @author manuel.doncel.martos
 * @since 5-4-2017
 */
@RunWith(MockitoJUnitRunner.class)
public class PlayerCommandServiceImplTest {

	@Mock
	private PlayerRepository playerRepository;
	@Mock
	private RabbitTemplate rabbitTemplate;
	@Mock
	private Util util;

	@InjectMocks
	private PlayerCommandServiceImpl playerCommandService;

	@Before
	public void setup() {
	}

	@Test
	public void testSaveProperPlayer() {
		final Optional<Auth0User> userProfile = Optional.of(createUserProfile());
		when(util.getUserProfile()).thenReturn(userProfile);
		final Player player = Player.builder().name("player").nickname("nickname").bornAddress("Ubeda, Jaen, Spain").build();
		when(playerRepository.save(player)).thenReturn(player);
		playerCommandService.save(player);
		verify(playerRepository, times(1)).save(player);
		verify(rabbitTemplate, times(1)).convertAndSend(any(String.class), eq(PlayerRegisteredEvent.ROUTING_KEY), any(PlayerRegisteredEvent.class));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testSaveNullTeam() {
		final Optional<Auth0User> userProfile = Optional.of(createUserProfile());
		when(util.getUserProfile()).thenReturn(userProfile);
		playerCommandService.save(null);
	}

	private Auth0User createUserProfile() {
		return new Auth0User(new UserProfile("id", "name", "nickname", "pictureURL", "email", true, "familyName", new Date(), new ArrayList<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), "givenName"));
	}
}
