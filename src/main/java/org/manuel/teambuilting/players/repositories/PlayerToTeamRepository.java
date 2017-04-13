/**
 * 
 */
package org.manuel.teambuilting.players.repositories;

import org.manuel.teambuilting.players.model.entities.PlayerToTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Collection;

/**
 * @author Manuel Doncel Martos
 *
 */
@Repository
public interface PlayerToTeamRepository extends PlayerDependentRepository<PlayerToTeam, BigInteger>, JpaRepository<PlayerToTeam, BigInteger> {

	/**
	 * Look for all the players that have played in the team whose id is teamId
	 * and they stopped played after the parameter end date
	 * 
	 * @param date
	 * @param teamId
	 * @return
	 */
	Collection<PlayerToTeam> findByToDateAfterOrToDateIsNullAndTeamId(LocalDate date, String teamId);

	Collection<PlayerToTeam> findByPlayerIdAndTeamId(BigInteger playerId, String teamId);

}
