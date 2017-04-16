package org.manuel.teambuilting.players.repositories;

import org.manuel.teambuilting.core.repositories.PlayerDependentRepository;
import org.manuel.teambuilting.players.model.entities.PlayerGeocoding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

/**
 * @author manuel.doncel.martos
 * @since 14-3-2017
 */
@Repository
public interface PlayerGeocodingRepository extends PlayerDependentRepository, JpaRepository<PlayerGeocoding, BigInteger> {
    
}
