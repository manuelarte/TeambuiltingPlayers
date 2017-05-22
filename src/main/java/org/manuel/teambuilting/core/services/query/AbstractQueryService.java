package org.manuel.teambuilting.core.services.query;

import org.springframework.data.repository.CrudRepository;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author manuel.doncel.martos
 * @since 12-3-2017
 */
public abstract class AbstractQueryService<Entity, ID extends Serializable, Repository extends CrudRepository<Entity, ID>> implements
	BaseQueryService<Entity, ID> {

	protected final Repository repository;

	public AbstractQueryService(final Repository repository) {
		this.repository = repository;
	}

	@Override
	public Optional<Entity> findOne(final ID id) {
		Assert.notNull(id, "Id cannot be null");
		final Optional<Entity> retrieved = Optional.ofNullable(repository.findOne(id));
		postFindOne(retrieved);
		return retrieved;
	}


	protected void postFindOne(final Optional<Entity> entity) {}

}
