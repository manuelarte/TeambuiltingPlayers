/**
 * 
 */
package org.manuel.teambuilting.players.model.entities;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.hibernate.envers.Audited;
import org.manuel.teambuilting.players.validations.PlayerExists;
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
@Immutable
@Getter
@lombok.Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Audited
public class PlayerToTeamSportDetails {

	@Id
	@GeneratedValue
	private BigInteger id;
	
	@NotNull
	@PlayerExists
	private BigInteger playerId;
	
	@NotNull
	private String sport;
	
	private String bio;
	
	@NotNull
	private String mainPosition;

	@PersistenceConstructor
	public PlayerToTeamSportDetails(final BigInteger playerId, final String sport, final String bio, final String mainPosition) {
		this.playerId = playerId;
		this.sport = sport;
		this.bio = bio; 
		this.mainPosition = mainPosition;
	}

}