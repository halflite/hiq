package net.halflite.hiq;

import static net.halflite.hiq.config.AppConfig.RESOURCES_PATH;
import static net.halflite.hiq.config.AppConfig.VIEW_PATH;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.CLStaticHttpHandler;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.halflite.hiq.config.AppConfig;

/**
 * アプリケーション起動クラス
 *
 * @author halflite
 *
 */
public class App {

	private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

	public static void main(String[] args) {
		String envPort = System.getProperties().getOrDefault("PORT", "9998").toString();
		int port = Integer.valueOf(envPort);

		URI uri = UriBuilder.fromUri("http://localhost/").port(port).build();
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
