package org.manuel.teambuilting.players.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.manuel.teambuilting.players.TestUtils;
import org.manuel.teambuilting.players.model.entities.Player;
import org.manuel.teambuilting.players.model.entities.PlayerToTeam;
import org.manuel.teambuilting.players.model.entities.UserData;
import org.manuel.teambuilting.players.repositories.PlayerRepository;
import org.manuel.teambuilting.players.repositories.PlayerToTeamRepository;
import org.manuel.teambuilting.players.repositories.UserDataRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.inject.Inject;
import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Manuel Doncel Martos
 * @since 16/04/2017.
 */
@WebAppConfiguration
@EnableWebMvc
@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PlayerToTeamControllerTest {

    private static TestUtils.TestUser user;

    @Inject
    private ObjectMapper mapper;

    @Inject
    private FilterChainProxy springSecurityFilterChain;

    @Inject
    private WebApplicationContext context;

    @Inject
    private PlayerRepository playerRepository;

    @Inject
    private UserDataRepository userDataRepository;

    @Inject
    private PlayerToTeamRepository playerToTeamRepository;

    private MockMvc mvc;

    @BeforeClass
    public static void beforeClass() {
        user = TestUtils.manuel();
    }

    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilters(this.springSecurityFilterChain)
                .build();
    }

    @Test
    public void findPlayerToTeamById() throws Exception {
        final Player player = Player.builder().name("name").build();
        playerRepository.save(player);
        final PlayerToTeam expected = PlayerToTeam.builder().playerId(player.getId()).teamId("teamId")
                .fromDate(new Date()).build();
        playerToTeamRepository.save(expected);

        final String contentAsString = mvc.perform(get("/playersToTeams/" + expected.getId(), ""))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        final PlayerToTeam actual = mapper.readValue(contentAsString, PlayerToTeam.class);

        assertEquals(expected, actual);
    }

    @Test
    public void findPlayerToTeamForPlayerId() throws Exception {
        final Player player = Player.builder().name("name").build();
        playerRepository.save(player);
        final PlayerToTeam expected = PlayerToTeam.builder().playerId(player.getId()).teamId("teamId")
                .fromDate(new Date()).build();
        playerToTeamRepository.save(expected);

        final String contentAsString = mvc.perform(get("/playersToTeams?playerId="
                + player.getId(), "")).andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();
        final List<PlayerToTeam> actual = mapper.readValue(contentAsString, new TypeReference<List<PlayerToTeam>>(){});

        assertTrue(actual.size() == 1);
        assertEquals(expected, actual.get(0));
    }

    @Test
    public void findPlayerToTeamForTeamId() throws Exception {
        final Player player = Player.builder().name("name").build();
        playerRepository.save(player);
        final PlayerToTeam playerToTeam = PlayerToTeam.builder().playerId(player.getId()).teamId("teamId")
                .fromDate(new Date()).build();
        playerToTeamRepository.save(playerToTeam);

        final String contentAsString = mvc.perform(get("/playersToTeams/teams/teamId", ""))
                .andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();
        final List<PlayerToTeam> actual = mapper.readValue(contentAsString, new TypeReference<List<PlayerToTeam>>(){});

        assertTrue(actual.size() == 1);
        assertEquals(playerToTeam, actual.get(0));
    }

    @Test
    public void testSavePlayerToTeamForAnUserNotHisPlayer() throws Exception {
        final PlayerToTeam playerToTeam = PlayerToTeam.builder().playerId(BigInteger.TEN).teamId("teamId").fromDate(new Date()).build();
        mvc.perform(post("/playersToTeams", "")
                .header("Authorization", MessageFormat.format("Bearer {0}", user.getToken()))
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(playerToTeam))).andExpect(status().is(403)).andReturn().getResponse()
                .getContentAsString();
    }

    @Test
    public void testSavePlayerToTeamForAnUserAndHisPlayer() throws Exception {
        final Player player = createContextForUser();

        final PlayerToTeam playerToTeam = PlayerToTeam.builder().playerId(player.getId()).teamId("teamId").fromDate(new Date()).build();
        mvc.perform(post("/playersToTeams", "")
                .header("Authorization", MessageFormat.format("Bearer {0}", user.getToken()))
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(playerToTeam))).andExpect(status().is2xxSuccessful()).andReturn().getResponse()
                .getContentAsString();
    }

    @Test
    public void testUpdatePlayerToTeamEntity() throws Exception {
        final Player player = createContextForUser();

        final Date fromDate = new DateTime().minusYears(1).toDate();
        final PlayerToTeam playerToTeam = PlayerToTeam.builder().playerId(player.getId()).teamId("teamId").fromDate(fromDate).build();
        playerToTeamRepository.save(playerToTeam);

        final PlayerToTeam expected = playerToTeam.toBuilder().fromDate(new Date()).build();
        final String updatedJson = mvc.perform(post("/playersToTeams", "")
                .header("Authorization", MessageFormat.format("Bearer {0}", user.getToken()))
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(expected))).andExpect(status().is2xxSuccessful()).andReturn().getResponse()
                .getContentAsString();
        final PlayerToTeam actual = mapper.readValue(updatedJson, PlayerToTeam.class);
        assertEquals(expected, actual);
    }

    private Player createContextForUser() {
        final Player player = Player.builder().name("Manuel Doncel Martos").nickname("manuel").build();
        playerRepository.save(player);
        final UserData userData = UserData.builder().userId(user.getUser_id()).playerId(player.getId()).build();
        userDataRepository.save(userData);
        return player;
    }

    @SneakyThrows
    private String asJsonString(final Object obj) {
        return mapper.writeValueAsString(obj);
    }

}
