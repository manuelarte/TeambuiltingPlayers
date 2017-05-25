package org.manuel.teambuilting.players.aspects;

import com.auth0.authentication.result.UserProfile;
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
import org.manuel.teambuilting.players.util.Util;
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
	private final Util util;

	@Before(
		value="@annotation(org.manuel.teambuilting.players.aspects.UserCanCud) && args(playerIdDependentEntity)")
	public void userCanCud(final JoinPoint call, final PlayerDependentEntity playerIdDependentEntity) {
		final UserProfile user = util.getUserProfile().get();
		final UserData userData = userService.getOrCreateUserData(user.getId());
		if (!Optional.ofNullable(userData.getPlayerId()).isPresent() ||
			!userData.getPlayerId().equals(playerIdDependentEntity.getPlayerId())) {
			throw new UserNotAllowedToModifyEntityException();
		}
	}

	@AfterReturning("@annotation(org.manuel.teambuilting.players.aspects.UserDataDeletePlayer) && args(player)")
	public void deletePlayerFromUserData(final JoinPoint call, final Player player) throws Throwable {
    	final UserProfile user = util.getUserProfile().get();
		final UserData userData = userService.getOrCreateUserData(user.getId());
		if (userData.getPlayerId() != null && userData.getPlayerId().equals(player.getId())) {
			userData.setPlayerId(null);
			userService.update(userData);
		} else {
			throw new UserNotAllowedToModifyEntityException();
		}
	}

}
