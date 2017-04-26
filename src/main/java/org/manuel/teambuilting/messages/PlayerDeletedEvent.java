package org.manuel.teambuilting.messages;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigInteger;
import java.time.Instant;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Event Message to show that a player was deleted
 *
 * @author Manuel Doncel Martos
 * @since 07/12/2016.
 */
@Data
@AllArgsConstructor
@Builder
public class PlayerDeletedEvent {

    public static final String ROUTING_KEY = "player.cud.deleted";

    @NotNull
    private final BigInteger playerId;

    @NotNull
    private final String userId;

    @NotNull
    private final Instant date;

    @JsonIgnore
    public String getRoutingKey() {
        return ROUTING_KEY;
    }
}
