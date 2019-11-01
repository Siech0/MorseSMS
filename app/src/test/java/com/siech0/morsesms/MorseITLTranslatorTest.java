package com.siech0.morsesms;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class MorseITLTranslatorTest {
    private IMorseTranslator translator = new MorseITLTranslator();

    @Test
    public void translate_simple() {
        IMorseOutputStream actual = translator.translate("a");
        IMorseOutputStream expected = new MorseListOutputStream(
                translator.chunksFromString("-.-.-       .-       ...-.-")
        );
        assertEquals(expected, actual);
    }

    @Test
    public void translate_complex() {
        String data = "Hello World";
        IMorseOutputStream actual = translator.translate(data);
        IMorseOutputStream expected = new MorseListOutputStream(
                translator.chunksFromString(
                    "-.-.-       " +
                            "....   .   .-..   .-..   ---       " +
                            ".--   ---   .-.   .-..   -..       " +
                            "...-.-"
                )
        );
        assertEquals(expected, actual);
    }

    @Test
    public void translate_unicode_simple() {
        String data = "À";
        IMorseOutputStream actual = translator.translate(data);
        IMorseOutputStream expected = new MorseListOutputStream(
                translator.chunksFromString(
                        "-.-.-       .--.-       ...-.-"
                )
        );
        assertEquals(expected, actual);
    }

    @Test
    public void translate_unicode_complex(){
        String data = "hÀÈę";
        IMorseOutputStream actual = translator.translate(data);
        IMorseOutputStream expected = new MorseListOutputStream(
                translator.chunksFromString(
                        "-.-.-       " +
                                "....   .--.-   .-..-   ..-..       "+
                                "...-.-"
                )
        );
        assertEquals(expected, actual);

    }

    static final MorseStreamChunk di = new MorseStreamChunk(1, true);
    static final MorseStreamChunk dit = di;
    static final MorseStreamChunk dah = new MorseStreamChunk(3, true);
    static final MorseStreamChunk s = new MorseStreamChunk(1, false);

    @Test
    public void chunksFromString_simple(){
        String data = ".-";
        IMorseTranslator translator = new MorseITLTranslator();
        List<IMorseStreamChunk> received = translator.chunksFromString(data);
        List<IMorseStreamChunk> expected = new ArrayList<IMorseStreamChunk>();
        expected.addAll(Arrays.asList(
                di, dah
        ));

        assertEquals(expected, received);
    }

    @Test
    public void chunksFromString_complex(){
        String data = "-.-.-       .-   -...   -.-.       ...-.-";
        IMorseTranslator translator = new MorseITLTranslator();
        List<IMorseStreamChunk> received = translator.chunksFromString(data);
        List<IMorseStreamChunk> expected = new ArrayList<IMorseStreamChunk>();
        expected.addAll(Arrays.asList(
                dah, di, dah, di, dah,
                s, s, s, s, s, s, s,
                di, dah,
                s, s, s,
                dah, di, di, dit,
                s, s, s,
                dah, di, dah, dit,
                s, s, s, s, s, s, s,
                di, di, di, dah, di, dah
        ));

        assertEquals(expected, received);
    }
}