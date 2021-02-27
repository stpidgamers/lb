package net.ccbluex.liquidbounce.features.module.modules.combat

import net.ccbluex.liquidbounce.event.*
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.features.module.modules.misc.AntiBot
import net.ccbluex.liquidbounce.script.api.global.Chat
import net.ccbluex.liquidbounce.utils.MinecraftInstance
import net.ccbluex.liquidbounce.utils.MovementUtils
import net.ccbluex.liquidbounce.utils.RotationUtils
import net.ccbluex.liquidbounce.utils.extensions.getDistanceToEntityBox
import net.ccbluex.liquidbounce.utils.timer.MSTimer
import net.ccbluex.liquidbounce.utils.timer.TimeUtils
import net.ccbluex.liquidbounce.value.BoolValue
import net.ccbluex.liquidbounce.value.FloatValue
import net.ccbluex.liquidbounce.value.IntegerValue
import net.ccbluex.liquidbounce.value.ListValue
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemSword
import net.minecraft.network.play.client.C02PacketUseEntity
import net.minecraft.network.play.client.C07PacketPlayerDigging
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement
import net.minecraft.util.BlockPos
import net.minecraft.util.EnumFacing
import org.apache.commons.lang3.RandomUtils
import java.util.ArrayList
import java.util.Comparator
import java.util.function.ToDoubleFunction
import java.util.stream.Collectors

@ModuleInfo(
    name = "NewAura",
    description = "a aura which i can fduckign understand better goddamit",
    category = ModuleCategory.COMBAT
)
class NewAura : Module() {
    var target: Entity? = null
    var range = FloatValue("Range", 3.3f, 1.0f, 8.0f)
    var maxCPS = IntegerValue("MaxCPS", 12, 0, 20)
    var minCPS = IntegerValue("MinCPS", 8, 0, 20)
    var minTurnSpeed = FloatValue("MinTurnSpeed", 3.0f, 1.1f,300.0f)
    var maxTurnSpeed = FloatValue("MaxTurnSpeed", 5.0f, 1.1f,300.0f)
    var autoBlockBoolean = BoolValue("AutoBlock",false)
    var autoBlockMode = ListValue("Autoblock Mode", arrayOf("NCP","Vanilla"),"NCP")
    var targets = ArrayList<Entity>()
    private val randomCenterValue = BoolValue("RandomCenter", true)
    private val predictValue = BoolValue("Predict", true)
    private val outborderValue = BoolValue("Outborder", false)
    private val throughWallsRangeValue = FloatValue("ThroughWallsRange", 3f, 0f, 8f)
    var attackDelay = 0L
    var attackTimer = MSTimer()
    var vanillaAutoBlockValue = 1000
    @EventTarget
    fun onUpdate(event: MotionEvent?) {
        if (event!!.eventState == EventState.PRE){
            for (en in mc.theWorld.loadedEntityList) {
                if (mc.thePlayer.getDistanceToEntity(en) <= range.get()){
                    target = en
                    updateRotations(en)
                    if (attackTimer.hasTimePassed(1000 / 10L) && en != mc.thePlayer && en.isEntityAlive){
                   /*     mc.thePlayer.swingItem()
                        mc.thePlayer.sendQueue.addToSendQueue(
                            C02PacketUseEntity(
                                en,
                                C02PacketUseEntity.Action.ATTACK
                            )
                        )
                        **/
                        if (autoBlockBoolean.get()){
                            var mode = autoBlockMode.get()
                            if (mode.equals("vanilla",true)){
                                if (mc.thePlayer.heldItem.item is ItemSword){
                                    mc.thePlayer.sendQueue.addToSendQueue(C08PacketPlayerBlockPlacement(mc.thePlayer.heldItem))
                                }
                            }
                        }
                        attackTimer.reset()
                    }
                }
            }
        }
    }

    fun updateRotations(entity: Entity?): Boolean{
        if(maxTurnSpeed.get() <= 0F)
            return true

        var boundingBox = entity!!.entityBoundingBox

        val (_, rotation) = RotationUtils.searchCenter(
            boundingBox,
            outborderValue.get() && !attackTimer.hasTimePassed(attackDelay / 2),
            randomCenterValue.get(),
            predictValue.get(),
            mc.thePlayer.getDistanceToEntityBox(entity) < throughWallsRangeValue.get()
        ) ?: return false
        val rotationz = RotationUtils.limitAngleChange(RotationUtils.serverRotation, rotation,(Math.random() * (maxTurnSpeed.get() - minTurnSpeed.get()) + minTurnSpeed.get()).toFloat())

        RotationUtils.setTargetRotation(rotationz, 0)
        return true
    }

    fun canAttack(entity: Entity?): Boolean {
        return if (!entity!!.isInvisible && entity != null && entity is EntityPlayer && !AntiBot.isBot(entity) && !entity.isDead && entity != mc.thePlayer) {
            true
        } else false
    }

    fun attack(entity: Entity?) {
        mc.thePlayer.sendQueue.addToSendQueue(C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK))
        mc.thePlayer.swingItem()
    }

    fun getRange(entity: Entity?): Float{
        return mc.thePlayer.getDistanceToEntity(entity)
    }

    @EventTarget
    fun onSlowDown(event: SlowDownEvent){
        if (autoBlockBoolean.get()){
            var mode = autoBlockMode.get()
            if (mode.equals("vanilla",true)){
                event.forward = 1.0f
                event.strafe = 1.0f
            }
        }
    }
}