package nukeminecart.distance;

import net.minecraft.client.MinecraftClient;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import nukeminecart.distance.resource.DistanceBoundary;

import java.util.ArrayList;
import java.util.List;

public class DistanceManager {
    public final List<DistanceBoundary> boundaries = new ArrayList<>();

    private Box lastBoundary;

    public void updateRenderDistance(BlockPos position) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null) return;

        RegistryKey<World> dimensionKey = client.world.getRegistryKey();
        Identifier dimensionId = dimensionKey.getValue();

        List<DistanceBoundary> insideBoundary = boundaries.stream().filter((boundary) -> boundary.bounds().contains(Vec3d.of(position))).filter((boundary) -> boundary.dimensionId().equals(dimensionId.toString())).toList();
        if (insideBoundary.size() > 1) {
            DynamicRenderDistance.LOGGER.error("Multiple boundaries for one position found. {}", position);
            return;
        }
        if (insideBoundary.isEmpty()) {
            lastBoundary = null;
            setRenderDistance(getUserRenderDistance());
            return;
        }

        DistanceBoundary boundary = insideBoundary.getFirst();
        if (boundary.bounds().equals(lastBoundary)) return;

        setRenderDistance(boundary.renderDistance());
        
        lastBoundary = boundary.bounds();
    }

    private void setRenderDistance(int renderDistance) {
        DynamicRenderDistance.currentRenderDistance = renderDistance;
    }

    private int getUserRenderDistance() {
        return MinecraftClient.getInstance().options.getViewDistance().getValue();
    }
}
