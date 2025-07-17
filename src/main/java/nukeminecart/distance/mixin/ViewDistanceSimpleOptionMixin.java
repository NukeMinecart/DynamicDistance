package nukeminecart.distance.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import nukeminecart.distance.DynamicRenderDistance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SimpleOption.class)
public abstract class ViewDistanceSimpleOptionMixin {

    @Shadow public abstract void setValue(Object value);

    @Shadow Object value; 

    @Inject(method = "getValue", at = @At("HEAD"))
    private void forceClientDistance(CallbackInfoReturnable<Integer> cir) {
        SimpleOption<?> option = (SimpleOption<?>) (Object) this;
        
        if(option == MinecraftClient.getInstance().options.getViewDistance()){
            if(DynamicRenderDistance.currentRenderDistance != -1) {
                if (DynamicRenderDistance.currentRenderDistance != (Integer) value) {
                    setValue(DynamicRenderDistance.currentRenderDistance);
                }
            }
        }
    }
}