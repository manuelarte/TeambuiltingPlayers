/**
 * 
 */
package org.manuel.teambuilting.players.repositories;

import org.manuel.teambuilting.players.model.entities.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

/**
 * @author Manuel Doncel Martos
 *
 */
@Repository
public interface PlayerRepository extends JpaRepository<Player, BigInteger> {

	Page<Player> findByNameLikeIgnoreCase(Pageable pageable, String name);

}
