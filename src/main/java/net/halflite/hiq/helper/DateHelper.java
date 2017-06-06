package net.halflite.hiq.helper;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 * 日時のヘルパークラス
 *
 * @author halflite
 *
 */
@Singleton
public class DateHelper {

	/** タイムゾーン */
	private final ZoneId zone;

	/**
	 * 現在時間を返します。
	 *
	 * @return
	 */
	public Instant now() {
		return ZonedDateTime.now(zone).toInstant();
	}

	/**
	 * 本日0時を返します。
	 *
	 * @return
	 */
	public Instant midnight() {
		return ZonedDateTime.now(zone).withHour(0).withMinute(0).withSecond(0).withNano(0).toInstant();
	}

	public DateHelper(@Named("TZ") String tz) {
		this.zone = ZoneId.of(tz);
	}
}
