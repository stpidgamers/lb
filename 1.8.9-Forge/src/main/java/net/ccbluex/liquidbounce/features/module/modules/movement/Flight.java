package net.ccbluex.liquidbounce.features.module.modules.movement;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.script.api.global.Chat;
import net.ccbluex.liquidbounce.utils.BypassUtil;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.PlayerUtil;
import net.ccbluex.liquidbounce.utils.timer.TickTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.lwjgl.input.Keyboard;


@ModuleInfo(name = "Flight", description = "Allows you to fly in survival mode.", category = ModuleCategory.MOVEMENT)
public class Flight extends Module {
    private final TickTimer tickTimer = new TickTimer();
    public ListValue mode = new ListValue("Mode", new String[]{ "Watchdog", "Motion", "Mineplex", "Redesky" }, "Watchdog");
    //Motion Fly
    public FloatValue motionSpeed = new FloatValue("Motion-Fly-Speed", 1.0f, 0.0f, 10.0f);
    public FloatValue vertcleMotionSpeed = new FloatValue("Motion-Vertical-Fly-Speed", 1.0f, 0.0f, 10.0f);
    //
    public BoolValue quickStop = new BoolValue("Quick-Stop",false);
    public BoolValue viewBob = new BoolValue("ViewBob", true);
    //Hypixel Fly
    public BoolValue blink = new BoolValue("Blink",true);
    public FloatValue boostSpeed = new FloatValue("Hypixel-Boost-Speed", 1.3f, 0.0f, 5.0f);
    public IntegerValue boostTicksz = new IntegerValue("Hypixel-Speed-Boost-Time", 20, 0, 500);
    public FloatValue boostTimer = new FloatValue("Hypixel-Timer-Time", 1.2f,0.1f,10.0f);
    //

    public boolean canFly = false;
    public int time = 0;



    @EventTarget
    public void onUpdate(UpdateEvent event){
        if (!MovementUtils.isMoving()){
            if (quickStop.get()){
                mc.thePlayer.motionX = 0;
                mc.thePlayer.motionZ = 0;
            }
        }

        if (mode.get().equalsIgnoreCase("Watchdog")) {
            if (!LiquidBounce.moduleManager.getModule("Blink").getState() && mc.thePlayer.hurtTime > 0 && blink.get()){
                LiquidBounce.moduleManager.getModule("Blink").toggle();
            }
            if (mc.thePlayer.hurtTime > 0){
                canFly = true;
            }
            if (canFly) {
                time++;
                mc.thePlayer.motionY = 0;
                tickTimer.update();
                int count = 0;
                int fastflew = 0;
                int stateY = 0;
                // Chat.print("fastflew = " + fastflew);

                stateY++;
                if (tickTimer.hasTimePassed(2)) {
                    switch (stateY) {
                        case 0:
                        case 1:
                            this.vClip(1.1274936900641403E-10);
                            ++stateY;
                            break;
                        case 2:
                            this.vClip(5.0E-17);
                            ++stateY;
                        case 3:
                            this.vClip(-1.1274936900641403E-10);
                            stateY = 0;
                            break;
                    }
                    //mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
                    if (!blink.get()){
                        vClip(BypassUtil.range(1.03-5, 3.5E-9));
                    }
                    tickTimer.reset();
                }

                if (time > boostTicksz.get()){
                    MovementUtils.strafe(0.11f);
                    mc.timer.timerSpeed = 1.0f;
                }else {
                    MovementUtils.strafe(boostSpeed.get());
                    mc.timer.timerSpeed = boostTimer.get();
                }



                if (viewBob.get()) {
                    mc.thePlayer.cameraYaw = 0.13f;
                }
            }

        }

        if (mode.get().equalsIgnoreCase("Motion")){
            MovementUtils.strafe(motionSpeed.get());
            mc.thePlayer.onGround= false;
            if (mc.thePlayer.movementInput.jump) {
                mc.thePlayer.motionY = vertcleMotionSpeed.get() * 0.6;
            } else if (mc.thePlayer.movementInput.sneak) {
                mc.thePlayer.motionY = -vertcleMotionSpeed.get() * 0.6;
            } else {
                mc.thePlayer.motionY = 0;
            }
            if (viewBob.get()){
                mc.thePlayer.cameraYaw = 0.13f;
            }
        }

        if (mode.get().equalsIgnoreCase("mineplex")){
            mc.timer.timerSpeed = 0.76f;
            float distance = 0;
            float speed;
            mc.thePlayer.motionY += 0.23f;

            if (!MovementUtils.isOnGround(0.02)){
                distance = 1.15f;
            }

            if (distance == 1.15f){
                speed = 0.43f;
            }else {
                speed = 0.56f;
            }

            MovementUtils.strafe(speed);

        }

        if (mode.get().equalsIgnoreCase("redesky")){
            int timer = 0;

            if (mc.thePlayer.ticksExisted % 2 == 0){
                ++timer;
            }
            switch (timer){
                case 1:
                    mc.timer.timerSpeed = 0.55f;
                    MovementUtils.strafe(0.11f);
                    mc.thePlayer.jump();
                    mc.thePlayer.motionY = 0.0;
                    break;
                case 2:
                    mc.timer.timerSpeed = 0.42f;
                    MovementUtils.strafe(0.22f);
                    mc.thePlayer.jump();
                    mc.thePlayer.motionY = 0.0;
                    break;
                case 3:
                    mc.timer.timerSpeed = 0.32f;
                    MovementUtils.strafe(0.22f);
                    mc.thePlayer.jump();
                    mc.thePlayer.motionY = 0.0;
                    timer = 0;
                    break;
            }
        }
    }

    public void vClip(double y){
        double posx = mc.thePlayer.posX;
        double posy = mc.thePlayer.posY;
        double posz = mc.thePlayer.posZ;
        mc.thePlayer.setPosition(posx, posy + y, posz);
    }

    @Override
    public void onEnable() {
        if (mode.get().equalsIgnoreCase("Watchdog")) {
            PlayerUtil.damage();
            if (mc.thePlayer.hurtTime > 0){
                //LiquidBounce.moduleManager.getModule("Blink").toggle();
            }
        }
    }

    @Override
    public void onDisable() {
        if (LiquidBounce.moduleManager.getModule("Blink").getState() && blink.get()){
            LiquidBounce.moduleManager.getModule("Blink").toggle();
        }
        if (mode.get().equalsIgnoreCase("Watchdog")) {
            time = 0;
            mc.timer.timerSpeed = 1.0f;
        }
        if (mode.get().equalsIgnoreCase("redesky")){
            mc.timer.timerSpeed = 1.0f;
        }
    }
}
