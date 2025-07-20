package nukeminecart.distance.mixin;

import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import nukeminecart.distance.DynamicRenderDistance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = GameOptions.class, priority = 1500)
public abstract class GameOptionsMixin {

    @Shadow public abstract SimpleOption<Integer> getViewDistance();

    @Inject(method = "getClampedViewDistance", at = @At("HEAD"), cancellable = true)
    public void forceRenderDistance(CallbackInfoReturnable<Integer> cir){
        cir.setReturnValue(DynamicRenderDistance.currentRenderDistance);
    }

    @Redirect(method = "getSyncedOptions", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/option/SimpleOption;getValue()Ljava/lang/Object;",
            ordinal = 0))
    private Object replaceViewDistanceValue(SimpleOption<?> instance) {
        if (instance == getViewDistance()) {
            return DynamicRenderDistance.currentRenderDistance;
        }
        return instance.getValue();
    }
}
