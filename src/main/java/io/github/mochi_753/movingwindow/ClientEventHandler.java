package io.github.mochi_753.movingwindow;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

@Mod.EventBusSubscriber(modid = MovingWindow.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ClientEventHandler {
    private static final Window WINDOW = Minecraft.getInstance().getWindow();

    @SubscribeEvent
    public static void onClientTick(final TickEvent.ClientTickEvent event) {
        LocalPlayer player = Minecraft.getInstance().player;

        if (player != null && event.phase.equals(TickEvent.Phase.END) && event.side.isClient()) {
            Vec3 deltaMovement = player.getDeltaMovement();
            double yaw = Math.toRadians(player.getYRot());
            double localX = deltaMovement.x() * Math.cos(yaw) + deltaMovement.z() * Math.sin(yaw);
            moveWindow((int) (localX * MovingWindowConfig.HORIZONTAL.get() * -1.0), (int) (deltaMovement.y() * MovingWindowConfig.VERTICAL.get() * -1.0));
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
