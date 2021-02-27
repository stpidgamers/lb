package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.script.api.global.Chat;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.minecraft.potion.Potion;

public class SpeedPotion extends SpeedMode {
    public SpeedPotion() {
        super("SpeedPotion");
    }

    @Override
    public void onMotion() {
        if (MovementUtils.isMoving()){
            Chat.print("movespeed = " + MovementUtils.getSpeed());
        }
    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void onMove(MoveEvent event) {

    }
}
