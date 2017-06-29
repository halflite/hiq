package net.halflite.hiq.constant;

import java.nio.charset.StandardCharsets;

/**
 * 定数定義クラス
 *
 * @author halflite
 *
 */
public class Constant {

	private Constant() {
	}

	/** 状態の文字最大長 */
	public static final int STATUS_LENGTH_MAX = 16;

	/** 役割の文字最大長 */
	public static final int ROLE_LENGTH_MAX = 8;

	/** 文字コード(UTF-8) */
	public static final String CHARSET = StandardCharsets.UTF_8.displayName();

	/** ResourceBundleの拡張子 */
	public static final String RESOURCEBUNDLE_EXTENSION = "properties";

}
