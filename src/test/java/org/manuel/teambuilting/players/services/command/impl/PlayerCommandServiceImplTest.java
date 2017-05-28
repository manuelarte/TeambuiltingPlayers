package org.manuel.teambuilting.players.services.command.impl;

import com.auth0.Auth0User;
import com.auth0.authentication.result.UserProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.manuel.teambuilting.messages.PlayerRegisteredEvent;
import org.manuel.teambuilting.players.model.entities.Player;
import org.manuel.teambuilting.players.repositories.PlayerRepository;
import org.manuel.teambuilting.players.util.Util;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * @author manuel.doncel.martos
 * @since 5-4-2017
 */
public class PlayerCommandServiceImplTest {

	@Mock
	private PlayerRepository playerRepository;
	@Mock
	private RabbitTemplate rabbitTemplate;
	@Mock
	private Util util;

	@InjectMocks
	private PlayerCommandServiceImpl playerCommandService;

	@BeforeEach
	public void beforeAll() {
		MockitoAnnotations.initMocks(this);
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

	@Test
	public void testSaveNullTeam() {
		final Optional<Auth0User> userProfile = Optional.of(createUserProfile());
		when(util.getUserProfile()).thenReturn(userProfile);
		assertThrows(IllegalArgumentException.class, ()->
			playerCommandService.save(null));
	}

	private Auth0User createUserProfile() {
		return new Auth0User(new UserProfile("id", "name", "nickname", "pictureURL", "email", true, "familyName", new Date(), new ArrayList<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), "givenName"));
	}
}
