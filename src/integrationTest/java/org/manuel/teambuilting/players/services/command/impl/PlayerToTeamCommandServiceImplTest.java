package org.manuel.teambuilting.players.services.command.impl;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.manuel.teambuilting.core.exceptions.ValidationRuntimeException;
import org.manuel.teambuilting.players.TestUtils;
import org.manuel.teambuilting.players.model.entities.Player;
import org.manuel.teambuilting.players.model.entities.PlayerToTeam;
import org.manuel.teambuilting.players.repositories.PlayerRepository;
import org.manuel.teambuilting.players.repositories.PlayerToTeamRepository;
import org.manuel.teambuilting.players.services.command.PlayerToTeamCommandService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.Calendar;
import java.util.Date;

import static org.manuel.teambuilting.players.TestUtils.manuel;

/**
 * Test Suit to check that it is not possible to store wrong player history
 *
 * @author manuel.doncel.martos
 * @since 13-1-2017
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class PlayerToTeamCommandServiceImplTest {

	@Inject
	private PlayerRepository playerRepository;

	@Inject
	private PlayerToTeamRepository playerToTeamRepository;

	@Inject
	private PlayerToTeamCommandService playerToTeamService;

	@BeforeClass
	public static void beforeClass() {
		setSecurityContext();
	}

	@Test(expected = ValidationRuntimeException.class)
	public void testSaveAnotherEntryForTheSameTeamAndInsideTimeFrame() {
		final Player player = Player.builder().name("name").nickname("nickname").sex('M').bornAddress("address")
				.imageLink("http:\\\\imageLink").build();
		playerRepository.save(player);
		final Date teamToDate = new Date();
		final Date teamFromDate = changeDate(teamToDate, -2, Calendar.YEAR);

		final String teamId = "teamId";
		final Date playerToTeamFromDate = teamFromDate;
		final Date playerToTeamToDate = teamToDate;
		playerToTeamRepository.save(new PlayerToTeam(player.getId(), teamId, playerToTeamFromDate, playerToTeamToDate));
		// That's original Setup, one player playing during all the history of the team, now we try to add another entry
		final Date newPlayerToTeamFromDate = changeDate(playerToTeamFromDate, +1, Calendar.MONTH);
		final Date newPlayerToTeamToDate = changeDate(playerToTeamToDate, -1, Calendar.MONTH);
		final PlayerToTeam notAllowedEntry = new PlayerToTeam(player.getId(), teamId, newPlayerToTeamFromDate, newPlayerToTeamToDate);
		playerToTeamService.save(notAllowedEntry);
	}

	@Test(expected = ValidationRuntimeException.class)
	public void testPreviousToDateIsNullSaveAnotherEntryForTheSameTeamAndInsideTimeFrame() {
        final Player player = Player.builder().name("name").nickname("nickname").sex('M').bornAddress("address")
                .imageLink("http:\\\\imageLink").build();
		playerRepository.save(player);
		final Date teamToDate = null;
		final Date teamFromDate = changeDate(new Date(), -10, Calendar.YEAR);

		final String teamId = "teamId";
		final Date playerToTeamFromDate = changeDate(teamFromDate, 1, Calendar.MONTH);
		final Date playerToTeamToDate = null;
		playerToTeamRepository.save(new PlayerToTeam(player.getId(), teamId, playerToTeamFromDate, playerToTeamToDate));
		// That's original Setup, one player playing during all the history of the team, now we try to add another entry
		final Date newPlayerToTeamFromDate = changeDate(playerToTeamFromDate, +1, Calendar.MONTH);
		final Date newPlayerToTeamToDate = null;
		final PlayerToTeam notAllowedEntry = new PlayerToTeam(player.getId(), teamId, newPlayerToTeamFromDate, newPlayerToTeamToDate);
		playerToTeamService.save(notAllowedEntry);
	}

	@Test
	public void testUpdateEntityWithSameValues() {
        final Player player = Player.builder().name("name").nickname("nickname").sex('M').bornAddress("address")
                .imageLink("http:\\\\imageLink").build();
	    playerRepository.save(player);
		final String teamId = "teamId";
		final Date teamToDate = new Date();
		final Date teamFromDate = changeDate(teamToDate, -2, Calendar.YEAR);

		final Date playerToTeamFromDate = teamFromDate;
		final Date playerToTeamToDate = teamToDate;
		final PlayerToTeam saved = playerToTeamRepository.save(new PlayerToTeam(player.getId(), teamId, playerToTeamFromDate, playerToTeamToDate));
		playerToTeamService.save(saved);
	}

	@Test
	@Ignore("I set the authentication but we need the JwtProvider")
	public void testUpdateEntityChangingFromDate() {
        final Player player = Player.builder().name("name").nickname("nickname").sex('M').bornAddress("address")
                .imageLink("http:\\\\imageLink").build();
		playerRepository.save(player);
		final String teamId = "teamId";
		final Date teamToDate = new Date();
		final Date teamFromDate = changeDate(teamToDate, -2, Calendar.YEAR);

		final Date playerToTeamFromDate = teamFromDate;
		final Date playerToTeamToDate = teamToDate;
		final PlayerToTeam saved = playerToTeamRepository.save(new PlayerToTeam(player.getId(), teamId, playerToTeamFromDate, playerToTeamToDate));
		saved.setFromDate(changeDate(saved.getFromDate(), +2, Calendar.MONTH));
		playerToTeamService.save(saved);
	}

	@Ignore
	@Test(expected = ValidationRuntimeException.class)
	public void testCannotStorePlayerHistoryAfterEndOfTheTeam() {
        final Player player = Player.builder().name("name").nickname("nickname").sex('M').bornAddress("address")
                .imageLink("http:\\\\imageLink").build();
		playerRepository.save(player);
		final String teamId = "teamId";
		final Date teamToDate = new Date();
		final Date teamFromDate = changeDate(teamToDate, -2, Calendar.YEAR);

		final Date playerToTeamToDate = changeDate(teamToDate, +1, Calendar.DAY_OF_MONTH);
		final Date playerToTeamFromDate = teamFromDate;
		final PlayerToTeam playerToTeam = new PlayerToTeam(player.getId(), teamId, playerToTeamFromDate, playerToTeamToDate);
		playerToTeamService.save(playerToTeam);
	}

	private Date changeDate(final Date date, final int number, final int calendarField ) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(calendarField, number);
		return cal.getTime();
	}

	private static void setSecurityContext() {
		SecurityContextHolder.createEmptyContext();
		SecurityContextHolder.getContext().setAuthentication(TestUtils.getAuthenticationFromUser(manuel()));
	}

}
