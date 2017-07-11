package net.halflite.hiq.entity;

import static org.apache.commons.lang3.builder.ReflectionToStringBuilder.toStringExclude;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.google.common.collect.ImmutableSet;

import net.halflite.hiq.constant.Constant;
import net.halflite.hiq.type.LabelKeyType;
import net.halflite.hiq.util.LabelKeyTypeUtils;

/**
 * アカウント
 *
 * @author halflite
 *
 */
@Entity
@Table(indexes = { @Index(columnList = "STATUS"), @Index(columnList = "CREATED") })
public class Account extends AbstractUpdatableEntity {

	private static final long serialVersionUID = 1L;

	public static final int PRINCIPAL_LENGTH_MAX = 32;

	public static final int NAME_LENGTH_MAX = 16;

	public static final int DESCRIPTION_LENGTH_MAX = 2048;

	// --------------------------------------------------

	/** 外部アカウントID */
	@Column(length = PRINCIPAL_LENGTH_MAX, nullable = false, unique = true)
	public String principal;

	/** Twitterスクリーンネーム */
	@Column(length = NAME_LENGTH_MAX, nullable = false)
	public String name;

	/** プロフィール */
	@Lob
	@Column(length = DESCRIPTION_LENGTH_MAX, nullable = false)
	public String description;

	/** 状態種別 */
	@Column(length = Constant.STATUS_LENGTH_MAX, nullable = false)
	@Enumerated(EnumType.STRING)
	public AccountStatusType status;

	// -------------------------------------------------- [constructor]

	public Account() {
	}

	public Account(String principal, String name, String description) {
		this.principal = principal;
		this.name = name;
		this.description = description;
		this.status = AccountStatusType.ENABLED;
	}

	// -------------------------------------------------- [inner class]

	/** 状態種別 */
	public static enum AccountStatusType implements LabelKeyType {
		/** 通常 */
		ENABLED,
		/** 停止中 */
		SUSPENDED,
		/** 退会済 */
		LEFT,
		/** 強制退会済 */
		FORCED;

		/** ログイン不可のステータス */
		private static final Collection<AccountStatusType> DENIED_STATUS = ImmutableSet.of(LEFT, FORCED);

		/** @return ログイン不可の時 {@code true} を返します。 */
		public boolean isLoginDenied() {
			return DENIED_STATUS.contains(this);
		}

		@Override
		public String getLabelKey() {
			return LabelKeyTypeUtils.getKey(this);
		}
	}

	@Override
	public String toString() {
		return toStringExclude(this, "admin", "diaries");
	}
}
