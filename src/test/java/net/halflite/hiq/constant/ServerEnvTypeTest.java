package net.halflite.hiq.constant;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

public class ServerEnvTypeTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testValue() {
		String port = "9998";
		Map<String, String> properties = ImmutableMap.of("PORT", port);
		String value = ServerEnvType.PORT.value(properties);

		assertThat(value).isEqualTo(port);
	}

	@Test
	public void testValues() {
		ServerEnvType[] values = ServerEnvType.values();

		assertThat(values.length).isEqualTo(2);
	}

	@Test
	public void testValueOf() {
		String arg = "LOCALHOST";
		ServerEnvType type = ServerEnvType.valueOf(arg);

		assertThat(type).isEqualTo(ServerEnvType.LOCALHOST);
	}
}
