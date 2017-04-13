package org.manuel.teambuilting.players.aspects;

import com.auth0.authentication.result.UserProfile;
import com.auth0.spring.security.api.Auth0JWTToken;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
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

    @AfterReturning(
            pointcut="@annotation(org.manuel.teambuilting.players.aspects.UserDataSave)",
            returning="retVal")
    public void saveEntityToUserData(final JoinPoint call, Player retVal) {
	    if (retVal instanceof Player) {
            final Player player = (Player) call.getArgs()[0];
            final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            final UserProfile user = auth0Client.getUser((Auth0JWTToken) auth);
            final UserData userData = userService.getOrCreateUserData(user.getId());
            userData.setPlayerId(player.getId());
            userService.update(userData);
        }
	}

	@After(value = "@annotation(org.manuel.teambuilting.players.aspects.UserDataDeletePlayer)")
	public void deletePlayerFromUserData(final JoinPoint call) {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final UserProfile user = auth0Client.getUser((Auth0JWTToken) auth);
		final UserData userData = userService.getOrCreateUserData(user.getId());
		userData.setPlayerId(null);
		userService.update(userData);
	}

}
