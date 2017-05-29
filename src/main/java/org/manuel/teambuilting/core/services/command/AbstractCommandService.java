package org.manuel.teambuilting.core.services.command;

import java.io.Serializable;

import org.manuel.teambuilting.players.aspects.UserCanCud;
import org.manuel.teambuilting.players.aspects.UserDataDeletePlayer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;

import lombok.AllArgsConstructor;

/**
 * @author manuel.doncel.martos
 * @since 4-4-2017
 */
@AllArgsConstructor
public class AbstractCommandService<Entity, ID extends Serializable, Repository extends CrudRepository<Entity, ID>> implements BaseCommandService<Entity, ID> {

	protected final Repository repository;

	@Override
	@PreAuthorize("hasAuthority('user') or hasAuthority('admin')")
	@UserCanCud
	public Entity save(final Entity entity) {
		Assert.notNull(entity, "The entity cannot be null");
		beforeSave(entity);
		final Entity savedEntity = repository.save(entity);
		afterSaved(savedEntity);
		return savedEntity;
	}

	@Override
	@UserDataDeletePlayer
	@PreAuthorize("hasAuthority('user') or hasAuthority('admin')")
	public void delete(final ID id) {
		Assert.notNull(id, "The id cannot be null");
		beforeDelete(id);
		repository.delete(id);
		afterDeleted(id);
	}

	protected void beforeSave(final Entity entity) {}

	protected void afterSaved(final Entity savedEntity) {}

	protected void beforeDelete(final ID id) {}

	protected void afterDeleted(final ID id) {}
}
