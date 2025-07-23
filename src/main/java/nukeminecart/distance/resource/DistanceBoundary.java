package nukeminecart.distance.resource;

import net.minecraft.util.math.Box;

public record DistanceBoundary(Box bounds, int renderDistance, String dimensionId){
}
