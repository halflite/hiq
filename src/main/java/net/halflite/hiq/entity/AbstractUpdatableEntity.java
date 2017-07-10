package net.halflite.hiq.entity;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

import java.io.Serializable;
import java.time.Instant;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.ToStringStyle;
import org.eclipse.persistence.annotations.Customizer;

import net.halflite.hiq.jpa.InstantTimestampConverter;
import net.halflite.hiq.jpa.OrderCustomizer;

/** 更新可能entityの抽象クラス */
@MappedSuperclass
@Customizer(OrderCustomizer.class)
public abstract class AbstractUpdatableEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** ID */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	/** 変更時間 */
	@Column(nullable = false)
	@Convert(converter = InstantTimestampConverter.class)
	public Instant modified;

	/** 作成時間 */
	@Column(updatable = false, nullable = false)
	@Convert(converter = InstantTimestampConverter.class)
	public Instant created;

	/**
	 * 更新可能か返します。
	 *
	 * @return {@code true}:更新可能です。 {@code false}:更新不可、作成のみ可能です。
	 */
	public boolean isUpdatable() {
		return Optional.ofNullable(this.id).isPresent();
	}

	@Override
	public String toString() {
		return reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
