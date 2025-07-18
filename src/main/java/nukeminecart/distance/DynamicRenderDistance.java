package nukeminecart.distance;

import net.fabricmc.api.ClientModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DynamicRenderDistance implements ClientModInitializer {
	public static final String MOD_ID = "dynamic-distance";
	
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static int currentRenderDistance = 8;
	public static int userRenderDistance = 2;

	@Override
	public void onInitializeClient() {
		
	}
}