package net.halflite.hiq.jpa;

import java.sql.Timestamp;
import java.time.Instant;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Instant <=> Timestamp 相互変換定義クラス
 *
 * @author halflite
 *
 */
@Converter(autoApply = true)
public class InstantTimestampConverter implements AttributeConverter<Instant, Timestamp> {

	@Override
	public Timestamp convertToDatabaseColumn(Instant attribute) {
		return Timestamp.from(attribute);
	}

	@Override
	public Instant convertToEntityAttribute(Timestamp dbData) {
		return dbData.toInstant();
	}
}
