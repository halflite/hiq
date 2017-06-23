package net.halflite.hiq.inject;

import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.persistence.EntityManager;

import com.google.common.collect.ImmutableMap;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.querydsl.jpa.EclipseLinkTemplates;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jersey.repackaged.com.google.common.collect.Maps;
import net.halflite.hiq.util.EnvUtils;

/**
 * 永続層アクセスの設定をDIするクラス
 *
 * @author halflite
 *
 */
public class PersistenceModule extends AbstractModule {

	/** JPAのDBユニット名 */
	private static final String UNIT = "dbunit";

	/** DB用環境変数とJPAプロパティとの関連連想配列 */
	private static final Map<String, String> PROPS_KEYS = ImmutableMap.<String, String> builder()
			.put("DATABASE_URL", "javax.persistence.jdbc.url")
			.put("JDBC_DATABASE_USERNAME", "javax.persistence.jdbc.user")
			.put("JDBC_DATABASE_PASSWORD", "javax.persistence.jdbc.password")
			.build();

	@Override
	protected void configure() {
		install(new JpaPersistModule(UNIT).properties(dbProperties()));
		bind(JPQLTemplates.class).toInstance(EclipseLinkTemplates.DEFAULT);
		bind(PersistInitializer.class);
	}

	/**
	 * 環境変数からDB用のプロパティ用連想配列を作って返します
	 *
	 * @return DB用のプロパティ用連想配列
	 */
	private Map<String, String> dbProperties() {
		Map<String, String> props = EnvUtils.properties(PROPS_KEYS.keySet());
		return props.entrySet()
				.stream()
				.map(e -> Maps.immutableEntry(PROPS_KEYS.get(e.getKey()), e.getValue()))
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue));
	}

	@Provides
	@Singleton
	protected JPAQueryFactory provideJPAQueryFactory(JPQLTemplates jpqlTemplates, Provider<EntityManager> emProvider) {
		return new JPAQueryFactory(jpqlTemplates, emProvider);
	}

	@Singleton
	public static class PersistInitializer {
		@Inject
		public PersistInitializer(PersistService service) {
			service.start();
		}
	}
}