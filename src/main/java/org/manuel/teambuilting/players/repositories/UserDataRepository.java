package org.manuel.teambuilting.players.repositories;

import org.manuel.teambuilting.players.model.entities.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Manuel on 11/12/2016.
 */
@Repository
public interface UserDataRepository extends JpaRepository<UserData, String> {

}
