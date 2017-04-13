package org.manuel.teambuilting.players.repositories;

import org.manuel.teambuilting.players.model.entities.PlayerGeocoding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

/**
 * @author manuel.doncel.martos
 * @since 14-3-2017
 */
@Repository
public interface PlayerGeocodingRepository extends JpaRepository<PlayerGeocoding, BigInteger> {

    List<PlayerGeocoding> findByPlayerId(BigInteger playerId);

}
