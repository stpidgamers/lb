package net.ccbluex.liquidbounce.features.module.modules.movement

import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.event.UpdateEvent
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.utils.MinecraftInstance

class Glide : Module() {
    var viewBobbing = 0

    @EventTarget
    fun onUpdate(event: UpdateEvent){
        if ((mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown())) {
            when (viewBobbing) {
                0 -> {
                    mc.thePlayer.cameraYaw = 0.105f
                    mc.thePlayer.cameraPitch = 0.105f
                    viewBobbing++
                }
                1 -> viewBobbing++
                2 -> viewBobbing = 0
            }
        }
    }
}