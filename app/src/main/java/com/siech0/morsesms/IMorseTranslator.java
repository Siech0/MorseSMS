package com.siech0.morsesms;

import java.util.List;

public interface IMorseTranslator {
    IMorseOutputStream wordSpace();
    IMorseOutputStream charSpace();

    IMorseOutputStream translate(String input);
    String toMorseString(IMorseOutputStream os);
    List<IMorseStreamChunk> chunksFromString(String input);
}
