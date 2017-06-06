package net.halflite.hiq.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class EnvUtilsTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testAllProperties() {
		String key = "hoge";
		String value = "fuga";
		System.setProperty(key, value);

		Map<String, String> allProperties = EnvUtils.allProperties();
		assertThat(allProperties).containsKey(key);
		assertThat(allProperties.get(key)).isEqualTo(value);
	}

	@Test
	public void testConstructor() {
		try {
			Constructor<?>[] constructors = EnvUtils.class.getDeclaredConstructors();

			assertThat(constructors.length).isNotEqualTo(0);

			Constructor<?> defaultConstructor = constructors[0];
			assertThat(defaultConstructor.getParameterTypes().length).isEqualTo(0);
			assertThat(Modifier.isPrivate(defaultConstructor.getModifiers())).isTrue();

			defaultConstructor.setAccessible(true);
			Object instance = defaultConstructor.newInstance();
			assertThat(instance).isNotNull().isInstanceOf(EnvUtils.class);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
