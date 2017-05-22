package org.manuel.teambuilting.players.aspects;

import com.auth0.Auth0User;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.manuel.teambuilting.core.model.PlayerDependentEntity;
import org.manuel.teambuilting.exceptions.UserNotAllowedToModifyEntityException;
import org.manuel.teambuilting.players.model.entities.Player;
import org.manuel.teambuilting.players.model.entities.UserData;
import org.manuel.teambuilting.players.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author manuel.doncel.martos
 * @since 9-1-2017
 */
@Aspect
@Component
@AllArgsConstructor
public class UserDataAspect {

	private final UserService userService;

	@Before(
		value="@annotation(org.manuel.teambuilting.players.aspects.UserCanCud) && args(playerIdDependentEntity)")
	public void userCanCud(final JoinPoint call, final PlayerDependentEntity playerIdDependentEntity) {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final Auth0User user = (Auth0User) auth.getPrincipal();
		final UserData userData = userService.getOrCreateUserData(user.getUserId());
		if (!Optional.ofNullable(userData.getPlayerId()).isPresent() ||
			!userData.getPlayerId().equals(playerIdDependentEntity.getPlayerId())) {
			throw new UserNotAllowedToModifyEntityException();
		}
	}

	@AfterReturning("@annotation(org.manuel.teambuilting.players.aspects.UserDataDeletePlayer) && args(player)")
	public void deletePlayerFromUserData(final JoinPoint call, final Player player) throws Throwable {
    	final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final Auth0User user = (Auth0User) auth.getPrincipal();
		final UserData userData = userService.getOrCreateUserData(user.getUserId());
		if (userData.getPlayerId() != null && userData.getPlayerId().equals(player.getId())) {
			userData.setPlayerId(null);
			userService.update(userData);
		} else {
			throw new UserNotAllowedToModifyEntityException();
		}
	}

}
