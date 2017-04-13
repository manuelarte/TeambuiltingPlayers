package org.manuel.teambuilting.players.repositories;

import org.manuel.teambuilting.players.model.entities.PlayerToTeamSportDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface PlayerToTeamSportDetailsRepository extends PlayerDependentRepository<PlayerToTeamSportDetails, BigInteger>, JpaRepository<PlayerToTeamSportDetails, BigInteger> {

	PlayerToTeamSportDetails findByPlayerIdAndSportIgnoringCase(BigInteger playerId, String sport);

}
