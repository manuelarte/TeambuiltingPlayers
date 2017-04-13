package org.manuel.teambuilting.players.model.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigInteger;

/**
 * @author Manuel on 11/12/2016.
 */
@Component
@Builder(toBuilder = true)
@Entity
@Audited
@Getter
@Setter
@NoArgsConstructor
public class UserData {

    @Id
    private String userId;

    private BigInteger playerId;

    @PersistenceConstructor
    public UserData(final String userId, final BigInteger playerId) {
        this.userId = userId;
        this.playerId = playerId;
    }

}
