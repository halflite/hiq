package net.halflite.hiq.repository;

import static net.halflite.hiq.entity.QAccount.account;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.persistence.EntityManager;

import com.querydsl.jpa.impl.JPAQueryFactory;

import net.halflite.hiq.entity.Account;
import net.halflite.hiq.helper.DateHelper;

/**
 * アカウント情報のリポジトリクラス
 *
 * @author halflite
 *
 */
@Singleton
public class AccountRepository extends AbstractUpdatableRepository<Account> {

	/**
	 * principalをキーにアカウント情報を取得します
	 *
	 * @param principal
	 * @return アカウント情報
	 */
	public Account findByPrincipal(String principal) {
		return this.jpaQueryFactory
				.selectFrom(account)
				.where(account.principal.eq(principal))
				.fetchOne();
	}

	@Inject
	public AccountRepository(Provider<EntityManager> emProvider,
			JPAQueryFactory jpaQueryFactory,
			DateHelper dateHelper) {
		super(emProvider, jpaQueryFactory, dateHelper);
	}
}
