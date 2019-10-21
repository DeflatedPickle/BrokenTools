package com.deflatedpickle.brokentools.client

import com.deflatedpickle.brokentools.client.event.ForgeEventHandler
import com.deflatedpickle.brokentools.common.Proxy
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.event.FMLInitializationEvent

class Proxy : Proxy() {
    override fun init(event: FMLInitializationEvent) {
        super.init(event)

        MinecraftForge.EVENT_BUS.register(ForgeEventHandler())
    }
}