package org.manuel.teambuilting.players.services;

import com.auth0.Auth0User;
import lombok.AllArgsConstructor;
import org.manuel.teambuilting.players.model.entities.Player;
import org.manuel.teambuilting.players.model.entities.UserData;
import org.manuel.teambuilting.players.repositories.UserDataRepository;
import org.manuel.teambuilting.players.services.command.PlayerCommandService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @author Manuel on 11/12/2016.
 */
@Service
@AllArgsConstructor
public class UserService {

    private final UserDataRepository repository;
    private PlayerCommandService playerCommandService;

    public UserData getOrCreateUserData(final String userId) {
        Assert.hasLength(userId);
        final Optional<UserData> retrieved = getUserData(userId);
        return retrieved.isPresent() ? retrieved.get() : createUserData(userId);
    }

    private Optional<UserData> getUserData(final String userId) {
        return Optional.ofNullable(repository.findOne(userId));
    }

    @Transactional
    private UserData createUserData(final String userId) {
        final Player player = createPlayer();
        final UserData userData = new UserData(userId, player.getId());
        return repository.save(userData);
    }

    private Player createPlayer() {
        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        final Auth0User user = (Auth0User)auth.getPrincipal();
        final Player player = Player.builder().name(user.getName()).nickname(user.getNickname())
                .imageLink(user.getPicture()).build();
        playerCommandService.save(player);
        return player;
    }

    public UserData update(final UserData userData) {
        return repository.save(userData);
    }
}
