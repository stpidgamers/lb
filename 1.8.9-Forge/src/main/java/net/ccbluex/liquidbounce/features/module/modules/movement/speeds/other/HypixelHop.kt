package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other

import net.ccbluex.liquidbounce.LiquidBounce
import net.ccbluex.liquidbounce.event.MoveEvent
import net.ccbluex.liquidbounce.event.PacketEvent
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
import net.ccbluex.liquidbounce.utils.ChatUtil
import net.ccbluex.liquidbounce.utils.MinecraftInstance
import net.ccbluex.liquidbounce.utils.MovementUtils
import net.minecraft.network.play.server.S08PacketPlayerPosLook

class HypixelHop : SpeedMode("Hypixel") {
    override fun onMotion() {

    }

    override fun onUpdate() {

    }

    override fun onMove(event: MoveEvent?) {
        var prevOnGround = false
        var shouldSlow = false;
        var moveSpeed = MovementUtils.getSpeed()
        mc.gameSettings.viewBobbing = false
        val speed = LiquidBounce.moduleManager.getModule("Speed") as Speed? ?: return
        if (MovementUtils.isMoving()) {
            if (MovementUtils.isOnGround(0.01)) {


                if (shouldSlow){

                    moveSpeed = MovementUtils.getBaseMoveSpeed() * 2.149f
                }else {
                    moveSpeed = MovementUtils.getBaseMoveSpeed() * 2.149f
                }

                MovementUtils.strafe(speed.groundBoostValue.get())
                prevOnGround = true
            } else {
                if (prevOnGround) {
                    moveSpeed -= (moveSpeed / 159.0).toFloat()
                    prevOnGround = false
                } else {
                    moveSpeed -= moveSpeed / 159.0f - 1.0E-5F
                }
            }
            when (mc.thePlayer.hurtTime){
                0 -> {
                    shouldSlow = false
                }
                2 -> {
                    shouldSlow = true;
                }
            }
           // ChatUtil.printChat("timer = " + mc.timer.timerSpeed)
            if (MovementUtils.isOnGround(0.01)){
                event!!.y = (0.41999998688697815.also { mc.thePlayer.motionY = it })
            }else if (!MovementUtils.isOnGround(0.05)){
                mc.thePlayer.motionY -= speed.hypixelNegatateMotion.get()
            }
            if (!MovementUtils.isMoving()){
                mc.thePlayer.motionY = 0.0;
              //  mc.thePlayer.motionY -= 53.3299090190309
                mc.thePlayer.motionZ = 0.0;
                mc.thePlayer.motionX = 0.0;
            }
            if (speed.zeroXZOnGround.get()) {
                MovementUtils.strafe(Math.max(MovementUtils.getBaseMoveSpeed(),moveSpeed))
                if (mc.thePlayer.onGround) {
                    mc.thePlayer.motionX = 0.0;
                    mc.thePlayer.motionZ = 0.0;
                }else {
                    mc.thePlayer.motionX *= moveSpeed + 1.0F
                    mc.thePlayer.motionZ *= moveSpeed + 1.0F
                }
            }else {
                MovementUtils.strafe(Math.max(MovementUtils.getBaseMoveSpeed(),moveSpeed))
            }
        }
    }

    override fun onPacket(event: PacketEvent?) {
        if (event!!.packet is S08PacketPlayerPosLook){

        }
    }

}