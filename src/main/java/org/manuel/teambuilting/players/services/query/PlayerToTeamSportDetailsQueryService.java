package org.manuel.teambuilting.players.services.query;

import org.manuel.teambuilting.players.model.entities.PlayerToTeamSportDetails;

import java.math.BigInteger;
import java.util.Optional;

/**
 * @author Manuel Doncel Martos
 *
 */
public interface PlayerToTeamSportDetailsQueryService extends BaseQueryService<PlayerToTeamSportDetails, BigInteger>, PlayerDependentQueryService<PlayerToTeamSportDetails, String> {

	Optional<PlayerToTeamSportDetails> findPlayerDetailsForSport(BigInteger playerId, String sport);

}
