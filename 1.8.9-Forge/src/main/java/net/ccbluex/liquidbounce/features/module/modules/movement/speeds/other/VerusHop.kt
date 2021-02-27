package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other

import net.ccbluex.liquidbounce.event.MoveEvent
import net.ccbluex.liquidbounce.event.PacketEvent
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
import net.ccbluex.liquidbounce.utils.MovementUtils
import net.minecraft.network.play.server.S08PacketPlayerPosLook

class VerusHop : SpeedMode("VerusHop") {
    override fun onMotion() {

    }

    override fun onUpdate() {
        val moveSpeed = getSpeed()
        if (MovementUtils.isMoving()){
            if (MovementUtils.isOnGround(0.01)){
                mc.thePlayer.jump()
            }else if (!MovementUtils.isOnGround(0.02)){
             //   mc.thePlayer.jump()
            }
            MovementUtils.strafe(moveSpeed)
        }
    }

    override fun onMove(event: MoveEvent?) {

    }

    override fun onPacket(event: PacketEvent?) {
        if (event!!.packet is S08PacketPlayerPosLook) {
            toggle()
        }
    }

    fun getSpeed(): Float {
        if (MovementUtils.isOnGround(0.02)){
            mc.thePlayer.motionX = 0.0
            mc.thePlayer.motionZ = 0.0
            return 0.67f
        }
        if (MovementUtils.isOnGround(0.03)){
            mc.thePlayer.motionY -= 0.11
            return 0.78f
        }
        if (MovementUtils.isOnGround(0.01)){
            return 0.98f
        }
        if (MovementUtils.isOnGround(0.00)){
            return 1.12f
        }
        return 0.33f
    }
}