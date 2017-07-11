package net.halflite.hiq.inject;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;
import javax.inject.Named;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.google.guiceberry.GuiceBerryModule;
import com.google.guiceberry.junit4.GuiceBerryRule;
import com.google.inject.AbstractModule;

public class EnvModuleTest {

	@Rule
	public final GuiceBerryRule rule = new GuiceBerryRule(EnvModuleEnv.class);

	@Inject
	@Named("TZ")
	private String tz;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testConfigure() {
		assertThat(tz).isEqualTo("Asia/Tokyo");
	}

	public static class EnvModuleEnv extends AbstractModule {
		@Override
		protected void configure() {
			String key = "TZ";
			String value = "Asia/Tokyo";
			System.setProperty(key, value);

			install(new GuiceBerryModule());
			install(new EnvModule());
		}
	}
}
