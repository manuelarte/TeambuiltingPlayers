package org.manuel.teambuilting.players.services;

import org.manuel.teambuilting.players.model.entities.UserData;
import org.manuel.teambuilting.players.repositories.UserDataRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.inject.Inject;
import java.math.BigInteger;
import java.util.Optional;

/**
 * @author Manuel on 11/12/2016.
 */
@Service
public class UserService {

    private final UserDataRepository repository;

    @Inject
    public UserService(final UserDataRepository repository) {
        this.repository = repository;
    }

    public UserData getOrCreateUserData(final String userId) {
        Assert.hasLength(userId);
        final Optional<UserData> retrieved = getUserData(userId);
        return retrieved.isPresent() ? retrieved.get() : createUserData(userId, null);
    }

    private Optional<UserData> getUserData(final String userId) {
        return Optional.ofNullable(repository.findOne(userId));
    }

    private UserData createUserData(final String userId, final BigInteger playerId) {
        final UserData userData = new UserData(userId, playerId);
        return repository.save(userData);
    }

    public UserData update(final UserData userData) {
        return repository.save(userData);
    }
}
