package net.halflite.hiq.inject;

import javax.inject.Named;
import javax.inject.Singleton;

import org.pac4j.core.config.Config;
import org.pac4j.jax.rs.grizzly.pac4j.GrizzlySessionStore;
import org.pac4j.jax.rs.jersey.features.Pac4JValueFactoryProvider;
import org.pac4j.oauth.client.TwitterClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import net.halflite.hiq.config.AuthConfigContextResolver;

/**
 * 認証系の設定をDI設定するクラス
 *
 * @author halflite
 *
 */
public class AuthModule extends AbstractModule {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthModule.class);

	@Override
	protected void configure() {
		bind(AuthConfigContextResolver.class);
		bind(Pac4JValueFactoryProvider.Binder.class).in(Singleton.class);
	}

	/**
	 * Pac4Jの設定オブジェクトを作って返します
	 *
	 * @param callback
	 *            認証コールバック用URI
	 * @param twitterClient
	 *            Twitter用認証クライアントオブジェクト
	 * @param grizzlySessionStore
	 *            Grizzly用セッションストア
	 * @return
	 */
	@Provides
	@Singleton
	protected Config providePac4jConfig(@Named("AUTH_CALLBACK_URL") String callback,
			TwitterClient twitterClient,
			GrizzlySessionStore grizzlySessionStore) {
		Config config = new Config(callback, twitterClient);
		return config;
	}

	/**
	 * Twitter用の認証クライアントを作って返します
	 *
	 * @param twKey
	 *            APIキー
	 * @param twSecret
	 *            API秘密鍵
	 * @return Twitter用の認証クライアントオブジェクト
	 */
	@Provides
	@Singleton
	protected TwitterClient provideTwitterClient(@Named("TWITTER_API_KEY") String twKey,
			@Named("TWITTER_API_SECRET") String twSecret) {
		return new TwitterClient(twKey, twSecret);
	}
}
