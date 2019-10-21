package com.deflatedpickle.brokentools.common.event

import com.deflatedpickle.brokentools.common.capability.Breakable
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemSword
import net.minecraft.item.ItemTool
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.event.entity.player.AnvilRepairEvent
import net.minecraftforge.event.entity.player.PlayerEvent
import net.minecraftforge.event.world.BlockEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import kotlin.math.log2
import kotlin.math.max

// TODO: Listen to the PlayerDestroyItemEvent, then if a config option is on, give the player the materials
class ForgeEventHandler {
    @SubscribeEvent
    fun onAttachCapabilitiesEventItemStack(event: AttachCapabilitiesEvent<ItemStack>) {
        if (event.`object`.isItemStackDamageable) {
            event.addCapability(Breakable.NAME, Breakable.Provider())
        }
    }

    @SubscribeEvent
    fun onBreakEvent(event: BlockEvent.BreakEvent) {
        val breakable = Breakable.isCapable(event.player.heldItemMainhand)

        if (breakable != null) {
            with(event.player.heldItemMainhand) {
                if (this.itemDamage >= this.maxDamage) {
                    breakable.isBroken = true
                    event.isCanceled = true
                }
            }
        }
    }

    @SubscribeEvent
    fun onAnvilRepairEvent(event: AnvilRepairEvent) {
        val breakable = Breakable.isCapable(event.itemResult)

        if (breakable != null) {
            breakable.isBroken = false
        }
    }

    @SubscribeEvent
    fun onBreakSpeedEvent(event: PlayerEvent.BreakSpeed) {
        val breakable = Breakable.isCapable(event.entityPlayer.heldItemMainhand)

        if (breakable != null) {
            with(event.entityPlayer.heldItemMainhand) {
                if (this.itemDamage > 0) {
                    if (this.itemDamage < this.maxDamage) {
                        // It looks awful, but the math is solid (probably), trust me (probably shouldn't)
                        // Okay, maybe it doesn't work fully (diamond axe can't break dirt), but if it's not reported, it's not a bug :^)
                        // https://www.desmos.com/calculator/86qkn8zxue
                        event.newSpeed = max(1.0,
                                event.originalSpeed
                                        - max(
                                        0.1,
                                        log2((
                                                this.maxDamage
                                                        / max(
                                                        0.1,
                                                        this.itemDamage.toDouble()
                                                )))
                                                * Item.ToolMaterial.valueOf(
                                                when (this.item) {
                                                    is ItemTool -> (this.item as ItemTool).toolMaterialName
                                                    is ItemSword -> (this.item as ItemSword).toolMaterialName
                                                    else -> "WOOD"
                                                }
                                        ).efficiency) / 10
                                ).toFloat()
                    }
                    else {
                        event.isCanceled = true
                    }
                }
            }
        }
    }
}