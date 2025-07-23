package nukeminecart.distance.resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.Strictness;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import nukeminecart.distance.DynamicRenderDistance;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class ResourceLoader implements SimpleSynchronousResourceReloadListener {
    private final Gson gson = new GsonBuilder().disableJdkUnsafe().setStrictness(Strictness.STRICT).create();
    
    @Override
    public Identifier getFabricId() {
        return Identifier.of(DynamicRenderDistance.MOD_ID, "bounds");
    }

    @Override
    public void reload(ResourceManager manager) {
        List<DistanceBoundary> boundaries = DynamicRenderDistance.getManager().boundaries;
        boundaries.clear();
        for(Identifier id : manager.findResources("bounds", path -> path.toString().endsWith(".json")).keySet()) {
            try(InputStream stream = manager.getResource(id).orElseThrow().getInputStream()) {
                boundaries.add(gson.fromJson(new InputStreamReader(stream), DistanceBoundary.class));
            } catch(Exception e) {
                DynamicRenderDistance.LOGGER.error("Error occurred while loading resource json {}", id.toString(), e);
            }
        }
        
        //TODO do input validation to make sure that the bounds do not overlap or add a priority system
    }
}
