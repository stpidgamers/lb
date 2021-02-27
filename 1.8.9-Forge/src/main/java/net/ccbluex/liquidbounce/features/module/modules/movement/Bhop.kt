package net.ccbluex.liquidbounce.features.module.modules.movement

import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.event.MoveEvent
import net.ccbluex.liquidbounce.event.UpdateEvent
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.utils.MinecraftInstance
import net.ccbluex.liquidbounce.utils.MovementUtils
import net.minecraft.network.play.client.C09PacketHeldItemChange


@ModuleInfo(name = "Bhop", description = "gotta go fast", category = ModuleCategory.MOVEMENT)
class Bhop :  Module() {

    @EventTarget
    fun onMove(event: MoveEvent){

    }

    @EventTarget
    fun onUpdate(event: UpdateEvent){
        if (!MovementUtils.isMoving()){
            mc.thePlayer.motionX = 0.0
            mc.thePlayer.motionZ = 0.0
        }
        mc.timer.timerSpeed = 0.76f
        var distance = 0f
        val speed: Float
        mc.thePlayer.motionY += 0.09
        mc.thePlayer.jump()
        mc.thePlayer.motionY -= 0.002
        mc.thePlayer.motionY -= 0.10
        mc.thePlayer.motionY -= 0.22
        mc.thePlayer.motionY = 0.0
        mc.thePlayer.motionY = 13.5E-9
        mc.thePlayer.motionY -= 15.9E-14
        mc.thePlayer.motionY = 12.5E-0 - 12.5E-9
        mc.thePlayer.motionY = -12.5E-0 - -12.5E-9
        for (i in 36..44) {
            val itemStack = mc.thePlayer.inventoryContainer.getSlot(i).stack
            if (itemStack != null) continue
            mc.netHandler.addToSendQueue(C09PacketHeldItemChange(i - 36))
            break
        }




        if (!MovementUtils.isOnGround(0.02)) {
            distance = 1.15f
        }else if (!MovementUtils.isOnGround(0.03)){
            mc.thePlayer.jump()
            mc.thePlayer.motionY += 0.10
            distance = 1.15f
        }else {
            distance = 0.4f
        }

        if (!MovementUtils.isOnGround(1.0)){
            mc.thePlayer.motionY -= 0.04
        }

        if (!MovementUtils.isOnGround(10.0)){
            mc.thePlayer.motionY -= 0.80
            mc.thePlayer.motionY += 0.92
          //  mc.thePlayer.jump()
        }

        speed = if (distance == 1.15f) {
            0.43f
        } else {
            0.56f
        }

        MovementUtils.strafe(speed)
    }

    override fun onDisable() {
        mc.timer.timerSpeed = 1.0f

    }
}