/**
 * 
 */
package org.manuel.teambuilting.players.model.entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.hibernate.envers.Audited;
import org.manuel.teambuilting.core.model.PlayerDependentEntity;
import org.manuel.teambuilting.players.model.TimeSlice;
import org.springframework.data.annotation.PersistenceConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author Manuel Doncel Martos
 *
 */
@Immutable
@Data
@lombok.Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Audited
public class PlayerToTeam implements PlayerDependentEntity, TimeSlice {

	@Id
	@GeneratedValue
	private BigInteger id;

	@NotNull
	private BigInteger playerId;

	@NotNull
	private String teamId;

	@NotNull
	private Date fromDate;

	private Date toDate;
	
	@AssertTrue
	private boolean startDateBeforeEndDate() {
		return toDate == null || toDate.getTime() > fromDate.getTime();
	}

	@PersistenceConstructor
	public PlayerToTeam(final BigInteger playerId, final String teamId, final Date fromDate, final Date toDate) {
		this.playerId = playerId;
		this.teamId = teamId;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}
	
}
