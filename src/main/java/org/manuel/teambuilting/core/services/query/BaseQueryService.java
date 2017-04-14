package org.manuel.teambuilting.core.services.query;

import java.util.Optional;

/**
 * @author manuel.doncel.martos
 * @since 14-3-2017
 */
public interface BaseQueryService<Entity, ID> {

	/**
	 * Return an optional with the entity whose id matches the parameter id
	 *
	 * @param id the id of the entity desired
	 * @return an optional with the entity whose id matches the input parameter
	 */
	Optional<Entity> findOne(ID id);

}
