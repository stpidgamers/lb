package net.ccbluex.liquidbounce.features.module.modules.movement;

import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.StepConfirmEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.timer.TickTimer;
import net.minecraft.network.play.client.C03PacketPlayer;

import java.util.Arrays;
import java.util.List;

@ModuleInfo(name = "Step2", description = "Allows you to step up blocks2.", category = ModuleCategory.MOVEMENT)
public class Step2 extends Module {
    int timerSpeed = 0;
    public TickTimer timer = new TickTimer();
    boolean resetTimer = false;
    @EventTarget
    public void onStepConfirm(StepConfirmEvent event){
        double rheight = mc.thePlayer.getEntityBoundingBox().minY - mc.thePlayer.posY;
        boolean canStep = rheight >= 0.625;
        double posX = mc.thePlayer.posX; double posZ = mc.thePlayer.posZ;
        double y = mc.thePlayer.posY;
        double[] heights = {0.425,0.821,0.699,0.599,1.022,1.372,1.652,1.869,2.019,1.907};
        for(double off : heights){
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(posX, y + off, posZ, false));
        }

    }

    @EventTarget
    public void onUpdate(UpdateEvent event){
        double[] heights = {0.425,0.821,0.699,0.599,1.022,1.372,1.652,1.869,2.019,1.907};
        double posX = mc.thePlayer.posX; double posZ = mc.thePlayer.posZ;
        double y = mc.thePlayer.posY;
        for(double off : heights){
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(posX, y + off, posZ, false));
        }
    }

    void ncpStep(double height){
        List<Double> offset = Arrays.asList(0.42,0.333,0.248,0.083,-0.078);
        double posX = mc.thePlayer.posX; double posZ = mc.thePlayer.posZ;
        double y = mc.thePlayer.posY;
        if(height < 1.1){
            double first = 0.42;
            double second = 0.75;
            if(height != 1){
                first *= height;
                second *= height;
                if(first > 0.425){
                    first = 0.425;
                }
                if(second > 0.78){
                    second = 0.78;
                }
                if(second < 0.49){
                    second = 0.49;
                }
            }
            if(first == 0.42)
                first = 0.41999998688698;
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(posX, y + first, posZ, false));
            if(y+second < y + height)
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(posX, y + second, posZ, false));
            return;
        }else if(height <1.6){
            for(int i = 0; i < offset.size(); i++){
                double off = offset.get(i);
                y += off;
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(posX, y, posZ, false));
            }
        }else if(height < 2.1){
            double[] heights = {0.425,0.821,0.699,0.599,1.022,1.372,1.652,1.869};
            for(double off : heights){
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(posX, y + off, posZ, false));
            }
        }else{
            double[] heights = {0.425,0.821,0.699,0.599,1.022,1.372,1.652,1.869,2.019,1.907};
            for(double off : heights){
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(posX, y + off, posZ, false));
            }
        }

    }
}
