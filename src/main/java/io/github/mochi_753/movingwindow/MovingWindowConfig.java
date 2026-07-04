package io.github.mochi_753.movingwindow;

import net.minecraftforge.common.ForgeConfigSpec;

public class MovingWindowConfig {
    public static final ForgeConfigSpec.DoubleValue VERTICAL;
    public static final ForgeConfigSpec.DoubleValue HORIZONTAL;
    public static final ForgeConfigSpec SPEC;
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    static {
        VERTICAL = BUILDER
            .comment("Multiplier for vertical window movement")
            .defineInRange("verticalMultiplier", 10.0, 0.0, Double.MAX_VALUE);
        HORIZONTAL = BUILDER
            .comment("Multiplier for horizontal window movement")
            .defineInRange("horizontalMultiplier", 20.0, 0.0, Double.MAX_VALUE);

        SPEC = BUILDER.build();
    }
}
