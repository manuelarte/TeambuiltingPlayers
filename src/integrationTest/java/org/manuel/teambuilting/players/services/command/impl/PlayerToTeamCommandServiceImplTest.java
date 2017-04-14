package org.manuel.teambuilting.players.services.command.impl;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.manuel.teambuilting.exceptions.ValidationRuntimeException;
import org.manuel.teambuilting.players.model.entities.Player;
import org.manuel.teambuilting.players.model.entities.PlayerToTeam;
import org.manuel.teambuilting.players.repositories.PlayerRepository;
import org.manuel.teambuilting.players.repositories.PlayerToTeamRepository;
import org.manuel.teambuilting.players.services.command.PlayerToTeamCommandService;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

/**
 * Test Suit to check that it is not possible to store wrong player history
 *
 * @author manuel.doncel.martos
 * @since 13-1-2017
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
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
		final Player player = playerRepository.save(new Player("name", "nickname",
			'M', "address", "imageLink"));
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
		final Player player = playerRepository.save(new Player("name", "nickname",
			'M', "address", "imageLink"));
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
		final Player player = playerRepository.save(new Player("name", "nickname",
				'M', "address", "imageLink"));
		final String teamId = "teamId";
		final Date teamToDate = new Date();
		final Date teamFromDate = changeDate(teamToDate, -2, Calendar.YEAR);

		final Date playerToTeamFromDate = teamFromDate;
		final Date playerToTeamToDate = teamToDate;
		final PlayerToTeam saved = playerToTeamRepository.save(new PlayerToTeam(player.getId(), teamId, playerToTeamFromDate, playerToTeamToDate));
		playerToTeamService.save(saved);
	}

	@Test
	public void testUpdateEntityChangingFromDate() {
		final Player player = playerRepository.save(new Player("name", "nickname",
				'M', "address", "imageLink"));
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
		final Player player = playerRepository.save(new Player("name", "nickname",
			'M', "address", "imageLink"));
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
