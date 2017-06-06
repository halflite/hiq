package net.halflite.hiq.inject;

import static com.google.inject.name.Names.bindProperties;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.ImmutableSet;
import com.google.inject.AbstractModule;

import jersey.repackaged.com.google.common.base.Predicates;
import jersey.repackaged.com.google.common.collect.Maps;
import net.halflite.hiq.util.EnvUtils;

/**
 * 環境変数の値をDI設定するクラス
 *
 * @author halflite
 *
 */
public class EnvModule extends AbstractModule {

	/** DIする要素のキー */
	private final static Collection<String> BIND_KEYS = ImmutableSet.<String> of("TZ",
			"DATABASE_URL",
			"JDBC_DATABASE_USERNAME",
			"JDBC_DATABASE_PASSWORD");

	@Override
	protected void configure() {
		Map<String, String> allProperties = EnvUtils.allProperties();
		Map<String, String> properties = Maps.filterKeys(allProperties, Predicates.in(BIND_KEYS));
		bindProperties(binder(), properties);
	}

}
