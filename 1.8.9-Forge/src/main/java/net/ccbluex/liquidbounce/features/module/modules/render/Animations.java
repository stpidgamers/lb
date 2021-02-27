package net.ccbluex.liquidbounce.features.module.modules.render;

import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.ListValue;

@ModuleInfo(name = "Animations", description = "Change block animations.", category = ModuleCategory.RENDER)
public class Animations extends Module {
    public ListValue mode = new ListValue("Mode", new String[]{ "1.7", "Push" }, "1.7");
    public int anim;

    @EventTarget
    public void onUpdate(UpdateEvent event){
        if (mode.equals("1.7")){
            anim = 1;
        }
        if (mode.equals("Push")){
            anim = 2;
        }
    }
}
