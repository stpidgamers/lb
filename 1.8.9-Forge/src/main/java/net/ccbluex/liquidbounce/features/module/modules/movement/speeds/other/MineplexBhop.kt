package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other

import net.ccbluex.liquidbounce.event.JumpEvent
import net.ccbluex.liquidbounce.event.MoveEvent
import net.ccbluex.liquidbounce.event.PacketEvent
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
import net.ccbluex.liquidbounce.utils.ChatUtil
import net.ccbluex.liquidbounce.utils.MovementUtils
import net.minecraft.network.play.server.S08PacketPlayerPosLook
import kotlin.math.min

class MineplexBhop : SpeedMode("MineplexBhop") {
    override fun onMotion() {
        ++mineplexStage
        var speed = 0.0.toFloat()
        //ChatUtil.printChat("stage = " + mineplexStage)

        if (mineplex < 0) mineplex++
        if (mc.thePlayer.posY != mc.thePlayer.posY) {
            mineplex = (-1.0).toFloat()
        }
        if (MovementUtils.isOnGround(0.01)){

        }else {

            speed = (0.98 - mineplexStage / 900 + mineplex / 2).toFloat()
        }
        MovementUtils.strafe(speed)
    }
    override fun onUpdate() {
        if (mineplexStage > 3){
            mineplexStage = 0;
        }
    }
    override fun onMove(event: MoveEvent) {
        if (MovementUtils.isOnGround(0.01) && MovementUtils.isMoving()) {
            mc.timer.timerSpeed = 2.001f
            event.y = (0.42.also { mc.thePlayer.motionY = it })
        }
    }
    override fun onJump(event: JumpEvent) {

    }

    override fun onPacket(event: PacketEvent?) {
        if (event!!.packet is S08PacketPlayerPosLook){
            mineplex = -2f;
        }
    }
}