package net.ccbluex.liquidbounce.features.module.modules.fun;

import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.util.ChatComponentText;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;

public class ChatUtil extends MinecraftInstance {

    public static void printChat(final String text) {
        mc.thePlayer.addChatComponentMessage(new ChatComponentText("\u00a78[\u00a79Memeware\u00a78]\u00a7f " + text));
    }

    public static void printChatFlag(final String text) {
        mc.thePlayer.addChatComponentMessage(new ChatComponentText("\u00a78[\u00a79MAC\u00a78]\u00a7f " + text));
    }

    public static void sendChat(final String text) {
        mc.thePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage(text));
    }

}
