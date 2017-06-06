package net.halflite.hiq.config;

import static com.google.inject.Stage.PRODUCTION;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.ServiceLocatorProvider;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;

import com.google.inject.Guice;
import com.google.inject.Injector;

import net.halflite.hiq.inject.AppModule;

/**
 * Guice - HK2 DI Bridge Feature
 *
 * @author halflite
 *
 */
public class GuiceFeature implements Feature {

	@Override
	public boolean configure(FeatureContext context) {
		ServiceLocator locator = ServiceLocatorProvider.getServiceLocator(context);
		GuiceBridge.getGuiceBridge().initializeGuiceBridge(locator);

		Injector injector = Guice.createInjector(PRODUCTION, new AppModule());
		GuiceIntoHK2Bridge guiceBridge = locator.getService(GuiceIntoHK2Bridge.class);
		guiceBridge.bridgeGuiceInjector(injector);
		return true;
	}
}
