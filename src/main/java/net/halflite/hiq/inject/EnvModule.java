package net.halflite.hiq.inject;

import static com.google.inject.name.Names.bindProperties;

import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableSet;
import com.google.inject.AbstractModule;

import net.halflite.hiq.util.EnvUtils;

/**
 * 環境変数の値をDI設定するクラス
 *
 * @author halflite
 *
 */
public class EnvModule extends AbstractModule {

	private static final Logger LOGGER = LoggerFactory.getLogger(EnvModule.class);

	/** DIする要素のキー */
	private final static Collection<String> BIND_KEYS = ImmutableSet.<String> of("TZ",
			"AUTH_CALLBACK_URL",
			"TWITTER_API_KEY",
			"TWITTER_API_SECRET");

	@Override
	protected void configure() {
		Map<String, String> properties = EnvUtils.properties(BIND_KEYS);
		LOGGER.debug("env:{}", properties);

		bindProperties(binder(), properties);
	}
}
