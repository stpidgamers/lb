package net.ccbluex.liquidbounce.features.module.modules.misc;

import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S06PacketUpdateHealth;
import net.minecraft.network.play.server.S40PacketDisconnect;
import net.minecraft.network.status.client.C01PacketPing;

@ModuleInfo(name = "AntiFall", description = "Sets you back when you fall a certian distance.", category = ModuleCategory.MISC)
public class AntiFall extends Module {
    @EventTarget
    public void onUpdate(UpdateEvent event){
        double posX = mc.thePlayer.posX;
        double posY = mc.thePlayer.posY;
        double posZ = mc.thePlayer.posZ;
        if (mc.thePlayer.fallDistance > 5.0){
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(posX,-999,posZ,true));
        }
    }

    @EventTarget
    public void onPacket(PacketEvent event){

    }

    public void no(){
     //   if (event.getPacket() instanceof S40PacketDisconnect){
      //      event.cancelEvent();
      //  }else if (event.getPacket() instanceof C01PacketPing){
      //      event.cancelEvent();
      //  }else if (event.getPacket() instanceof S00PacketDisconnect){
      //      event.cancelEvent();
     //   }else if (event.getPacket() instanceof S06PacketUpdateHealth){
     //      event.cancelEvent();
   //     }
    }
}
