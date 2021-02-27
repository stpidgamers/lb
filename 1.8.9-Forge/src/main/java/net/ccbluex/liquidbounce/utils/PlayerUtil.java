package net.ccbluex.liquidbounce.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MathHelper;

public class PlayerUtil extends MinecraftInstance {
    public static void damage(){
        int damage = 1;
        if (damage > MathHelper.floor_double(mc.thePlayer.getMaxHealth())){
            damage = MathHelper.floor_double(mc.thePlayer.getMaxHealth());
        }
        double offset = 0.0625;
        if (mc.thePlayer != null && mc.getNetHandler() != null && mc.thePlayer.onGround){
            for (int j = 0; j <= (3 + damage) / offset; ++j){
                mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + offset, mc.thePlayer.posZ, false));
                mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, j == (3 + damage) / offset));
            }
        }
    }
}
