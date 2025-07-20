package nukeminecart.distance;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = DynamicRenderDistance.MOD_ID)
public class DistanceConfig implements ConfigData {
    boolean isEnabled = true;
    
    @ConfigEntry.BoundedDiscrete(min = 2, max = 32)
    int minimumRenderDistance = 2;

    @ConfigEntry.Gui.Tooltip
    boolean isCreationMode = false;
}
