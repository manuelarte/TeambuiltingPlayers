package org.manuel.teambuilting.players.aspects;

import com.auth0.authentication.result.UserProfile;
import com.auth0.spring.security.api.Auth0JWTToken;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.manuel.teambuilting.exceptions.UserNotAllowedToModifyEntityException;
import org.manuel.teambuilting.players.config.Auth0Client;
import org.manuel.teambuilting.players.model.entities.Player;
import org.manuel.teambuilting.players.model.entities.UserData;
import org.manuel.teambuilting.players.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

/**
 * @author manuel.doncel.martos
 * @since 9-1-2017
 */
@Aspect
@Component
@AllArgsConstructor
public class UserDataAspect {

	private final UserService userService;
	private final Auth0Client auth0Client;

	@AfterReturning("@annotation(org.manuel.teambuilting.players.aspects.UserDataDeletePlayer) && args(player)")
	public void deletePlayerFromUserData(final JoinPoint call, final Player player) throws Throwable {
    	final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final UserProfile user = auth0Client.getUser((Auth0JWTToken) auth);
		final UserData userData = userService.getOrCreateUserData(user.getId());
		if (userData.getPlayerId() != null && userData.getPlayerId().equals(player.getId())) {
			userData.setPlayerId(null);
			userService.update(userData);
		} else {
			throw new UserNotAllowedToModifyEntityException();
		}
	}

}
