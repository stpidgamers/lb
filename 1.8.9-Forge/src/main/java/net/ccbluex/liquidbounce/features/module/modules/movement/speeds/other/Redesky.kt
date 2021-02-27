package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other

import net.ccbluex.liquidbounce.event.MoveEvent
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
import net.ccbluex.liquidbounce.utils.MinecraftInstance
import net.ccbluex.liquidbounce.utils.MovementUtils

class Redesky : SpeedMode("Redesky") {
    override fun onMotion() {

    }

    override fun onUpdate() {
        var state = 0
        if (mc.thePlayer.onGround) {
            ++state
        }
        if (MovementUtils.isMoving() && mc.thePlayer.onGround){
          mc.thePlayer.jump()
        }
        when (state){
            1 -> {
                MovementUtils.strafe(0.233f)
                mc.thePlayer.motionY *= 0.97
                mc.thePlayer.motionX *= 1.008
                mc.thePlayer.motionZ *= 1.008
                mc.timer.timerSpeed = 0.55f
            }
            2 -> {
                MovementUtils.strafe(0.211f)
                mc.thePlayer.motionY *= 0.97
                mc.thePlayer.motionX *= 1.008
                mc.thePlayer.motionZ *= 1.008
                mc.timer.timerSpeed = 0.22f
            }
            3 -> {
                MovementUtils.strafe(0.202f)
                mc.thePlayer.motionY *= 0.97
                mc.thePlayer.motionX *= 1.008
                mc.thePlayer.motionZ *= 1.008
                mc.timer.timerSpeed = 1f
                state = 0
            }
        }
        if (MovementUtils.isMoving()){
            mc.thePlayer.motionY *= 0.97
            mc.thePlayer.motionX *= 1.008
            mc.thePlayer.motionZ *= 1.008
        }
    }

    override fun onMove(event: MoveEvent?) {

    }


}