package net.halflite.hiq.config;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.pac4j.core.config.Config;

/**
 * Pac4J Config ContextResolver
 *
 * @author halflite
 *
 */
@Provider
@Singleton
public class AuthConfigContextResolver implements ContextResolver<Config> {

	/** Pac4J Config */
	private final Config config;

	@Override
	public Config getContext(Class<?> type) {
		return this.config;
	}

	@Inject
	public AuthConfigContextResolver(Config config) {
		this.config = config;
	}
}
