package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other

import net.ccbluex.liquidbounce.LiquidBounce
import net.ccbluex.liquidbounce.event.MoveEvent
import net.ccbluex.liquidbounce.features.module.modules.movement.Speed
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode
import net.ccbluex.liquidbounce.utils.MinecraftInstance
import net.ccbluex.liquidbounce.utils.MovementUtils

class JumpFactor : SpeedMode("JumpFactor") {
    override fun onMotion() {
        val speed = LiquidBounce.moduleManager.getModule("Speed") as Speed? ?: return

        if (MovementUtils.isMoving()) {
            if (mc.thePlayer.onGround && speed.jump.get()) {
                mc.thePlayer.jump()
            }
            if (MovementUtils.isOnGround(0.001)){
                if (speed.zeroXZOnGround.get()){
                    MovementUtils.strafe(0.21f)
                }
            }
            mc.thePlayer.jumpMovementFactor = speed.jumpfactorValue.get()
            MovementUtils.strafe()
        }
        if (mc.gameSettings.keyBindBack.isKeyDown) {
            mc.thePlayer.motionX -= speed.jumpfactorValue.get() * speed.jumpfactorValue.get()
            mc.thePlayer.motionZ -= speed.jumpfactorValue.get() * speed.jumpfactorValue.get()
        }
    }

    override fun onUpdate() {

    }

    override fun onMove(event: MoveEvent?) {
        val speed = LiquidBounce.moduleManager.getModule("Speed") as Speed? ?: return
        if (!speed.jump.get() && MovementUtils.isOnGround(0.001) && MovementUtils.isMoving()){
            event!!.y = (speed.motionY.get().toDouble().also { mc.thePlayer.motionY = it })
        }
    }
}