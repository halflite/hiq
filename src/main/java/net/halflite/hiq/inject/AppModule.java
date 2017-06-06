package net.halflite.hiq.inject;

import com.google.inject.AbstractModule;

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
	}
}
