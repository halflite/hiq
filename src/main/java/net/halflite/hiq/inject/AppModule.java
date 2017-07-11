package net.halflite.hiq.inject;

import com.google.inject.AbstractModule;

import net.halflite.hiq.logic.AuthenticateLogic;

/**
 * アプリケーションのDI設定のクラス
 *
 * @author halflite
 *
 */
public class AppModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new EnvModule());
		install(new AuthModule());
		install(new PersistenceModule());

		// ロジッククラスのDI 多くなってきたら、別モジュールに切り出す
		bind(AuthenticateLogic.class);
	}
}
