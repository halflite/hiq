package net.halflite.hiq.util;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Maps;

/**
 * 環境変数用ユーティリティクラス
 *
 * @author halflite
 */
public class EnvUtils {

	private EnvUtils() {
	}

	/**
	 * 環境変数をまとめた連想配列を作って返します
	 *
	 * @return System.getenv()/System.getProperties()の内容が入った連想配列
	 */
	public static Map<String, String> allProperties() {
		// 環境変数をまとめた連想配列を作成
		Map<String, String> properties = new HashMap<>();
		properties.putAll(System.getenv());
		properties.putAll(Maps.fromProperties(System.getProperties()));
		return properties;
	}
}
