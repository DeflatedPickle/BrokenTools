package com.deflatedpickle.brokentools.client.event

import com.deflatedpickle.brokentools.common.capability.Breakable
import net.minecraft.item.ItemStack
import net.minecraft.util.text.TextFormatting
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.event.entity.player.ItemTooltipEvent
import net.minecraftforge.event.entity.player.PlayerEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

class ForgeEventHandler {
    @SubscribeEvent
    fun onItemTooltipEvent(event: ItemTooltipEvent) {
        val breakable = Breakable.isCapable(event.itemStack)

        if (breakable != null) {
            if (event.itemStack.itemDamage == event.itemStack.maxDamage) {
                // TODO: Add a translation string
                event.toolTip.add(1, "${TextFormatting.RED}Broken!")
            }
        }
    }
}