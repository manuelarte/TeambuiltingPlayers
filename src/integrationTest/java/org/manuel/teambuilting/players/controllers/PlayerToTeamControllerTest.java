package org.manuel.teambuilting.players.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.manuel.teambuilting.players.model.entities.Player;
import org.manuel.teambuilting.players.model.entities.PlayerToTeam;
import org.manuel.teambuilting.players.repositories.PlayerRepository;
import org.manuel.teambuilting.players.repositories.PlayerToTeamRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Collection;
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
@SpringBootTest
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PlayerToTeamControllerTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Inject
    private WebApplicationContext context;

    @Inject
    private PlayerRepository playerRepository;

    @Inject
    private PlayerToTeamRepository playerToTeamRepository;

    private MockMvc mvc;

    @BeforeClass
    public static void beforeClass() {
        setSecurityContext();
    }

    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void findPlayerToTeamById() throws Exception {
        final Player player = Player.builder().name("name").build();
        playerRepository.save(player);
        final PlayerToTeam expected = PlayerToTeam.builder().playerId(player.getId()).teamId("teamId").fromDate(new Date()).build();
        playerToTeamRepository.save(expected);

        final String contentAsString = mvc.perform(get("/playersToTeams/" + expected.getId(), "")).andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();
        final PlayerToTeam actual = mapper.readValue(contentAsString, PlayerToTeam.class);

        assertEquals(expected, actual);
    }

    @Test
    public void findPlayerToTeamForPlayerId() throws Exception {
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
        final List<PlayerToTeam> actual = mapper.readValue(contentAsString, new TypeReference<List<PlayerToTeam>>(){});

        assertTrue(actual.size() == 1);
        assertEquals(playerToTeam, actual.get(0));
    }

    @Test
    public void testSavePlayerToTeam() throws Exception {
        final Player player = Player.builder().name("name").build();
        playerRepository.save(player);
        final PlayerToTeam playerToTeam = PlayerToTeam.builder().playerId(player.getId()).teamId("teamId").fromDate(new Date()).build();
        final String contentAsString = mvc.perform(post("/playersToTeams", "").contentType(MediaType.APPLICATION_JSON).content(asJsonString(playerToTeam))).andExpect(status().isOk()).andReturn().getResponse()
                .getContentAsString();
    }

    @SneakyThrows
    private static String asJsonString(final Object obj) {
        mapper.findAndRegisterModules();
        return mapper.writeValueAsString(obj);
    }

    private static void setSecurityContext() {
        final SecurityContext securityContext = new SecurityContext() {
            @Override
            public Authentication getAuthentication() {
                return new Authentication() {
                    @Override
                    public Collection<? extends GrantedAuthority> getAuthorities() {
                        return Arrays.asList(new SimpleGrantedAuthority("user"), new SimpleGrantedAuthority("admin"));
                    }

                    @Override
                    public Object getCredentials() {
                        return null;
                    }

                    @Override
                    public Object getDetails() {
                        return null;
                    }

                    @Override
                    public Object getPrincipal() {
                        return null;
                    }

                    @Override
                    public boolean isAuthenticated() {
                        return true;
                    }

                    @Override
                    public void setAuthenticated(final boolean isAuthenticated) throws IllegalArgumentException {

                    }

                    @Override
                    public String getName() {
                        return null;
                    }
                };
            }

            @Override
            public void setAuthentication(final Authentication authentication) {
            }
        };
        SecurityContextHolder.setContext(securityContext);
    }

}
