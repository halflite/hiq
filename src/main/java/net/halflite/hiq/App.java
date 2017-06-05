package net.halflite.hiq;

import static net.halflite.hiq.config.AppConfig.RESOURCES_PATH;
import static net.halflite.hiq.config.AppConfig.VIEW_PATH;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import com.google.common.collect.Maps;

import net.halflite.hiq.config.AppConfig;
import net.halflite.hiq.config.AppConfig.DefaultServerEnvType;

/**
 * アプリケーション起動クラス
 *
 * @author halflite
 *
 */
public class App {

	private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		// jul-to-slf4j init.
		SLF4JBridgeHandler.removeHandlersForRootLogger();
		SLF4JBridgeHandler.install();

		// 環境変数取得
		Map<String, String> props = getPropertie();

		// ポート番号
		int port = Integer.valueOf(getPropertieValue(props, DefaultServerEnvType.PORT));
		LOGGER.info("Server PORT:{}", port);

		// ローカルホスト
		String localhost = getPropertieValue(props, DefaultServerEnvType.LOCALHOST);
		LOGGER.info("localhost:{}", localhost);

		URI uri = UriBuilder.fromUri(localhost).port(port).build();
		HttpServer server = GrizzlyHttpServerFactory.createHttpServer(uri, AppConfig.create(), false);

		HttpHandler handler = new CLStaticHttpHandler(HttpServer.class.getClassLoader(), RESOURCES_PATH, VIEW_PATH);
		server.getServerConfiguration().addHttpHandler(handler, "/");

		try {
			server.start();
		} catch (Exception e) {
			LOGGER.warn("Server fault.", e);
			server.shutdownNow();
		}
	}

	/**
	 * 環境変数をまとめた連想配列を作って返します
	 *
	 * @return
	 */
	private static Map<String, String> getPropertie() {
		// 環境変数をまとめた連想配列を作成
		Map<String, String> props = new HashMap<>();
		props.putAll(System.getenv());
		props.putAll(Maps.fromProperties(System.getProperties()));
		// ログ出力
		props.entrySet()
				.stream()
				.forEach(e -> LOGGER.info("env:{}", e));
		return props;
	}

	private static String getPropertieValue(Map<String, String> props, DefaultServerEnvType type) {
		return props.getOrDefault(type.getKey(), type.getDefaultValue());
	}
}
