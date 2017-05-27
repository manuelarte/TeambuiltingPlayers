package org.manuel.teambuilting.players.validations;

import org.manuel.teambuilting.players.model.entities.Player;
import org.manuel.teambuilting.players.repositories.PlayerRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigInteger;
import java.util.Optional;

/**
 * @author Manuel on 12/12/2016.
 */
@Component
public class PlayerExistsValidator implements ConstraintValidator<PlayerExists, BigInteger> {

    private final PlayerRepository playerRepository;

    @Inject
    public PlayerExistsValidator(final PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public void initialize(final PlayerExists constraintAnnotation) {

    }

    @Override
    public boolean isValid(final BigInteger playerId, final ConstraintValidatorContext context) {
        Assert.notNull(playerId, "Player id cannot be null");
        Assert.notNull(context, "Context cannot be null");
        final Optional<Player> retrieved = Optional.ofNullable(playerRepository.findOne(playerId));
        return retrieved.isPresent();
    }
}
