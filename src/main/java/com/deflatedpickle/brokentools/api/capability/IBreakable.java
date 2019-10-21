package com.deflatedpickle.brokentools.api.capability;

public interface IBreakable {
    void setBroken(boolean value);
    boolean isBroken();

    void setDamage(int value);
    int getDamage();
}
