package net.halflite.hiq.constant;

import java.util.Map;

/**
 * サーバーのデフォルト値を持つ列挙型
 *
 * @author halflite
 */
public enum ServerEnvType {
	/** ローカルホストURL */
	LOCALHOST("http://localhost/"),
	/** ポート番号 */
	PORT("8080");

	/** デフォルト値 */
	private final String defaultValue;

	private ServerEnvType(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/** @return 連想配列からキーに対応する値を、ない場合はデフォルト値を返します */
	public String value(Map<String, String> properties) {
		String key = this.toString();
		return properties.getOrDefault(key, this.defaultValue);
	}
}
