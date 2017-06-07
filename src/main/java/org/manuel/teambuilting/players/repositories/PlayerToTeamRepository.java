/**
 * 
 */
package org.manuel.teambuilting.players.repositories;

import org.manuel.teambuilting.core.repositories.PlayerDependentRepository;
import org.manuel.teambuilting.players.model.entities.PlayerToTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;

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
	 * @param teamId
	 * @param date
	 * @return
	 */
	@Query("select p from PlayerToTeam p where p.teamId = :teamId and (p.toDate is null or p.toDate > :date)")
	Collection<PlayerToTeam> findByTeamIdAndToDateAfterOrToDateIsNull(@Param("teamId") String teamId, @Param("date") Date date);

	Collection<PlayerToTeam> findByPlayerIdAndTeamId(BigInteger playerId, String teamId);

}
