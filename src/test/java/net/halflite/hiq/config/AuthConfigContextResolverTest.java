package net.halflite.hiq.config;

import static com.google.inject.name.Names.named;
import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.pac4j.core.config.Config;

import com.google.guiceberry.GuiceBerryModule;
import com.google.guiceberry.junit4.GuiceBerryRule;
import com.google.inject.AbstractModule;

import net.halflite.hiq.inject.AuthModule;

public class AuthConfigContextResolverTest {

	@Rule
	public final GuiceBerryRule rule = new GuiceBerryRule(AuthConfigContextResolverEnv.class);

	@Inject
	private AuthConfigContextResolver authConfigContextResolver;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		assertThat(authConfigContextResolver).isNotNull();
	}

	@Test
	public void testGetContext() {
		// 引数に関係なくDIされたオブジェクトを返すことを検証するため、null代入
		Class<?> type = null;
		Config config = authConfigContextResolver.getContext(type);

		assertThat(config).isNotNull();
	}

	public static class AuthConfigContextResolverEnv extends AbstractModule {
		@Override
		protected void configure() {
			install(new GuiceBerryModule());
			install(new AuthModule());
			bindConstant().annotatedWith(named("TWITTER_API_KEY")).to("test.twitter.api.key");
			bindConstant().annotatedWith(named("TWITTER_API_SECRET")).to("test.twitter.api.secret");
			bindConstant().annotatedWith(named("AUTH_CALLBACK_URL")).to("http://example.com/auth/callback");
		}
	}
}
