package nukeminecart.distance.mixin;

import net.minecraft.server.integrated.IntegratedServer;
import nukeminecart.distance.DynamicRenderDistance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(IntegratedServer.class)
public class IntegratedServerMixin {
    
    @ModifyVariable(
            method = "tick(Ljava/util/function/BooleanSupplier;)V",
            at = @At("STORE"),
            ordinal = 0  // Use ordinal if there are multiple int variables
    )
    private int modifyTickVariable(int i) {
        return Math.max(i, DynamicRenderDistance.currentRenderDistance);
    }
}