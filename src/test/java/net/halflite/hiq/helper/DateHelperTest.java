package net.halflite.hiq.helper;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;

import org.junit.Before;
import org.junit.Test;

public class DateHelperTest {

	private DateHelper dateHelper;

	@Before
	public void setUp() throws Exception {
		String tz = "Asia/Tokyo";
		this.dateHelper = new DateHelper(tz);
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
}
