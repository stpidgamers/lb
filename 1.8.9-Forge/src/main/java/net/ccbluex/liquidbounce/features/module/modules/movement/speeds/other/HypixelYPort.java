package net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other;

import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.utils.MovementUtils;

public class HypixelYPort extends SpeedMode {
    double moveSpeed;
    double lastDist;
    boolean wasOnGround;
    int ticksSinceJump;

    public HypixelYPort() {
        super("HypixelYPort");
        this.ticksSinceJump = 0;
        this.moveSpeed = 0.0;
    }

    @Override
    public void onMotion() {
        double xDist = mc.thePlayer.posX - mc.thePlayer.prevPosX;
        double zDist = mc.thePlayer.posZ - mc.thePlayer.prevPosZ;
        this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void onMove(MoveEvent event) {
        if (MovementUtils.isMoving()) {
            if (mc.thePlayer.onGround){
                this.moveSpeed = MovementUtils.getBaseMoveSpeed() * 1.022521442084547;
                event.setY(0.4);
                this.ticksSinceJump = 0;
                this.wasOnGround = true;
            }
            else if (this.wasOnGround) {
                this.moveSpeed = 0.4095000046491623;
                this.wasOnGround = false;
            }
            else if (!MovementUtils.isOnGround(0.6121442084547811) && !this.wasOnGround) {
                this.moveSpeed -= this.lastDist / 158.9;
                if (this.ticksSinceJump == 0) {
                    event.setY(-0.22118690746466904);
                    ++this.ticksSinceJump;
                }
            }
            else {
                this.moveSpeed -= this.lastDist / 159.2;
            }
            MovementUtils.strafe((float)this.moveSpeed);
        }
    }
}
