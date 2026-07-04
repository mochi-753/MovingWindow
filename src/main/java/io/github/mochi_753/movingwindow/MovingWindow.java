package io.github.mochi_753.movingwindow;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(value = MovingWindow.MOD_ID)
@OnlyIn(Dist.CLIENT)
public class MovingWindow {
    public static final String MOD_ID = "movingwindow";

    public MovingWindow(final FMLJavaModLoadingContext context) {
        context.registerConfig(ModConfig.Type.CLIENT, MovingWindowConfig.SPEC);
    }
}
