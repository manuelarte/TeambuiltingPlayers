package org.manuel.teambuilting.players.model.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import org.hibernate.envers.Audited;
import org.manuel.teambuilting.core.model.PlayerDependentEntity;
import org.springframework.data.annotation.PersistenceConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigInteger;

/**
 * @author Manuel on 11/12/2016.
 */
@Immutable
@Builder(toBuilder = true)
@Entity
@Audited
@Getter
@Setter
@NoArgsConstructor
public class UserData implements PlayerDependentEntity {

    @Id
    private String userId;

    private BigInteger playerId;

    @PersistenceConstructor
    public UserData(final String userId, final BigInteger playerId) {
        this.userId = userId;
        this.playerId = playerId;
    }

}
