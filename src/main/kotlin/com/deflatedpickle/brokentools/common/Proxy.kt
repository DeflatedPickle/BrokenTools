package com.deflatedpickle.brokentools.common

import com.deflatedpickle.brokentools.common.capability.Breakable
import com.deflatedpickle.brokentools.common.event.ForgeEventHandler
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent

open class Proxy {
    open fun preInit(event: FMLPreInitializationEvent) {
        Breakable.register()
    }

    open fun init(event: FMLInitializationEvent) {
        MinecraftForge.EVENT_BUS.register(ForgeEventHandler())
    }
}