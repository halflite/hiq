package net.halflite.hiq.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Predicates;
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
		properties.putAll(Maps.fromProperties(System.getProperties()));
		properties.putAll(System.getenv());
		return properties;
	}

	/**
	 * 環境変数の中から指定されたキーのものだけを連想配列にして返します
	 *
	 * @param keySet
	 *            キー(複数)
	 * @return System.getenv()/System.getProperties()の内容で指定されたキーを持つ連想配列
	 */
	public static Map<String, String> properties(Collection<String> keySet) {
		return Maps.filterKeys(allProperties(), Predicates.in(keySet));
	}

}
