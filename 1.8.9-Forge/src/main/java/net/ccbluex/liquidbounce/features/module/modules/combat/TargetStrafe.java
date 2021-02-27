package net.ccbluex.liquidbounce.features.module.modules.combat;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.ModuleManager;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

import java.awt.*;

@ModuleInfo(name = "TargetStrafe", description = "Strafes around the current attacking entity.", category = ModuleCategory.COMBAT)
public class TargetStrafe extends Module {
    KillAura aura = (KillAura) LiquidBounce.moduleManager.getModule("Killaura");
    static KillAura aura2 = (KillAura) LiquidBounce.moduleManager.getModule("Killaura");

    @EventTarget
    public void Render(Render2DEvent event){
        if (canStrafe()) {
            Entity entity = aura.getTarget();
            double ticks = event.getPartialTicks();
            double radius = aura.getRangeValue().get() - 1;
            final double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * ticks - mc.getRenderManager().viewerPosX;
            final double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * ticks - mc.getRenderManager().viewerPosY;
            final double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * ticks - mc.getRenderManager().viewerPosZ;

            final float r = Color.darkGray.getRed() / 255.0F;
            final float g = Color.darkGray.getGreen() / 255.0F;
            final float b = Color.darkGray.getBlue() / 255.0F;
            GL11.glPushMatrix();
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(false);
            GL11.glLineWidth(3.0f);
            GL11.glBegin(GL11.GL_LINE_STRIP);

            for (int i = 0; i <= 90; ++i) {
                GL11.glColor3f(r, g, b);
                GL11.glVertex3d(x + radius * Math.cos(i * (Math.PI / 2.0F) / 45.0F), y, z + radius * Math.sin(i * (Math.PI / 2.0F) / 45.0F));
            }

            GL11.glEnd();
            GL11.glDepthMask(true);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glPopMatrix();
        }
    }

    public static boolean canStrafe(){
        return aura2.getTarget() != null && LiquidBounce.moduleManager.getModule("TargetStrafe").getState();
    }
}
