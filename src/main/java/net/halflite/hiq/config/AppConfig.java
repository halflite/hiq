package net.halflite.hiq.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.freemarker.FreemarkerMvcFeature;

/** Jersey Configuration */
public class AppConfig {

	/** ビューテンプレートの場所 */
	public static final String VIEW_PATH = "/views/";

	/** Webjarsのリソースパス */
	public static final String RESOURCES_PATH = "/META-INF/resources/";

	/** デフォルトのローカルホスト(主にローカル試験用) */
	public static final String DEFAULT_LOCALHOST = "http://localhost/";

	/** デフォルトのポート(主にローカル試験用) */
	public static final String DEFAULT_PORT = "8080";

	private AppConfig() {
	}

	/**
	 * リソースのconfigを作って返します。
	 *
	 * @return configオブジェクト
	 */
	public static ResourceConfig create() {
		return new ResourceConfig()
				.packages("net.halflite.hiq.resource")
				.register(FreemarkerMvcFeature.class)
				.property(FreemarkerMvcFeature.TEMPLATE_BASE_PATH, VIEW_PATH);
	}
}
