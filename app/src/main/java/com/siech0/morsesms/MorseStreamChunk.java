package com.siech0.morsesms;

public class MorseStreamChunk implements IMorseStreamChunk {
    private final int units;
    private final boolean active;

    public MorseStreamChunk(int units, boolean active){
        this.active = active;
        this.units = units;
    }

    public MorseStreamChunk(MorseStreamChunk other){
        this.active = other.active;
        this.units = other.units;
    }

    @Override
    public int units(){
        return this.units;
    }

    @Override
    public boolean active(){
        return this.active;
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null){
            return false;
        }

        if(!MorseStreamChunk.class.isAssignableFrom(obj.getClass())){
            return false;
        }

        final MorseStreamChunk other = (MorseStreamChunk) obj;
        if(this.units() != other.units() || this.active() != other.active()){
            return false;
        }
        return true;
    }

    @Override
    public String toString(){
        return String.format("{MorseStreamChunk - Units: %d Active: %b}", this.units(), this.active());
    }
}
