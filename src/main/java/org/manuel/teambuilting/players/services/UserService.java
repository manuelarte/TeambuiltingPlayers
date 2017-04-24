package org.manuel.teambuilting.players.services;

import com.auth0.authentication.result.UserProfile;
import com.auth0.spring.security.api.Auth0JWTToken;
import lombok.AllArgsConstructor;
import org.manuel.teambuilting.players.config.Auth0Client;
import org.manuel.teambuilting.players.model.entities.Player;
import org.manuel.teambuilting.players.model.entities.UserData;
import org.manuel.teambuilting.players.repositories.PlayerRepository;
import org.manuel.teambuilting.players.repositories.UserDataRepository;
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
    private Auth0Client auth0Client;
    private PlayerRepository playerRepository;

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
        final UserProfile user = auth0Client.getUser((Auth0JWTToken) auth);
        final Player player = Player.builder().name(user.getName()).nickname(user.getNickname())
                .imageLink(user.getPictureURL()).build();
        playerRepository.save(player);
        return player;
    }

    public UserData update(final UserData userData) {
        return repository.save(userData);
    }
}
