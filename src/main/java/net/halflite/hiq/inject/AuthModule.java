package net.halflite.hiq.inject;

import javax.inject.Named;
import javax.inject.Singleton;

import org.pac4j.core.config.Config;
import org.pac4j.oauth.client.TwitterClient;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import net.halflite.hiq.config.AuthConfigContextResolver;

/**
 * 認証系の設定をDIするクラス
 *
 * @author halflite
 *
 */
public class AuthModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(AuthConfigContextResolver.class);
	}

	/**
	 * Pac4Jの設定オブジェクトを作って返します
	 *
	 * @param callback
	 *            認証コールバック用URI
	 * @param twitterClient
	 *            Twitter用認証クライアントオブジェクト
	 * @return Pac4Jの設定オブジェクト
	 */
	@Provides
	@Singleton
	protected Config providePac4jConfig(@Named("AUTH_CALLBACK_URL") String callback,
			TwitterClient twitterClient) {
		return new Config(callback, twitterClient);
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
