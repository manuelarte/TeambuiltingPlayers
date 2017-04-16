package org.manuel.teambuilting.players.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.hibernate.envers.Audited;
import org.manuel.teambuilting.core.model.PlayerDependentEntity;
import org.springframework.data.annotation.PersistenceConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

/**
 * @author Manuel Doncel Martos
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize
@Immutable
@Data
@NoArgsConstructor
@Entity
@Audited
public class PlayerGeocoding implements PlayerDependentEntity {

    @Id
    @GeneratedValue
    protected BigInteger id;

    @NotNull
    protected BigInteger playerId;

    @NotNull
    protected double lat;

    @NotNull
    protected double lng;

    @PersistenceConstructor
    @lombok.Builder
	public PlayerGeocoding(final BigInteger playerId, final double lat, final double lng) {
		this.playerId = playerId;
		this.lat = lat;
		this.lng = lng;
	}

}