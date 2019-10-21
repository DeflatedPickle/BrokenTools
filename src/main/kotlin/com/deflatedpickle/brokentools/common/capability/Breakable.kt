package com.deflatedpickle.brokentools.common.capability

import com.deflatedpickle.brokentools.Reference
import com.deflatedpickle.brokentools.api.capability.IBreakable
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTBase
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraft.util.ResourceLocation
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityInject
import net.minecraftforge.common.capabilities.CapabilityManager
import net.minecraftforge.common.capabilities.ICapabilitySerializable
import java.util.concurrent.Callable

object Breakable {
    val NAME = ResourceLocation(Reference.MOD_ID, "breakable")

    const val IS_BROKEN = "isBroken"
    const val DAMAGE = "damage"

    fun isCapable(entity: ItemStack): IBreakable? {
        if (entity.hasCapability(Provider.CAPABILITY!!, null)) {
            entity.getCapability(Provider.CAPABILITY!!, null)!!.also {
                return it
            }
        }
        return null
    }

    class Implementation : IBreakable {
        private var broken = false
        private var damage = 0

        override fun setBroken(value: Boolean) {
            broken = value
        }

        override fun isBroken(): Boolean = broken

        override fun setDamage(value: Int) {
            damage = value
        }

        override fun getDamage(): Int = damage
    }

    class Storage : Capability.IStorage<IBreakable> {
        override fun readNBT(capability: Capability<IBreakable>?, instance: IBreakable?, side: EnumFacing?, nbt: NBTBase?) {
            if (instance is Implementation) {
                with(nbt as NBTTagCompound) {
                    instance.isBroken = this.getBoolean(IS_BROKEN)
                    instance.damage = this.getInteger(DAMAGE)
                }
            }
            else {
                throw IllegalArgumentException("Can not deserialize to an instance that isn't the default implementation")
            }
        }

        override fun writeNBT(capability: Capability<IBreakable>?, instance: IBreakable?, side: EnumFacing?): NBTBase? {
            if (instance != null) {
                return NBTTagCompound().apply {
                    setBoolean(IS_BROKEN, instance.isBroken)
                    setInteger(DAMAGE, instance.damage)
                }
            }
            return null
        }
    }

    class Factory : Callable<IBreakable> {
        override fun call(): IBreakable {
            return Implementation()
        }
    }

    class Provider : ICapabilitySerializable<NBTBase> {
        companion object {
            @JvmStatic
            @CapabilityInject(IBreakable::class)
            var CAPABILITY: Capability<IBreakable>? = null
        }

        val INSTANCE = CAPABILITY?.defaultInstance

        override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean {
            return capability == CAPABILITY
        }

        override fun <T : Any> getCapability(capability: Capability<T>, facing: EnumFacing?): T? {
            return if (capability == CAPABILITY) CAPABILITY!!.cast(this.INSTANCE) else null
        }

        override fun serializeNBT(): NBTBase {
            return CAPABILITY!!.storage.writeNBT(CAPABILITY, this.INSTANCE, null)!!
        }

        override fun deserializeNBT(nbt: NBTBase) {
            CAPABILITY!!.storage.readNBT(CAPABILITY, this.INSTANCE, null, nbt)
        }
    }

    fun register() {
        CapabilityManager.INSTANCE.register(IBreakable::class.java, Storage(), Factory())
    }
}