package com.siech0.morsesms;

import java.util.List;

public class MorseAmericanTranslator implements IMorseTranslator {

    @Override
    public IMorseOutputStream translate(String input){
        return null;
    }
    @Override
    public String toMorseString(IMorseOutputStream os) {return null;}
    @Override
    public List<IMorseStreamChunk> chunksFromString(String input) {return null; }

    @Override
    public IMorseOutputStream wordSpace(){
        return null;
    }
    @Override
    public IMorseOutputStream charSpace(){
        return null;
    }
}
