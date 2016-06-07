package __SneakySnake__.generated;

import ej.components.registry.BundleRegistry;
import ej.wadapps.registry.SharedRegistryFactory;

public class BackgroundServicesStandalone {

	public static void main(String[] args) {
		SharedRegistryFactory.getSharedRegistry().register(BundleRegistry.class, new StandaloneRegistry());

		// Start entry point.
		new SneakySnakeEntryPoint().start();

		// Background services are automatically launched.
	}

}