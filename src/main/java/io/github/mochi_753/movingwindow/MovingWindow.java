package io.github.mochi_753.movingwindow;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

@Mod(value = MovingWindow.MOD_ID)
@OnlyIn(Dist.CLIENT)
public class MovingWindow {
    public static final String MOD_ID = "movingwindow";
    private static final Window WINDOW = Minecraft.getInstance().getWindow();

    public MovingWindow(final FMLJavaModLoadingContext context) {
        MinecraftForge.EVENT_BUS.addListener(MovingWindow::onRenderTick);
    }

    public static void onRenderTick(final TickEvent.RenderTickEvent event) {
        LocalPlayer player = Minecraft.getInstance().player;

        if (player != null && event.phase.equals(TickEvent.Phase.START) && event.side.isClient()) {
            Vec3 deltaMovement = player.getDeltaMovement();
            double yaw = Math.toRadians(player.getYRot());

            double localX = deltaMovement.x() * Math.cos(yaw) + deltaMovement.z() * Math.sin(yaw);
            moveWindow((int) (localX * -20.0), (int) (deltaMovement.y() * -10.0));
        }
    }

    private static void moveWindow(final int dx, final int dy) {
        if (WINDOW.shouldClose() || WINDOW.isFullscreen()) return;

        try (MemoryStack memoryStack = MemoryStack.stackPush()) {
            IntBuffer xPos = memoryStack.mallocInt(1);
            IntBuffer yPos = memoryStack.mallocInt(1);

            GLFW.glfwGetWindowPos(WINDOW.getWindow(), xPos, yPos);

            xPos.put(0, xPos.get(0) + dx);
            yPos.put(0, yPos.get(0) + dy);
            GLFW.glfwSetWindowPos(WINDOW.getWindow(), xPos.get(0), yPos.get(0));
        }
    }
}
