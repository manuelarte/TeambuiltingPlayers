package org.manuel.teambuilting.core.services.command;

/**
 * @author manuel.doncel.martos
 * @since 14-3-2017
 */
public interface BaseCommandService<Entity, ID> {

	/**
	 * Persist the input parameter entity
	 *
	 * @param entity to be saved
	 * @return the entity saved
	 */
	Entity save(Entity entity);

	/**
	 * Delete the entity whose id matches the parameter id
	 *
	 * @param id the id of the entity desired to be deleted
	 */
	void delete(ID id);

}
