package org.manuel.teambuilting.players.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.manuel.teambuilting.players.model.entities.Player;
import org.manuel.teambuilting.players.model.entities.PlayerToTeam;
import org.manuel.teambuilting.players.repositories.PlayerRepository;
import org.manuel.teambuilting.players.repositories.PlayerToTeamRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Manuel Doncel Martos
 * @since 16/04/2017.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class PlayerToTeamControllerTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Inject
    private WebApplicationContext context;

    @Inject
    private PlayerRepository playerRepository;

    @Inject
    private PlayerToTeamRepository playerToTeamRepository;

    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void findOnePlayerToTeam() throws Exception {
        final Player player = Player.builder().name("name").build();
        playerRepository.save(player);
        final PlayerToTeam expected = PlayerToTeam.builder().playerId(player.getId()).teamId("teamId").fromDate(new Date()).build();
        playerToTeamRepository.save(expected);

        final String contentAsString = mvc.perform(get("/playersToTeams?playerId=" + player.getId(), "")).andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();
        final List<PlayerToTeam> actual = mapper.readValue(contentAsString, new TypeReference<List<PlayerToTeam>>(){});

        assertTrue(actual.size() == 1);
        assertEquals(expected, actual.get(0));
    }

    @Test
    public void findPlayerToTeamForTeamId() throws Exception {
        final Player player = Player.builder().name("name").build();
        playerRepository.save(player);
        final PlayerToTeam playerToTeam = PlayerToTeam.builder().playerId(player.getId()).teamId("teamId").fromDate(new Date()).build();
        playerToTeamRepository.save(playerToTeam);

        final String contentAsString = mvc.perform(get("/playersToTeams/teams/teamId", "")).andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();
        final List<Player> actual = mapper.readValue(contentAsString, new TypeReference<List<Player>>(){});

        assertTrue(actual.size() == 1);
        assertEquals(player, actual.get(0));
    }

}
