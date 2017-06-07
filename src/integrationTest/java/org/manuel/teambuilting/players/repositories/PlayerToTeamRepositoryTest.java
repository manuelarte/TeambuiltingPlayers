package org.manuel.teambuilting.players.repositories;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.manuel.teambuilting.players.model.entities.Player;
import org.manuel.teambuilting.players.model.entities.PlayerToTeam;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.inject.Inject;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;

/**
 * @author Manuel Doncel Martos
 * @since 07/06/2017.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class PlayerToTeamRepositoryTest {

    @Inject
    private PlayerToTeamRepository playerToTeamRepository;

    @Inject
    private PlayerRepository playerRepository;

    @After
    public void after() {
        playerToTeamRepository.deleteAll();
        playerRepository.deleteAll();
    }

    @Test
    public void testRetrievePlayerStillPlayingInTeam() {
        final String name = "Manuel Doncel Martos";
        final Player player = Player.builder().name(name).nickname("Manuel D").build();
        playerRepository.save(player);

        final String teamId = "teamId";
        final PlayerToTeam playerToTeam = PlayerToTeam.builder().playerId(player.getId())
                .teamId(teamId).fromDate(new Date()).build();
        playerToTeamRepository.save(playerToTeam);

        final Date date = new GregorianCalendar(1900, Calendar.FEBRUARY, 11).getTime();
        final Collection<PlayerToTeam> byTeamIdAndToDateAfterOrToDateIsNull = playerToTeamRepository.findByTeamIdAndToDateAfterOrToDateIsNull(teamId, date);
        assertEquals(1, byTeamIdAndToDateAfterOrToDateIsNull.size());
        assertEquals(playerToTeam, byTeamIdAndToDateAfterOrToDateIsNull.iterator().next());
    }

    @Test
    public void testRetrievePlayerStillPlayingInTeamDateNull() {
        final String name = "Manuel Doncel Martos";
        final Player player = Player.builder().name(name).nickname("Manuel D").build();
        playerRepository.save(player);

        final String teamId = "teamId";
        final PlayerToTeam playerToTeam = PlayerToTeam.builder().playerId(player.getId())
                .teamId(teamId).fromDate(new Date()).build();
        playerToTeamRepository.save(playerToTeam);

        final Collection<PlayerToTeam> byTeamIdAndToDateAfterOrToDateIsNull = playerToTeamRepository.findByTeamIdAndToDateAfterOrToDateIsNull(teamId, null);
        assertEquals(1, byTeamIdAndToDateAfterOrToDateIsNull.size());
        assertEquals(playerToTeam, byTeamIdAndToDateAfterOrToDateIsNull.iterator().next());
    }

    @Test
    public void testRetrievePlayerStoppedPlayingInTeam() {
        final String name = "Manuel Doncel Martos";
        final Player player = Player.builder().name(name).nickname("Manuel D").build();
        playerRepository.save(player);

        final String teamId = "teamId";
        final Date fromDate = new GregorianCalendar(2014, Calendar.SEPTEMBER, 1).getTime();
        final Date toDate = new GregorianCalendar(2016, Calendar.MAY, 30).getTime();
        final PlayerToTeam playerToTeam = PlayerToTeam.builder().playerId(player.getId())
                .teamId(teamId).fromDate(fromDate).toDate(toDate).build();
        playerToTeamRepository.save(playerToTeam);

        final Date date = new GregorianCalendar(1900, Calendar.FEBRUARY, 11).getTime();
        final Collection<PlayerToTeam> byTeamIdAndToDateAfterOrToDateIsNull = playerToTeamRepository.findByTeamIdAndToDateAfterOrToDateIsNull(teamId, date);
        assertEquals(1, byTeamIdAndToDateAfterOrToDateIsNull.size());
        assertEquals(playerToTeam, byTeamIdAndToDateAfterOrToDateIsNull.iterator().next());
    }

    @Test
    public void testRetrievePlayerStoppedPlayingInTeamForADateAfter() {
        final String name = "Manuel Doncel Martos";
        final Player player = Player.builder().name(name).nickname("Manuel D").build();
        playerRepository.save(player);

        final String teamId = "teamId";
        final Date fromDate = new GregorianCalendar(2014, Calendar.SEPTEMBER, 1).getTime();
        final Date toDate = new GregorianCalendar(2016, Calendar.MAY, 30).getTime();
        final PlayerToTeam playerToTeam = PlayerToTeam.builder().playerId(player.getId())
                .teamId(teamId).fromDate(fromDate).toDate(toDate).build();
        playerToTeamRepository.save(playerToTeam);

        final Date date = new GregorianCalendar(2017, Calendar.JANUARY, 1).getTime();
        final Collection<PlayerToTeam> byTeamIdAndToDateAfterOrToDateIsNull = playerToTeamRepository.findByTeamIdAndToDateAfterOrToDateIsNull(teamId, date);
        assertEquals(0, byTeamIdAndToDateAfterOrToDateIsNull.size());
    }


}
