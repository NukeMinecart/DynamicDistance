package nukeminecart.distance;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.minecraft.text.Text;

public class DynamicRenderDistance implements ClientModInitializer {
	public static final String MOD_ID = "dynamic-distance";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static int currentRenderDistance = 8;
	public static DistanceConfig config;

	@Override
	public void onInitializeClient() {
		AutoConfig.register(DistanceConfig.class, GsonConfigSerializer::new);
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
			ClientCustomCommand.register(dispatcher);
		});
		config = AutoConfig.getConfigHolder(DistanceConfig.class).getConfig();
	}

	public static class ClientCustomCommand {

		// Example field that we want to modify (client-side only)

		public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
			dispatcher.register(
					ClientCommandManager.literal("distance") // Command name: /setfield
							.then(ClientCommandManager.argument("value", IntegerArgumentType.integer()) // Integer argument
									.executes(ClientCustomCommand::execute) // Execute method
							)
			);
		}

		private static int execute(CommandContext<FabricClientCommandSource> context) {
			// Get the integer argument
			int newValue = IntegerArgumentType.getInteger(context, "value");

			// Update the field (client-side only)
			currentRenderDistance = newValue;

			// Send feedback to the client player only
			context.getSource().sendFeedback(
					Text.literal("Field value set to: " + newValue)
			);

			MinecraftClient.getInstance().options.sendClientSettings();

			return 1; // Command success
		}
	}
}