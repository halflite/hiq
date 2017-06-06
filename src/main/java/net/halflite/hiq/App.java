package net.halflite.hiq;

import static net.halflite.hiq.config.AppConfig.RESOURCES_PATH;
import static net.halflite.hiq.config.AppConfig.VIEW_PATH;

import java.net.URI;
import java.util.Map;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import net.halflite.hiq.config.AppConfig;
import net.halflite.hiq.constant.ServerEnvType;
import net.halflite.hiq.util.EnvUtils;

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
		Map<String, String> properties = EnvUtils.allProperties();
		properties.entrySet()
				.stream()
				.forEach(e -> LOGGER.info("env:{}", e));

		// ポート番号
		int port = Integer.valueOf(ServerEnvType.PORT.value(properties));
		LOGGER.info("Server PORT:{}", port);

		// ローカルホスト
		String localhost = ServerEnvType.LOCALHOST.value(properties);
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
}
