package me.tomnewton.discordbot.utils;

public class MutableBoolean implements MutableType<Boolean> {

    private boolean value;


    public MutableBoolean() {
    }

    public MutableBoolean(boolean value) {
        this.value = value;
    }

    public boolean and(boolean other) {
        return value && other;
    }

    public boolean or(boolean other) {
        return value || other;
    }

    public boolean xor(boolean other) {
        return value ^ other;
    }

    public boolean andEquals(boolean other) {
        value = and(other);
        return value;
    }

    public boolean orEquals(boolean other) {
        value = or(other);
        return value;
    }

    public boolean xorEquals(boolean other) {
        value = xor(other);
        return value;
    }

    @Override
    public Boolean get() {
        return value;
    }

    @Override
    public void set(Boolean value) {
        this.value = value;
    }
}
