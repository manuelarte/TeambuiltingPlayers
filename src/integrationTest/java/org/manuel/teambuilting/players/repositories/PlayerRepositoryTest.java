package org.manuel.teambuilting.players.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import javax.inject.Inject;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.manuel.teambuilting.players.model.entities.Player;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author manuel.doncel.martos
 * @since 29-5-2017
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class PlayerRepositoryTest {

	@Inject
	private PlayerRepository playerRepository;

	@After
	public void after() {
		playerRepository.deleteAll();
	}

	@Test
	public void testRetrievePlayerWithSameName() {
		final String name = "Manuel Doncel Martos";
		final Player player = Player.builder().name(name).nickname("Manuel D").build();
		playerRepository.save(player);

		final Page<Player> byContainingName = playerRepository.findByNameContainingIgnoreCase(null, name);
		assertEquals(1, byContainingName.getTotalElements());
		assertEquals(player, byContainingName.iterator().next());
	}

	@Test
	public void testRetrieveNoMatchingName() {
		final String name = "Not Existing";
		final Player player = Player.builder().name("Manuel Doncel Martos").nickname("Manuel D").build();
		playerRepository.save(player);

		final Page<Player> byContainingName = playerRepository.findByNameContainingIgnoreCase(null, name);
		assertEquals(0, byContainingName.getTotalElements());
	}

	@Test
	public void testRetrievePlayersWithAlikeName() {
		final String name = "Manuel";
		final Player player = Player.builder().name(name + " Doncel Martos").nickname("Manuel D").build();
		final Player playerTwo = Player.builder().name(name + " Martos Doncel").nickname("Manuel D").build();
		playerRepository.save(Arrays.asList(player, playerTwo));

		final Page<Player> byContainingName = playerRepository.findByNameContainingIgnoreCase(null, name);
		assertEquals(2, byContainingName.getTotalElements());
		assertTrue(byContainingName.getContent().contains(player));
		assertTrue(byContainingName.getContent().contains(playerTwo));
	}

	@Test
	public void testRetrievePlayerWithAlikeName() {
		final String name = "Manuel";
		final Player player = Player.builder().name(name + " Doncel Martos").nickname("Manuel D").build();
		final Player playerTwo = Player.builder().name("Another Name").nickname("AnotherNick").build();
		playerRepository.save(Arrays.asList(player, playerTwo));

		final Page<Player> byNameLikeIgnoreCase = playerRepository.findByNameContainingIgnoreCase(null, name);
		assertEquals(1, byNameLikeIgnoreCase.getTotalElements());
		assertTrue(byNameLikeIgnoreCase.getContent().contains(player));
	}

	@Test
	public void testRetrievePlayerWithAlikeNameLowerLetter() {
		final String name = "Manuel";
		final String nameLooked = name.toLowerCase();
		final Player player = Player.builder().name(name + " Doncel Martos").nickname("Manuel D").build();
		final Player playerTwo = Player.builder().name("Another Name").nickname("AnotherNick").build();
		playerRepository.save(Arrays.asList(player, playerTwo));

		final Page<Player> byNameLikeIgnoreCase = playerRepository.findByNameContainingIgnoreCase(null, nameLooked);
		assertEquals(1, byNameLikeIgnoreCase.getTotalElements());
		assertTrue(byNameLikeIgnoreCase.getContent().contains(player));
	}

}
