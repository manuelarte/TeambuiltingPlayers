/**
 * 
 */
package org.manuel.teambuilting.players.model.entities;

import lombok.*;
import org.hibernate.envers.Audited;
import org.manuel.teambuilting.core.model.PlayerDependentEntity;
import org.manuel.teambuilting.players.model.TimeSlice;
import org.springframework.data.annotation.PersistenceConstructor;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.Date;

/**
 * @author Manuel Doncel Martos
 *
 */
@Data
@lombok.Builder(toBuilder = true)
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Audited
public class PlayerToTeam implements PlayerDependentEntity, TimeSlice {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private BigInteger id;

	@Version
	public Long lockVersion;

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
