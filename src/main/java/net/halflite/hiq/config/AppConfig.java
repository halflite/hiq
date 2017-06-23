package net.halflite.hiq.config;

import java.util.Set;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.freemarker.FreemarkerMvcFeature;
import org.pac4j.jax.rs.features.Pac4JSecurityFeature;
import org.pac4j.jax.rs.grizzly.features.GrizzlyJaxRsContextFactoryProvider;
import org.pac4j.jax.rs.jersey.features.Pac4JValueFactoryProvider;

import net.halflite.hiq.resource.IndexResource;

/** Jersey Resource Configuration */
public class AppConfig extends ResourceConfig {

	/** ビューテンプレートの場所 */
	public static final String VIEW_PATH = "/views/";

	/** Webjarsのリソースパス */
	public static final String RESOURCES_PATH = "/META-INF/resources/";

	public AppConfig() {
		super();
		// アプリケーション固有設定
		this.packages(IndexResource.class.getPackage().getName())
				.register(GrizzlyJaxRsContextFactoryProvider.class)
				.register(Pac4JSecurityFeature.class)
				.register(new Pac4JValueFactoryProvider.Binder())
				.register(AuthConfigContextResolver.class)
				.register(GuiceFeature.class)
				.register(FreemarkerMvcFeature.class)
				.property(FreemarkerMvcFeature.TEMPLATE_BASE_PATH, VIEW_PATH);
	}

	public AppConfig(Class<?>... classes) {
		throw new UnsupportedOperationException();
	}

	public AppConfig(ResourceConfig original) {
		throw new UnsupportedOperationException();
	}

	public AppConfig(Set<Class<?>> classes) {
		throw new UnsupportedOperationException();
	}
}
