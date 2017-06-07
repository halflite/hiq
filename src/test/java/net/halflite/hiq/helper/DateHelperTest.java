package net.halflite.hiq.helper;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.google.guiceberry.GuiceBerryModule;
import com.google.guiceberry.junit4.GuiceBerryRule;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public class DateHelperTest {

	@Rule
	public final GuiceBerryRule rule = new GuiceBerryRule(DateHelperEnv.class);

	@Inject
	private DateHelper dateHelper;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testNow() {
		Instant now = this.dateHelper.now();
		assertThat(now).isNotNull();
	}

	@Test
	public void testMidnight() {
		Instant midnight = this.dateHelper.midnight();
		assertThat(midnight).isNotNull();
	}

	public static class DateHelperEnv extends AbstractModule {
		@Override
		protected void configure() {
			install(new GuiceBerryModule());
			bind(DateHelper.class);
			bindConstant().annotatedWith(Names.named("TZ")).to("Asia/Tokyo");
		}
	}

}
