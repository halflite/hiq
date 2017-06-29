package net.halflite.hiq.repository;

import java.time.Instant;

import javax.inject.Provider;
import javax.persistence.EntityManager;

import com.querydsl.jpa.impl.JPAQueryFactory;

import net.halflite.hiq.entity.AbstractUpdatableEntity;
import net.halflite.hiq.helper.DateHelper;

public abstract class AbstractUpdatableRepository<T extends AbstractUpdatableEntity> {

	/** EntityManager Provider */
	protected final Provider<EntityManager> emProvider;

	/** QueryDSL JPA Factory */
	protected final JPAQueryFactory jpaQueryFactory;

	/** 日時ヘルパ */
	protected final DateHelper dateHelper;

	/**
	 * 新規エンティティを永続化します
	 *
	 * @param entity
	 */
	public void insert(T entity) {
		Instant now = dateHelper.now();
		entity.created = now;
		entity.modified = now;
		EntityManager em = this.emProvider.get();
		em.persist(entity);
		em.flush();
	}

	public AbstractUpdatableRepository(Provider<EntityManager> emProvider,
			JPAQueryFactory jpaQueryFactory,
			DateHelper dateHelper) {
		this.emProvider = emProvider;
		this.jpaQueryFactory = jpaQueryFactory;
		this.dateHelper = dateHelper;
	}

}
