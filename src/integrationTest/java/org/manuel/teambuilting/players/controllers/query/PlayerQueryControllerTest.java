package org.manuel.teambuilting.players.controllers.query;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.manuel.teambuilting.core.exceptions.ErrorCode;
import org.manuel.teambuilting.core.exceptions.ExceptionMessage;
import org.manuel.teambuilting.players.model.entities.Player;
import org.manuel.teambuilting.players.repositories.PlayerRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;
import java.util.Random;

import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author manuel.doncel.martos
 * @since 5-4-2017
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class PlayerQueryControllerTest {

	@Inject
	private WebApplicationContext context;

	@Inject
	private PlayerRepository playerRepository;

	private MockMvc mvc;

	private final ObjectMapper mapper = new ObjectMapper();

	@Before
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

	@Test
	public void findOnePlayerThatExists() throws Exception {
		final Player expected = Player.builder().name("name").build();
		playerRepository.save(expected);

		final String contentAsString = mvc.perform(get("/players/" + expected.getId()))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		final Player actual = mapper.readValue(contentAsString, Player.class);
		assertEquals(expected, actual);
	}

	@Test
	public void findOnePlayerThatDoesNotExist() throws Exception {
		final int id = new Random().nextInt();
		final String contentAsString = mvc.perform(get("/players/" + id, "")).andExpect(status().is4xxClientError()).andReturn().getResponse()
			.getContentAsString();

		final ExceptionMessage actual = mapper.readValue(contentAsString, ExceptionMessage.class);
		assertEquals(ErrorCode.ID_NOT_FOUND.getErrorCode(), actual.getErrorCode());
	}

	@Test
	public void findPlayersByName() throws Exception {
		final Player expected = Player.builder().name("name").build();
		final Player expected2 = Player.builder().name("name").build();
		playerRepository.save(expected);
		playerRepository.save(expected2);

		mvc.perform(get("/players?name=name", ""))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content", is(not(empty())))).andReturn().getResponse().getContentAsString();
		;
	}
}
