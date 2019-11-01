package com.siech0.morsesms;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class MorseListOutputStreamTest {
    @Test
    public void add() {
        MorseListOutputStream actual = new MorseListOutputStream(Arrays.asList(
                new MorseStreamChunk(1, true),
                new MorseStreamChunk( 2, true)
        ));
        MorseListOutputStream expected = new MorseListOutputStream(Arrays.asList(
                new MorseStreamChunk(1, true),
                new MorseStreamChunk( 2, true),
                new MorseStreamChunk(3, true)
        ));
        actual.add(new MorseStreamChunk(3, true));
        assertEquals(expected, actual);
    }

    @Test
    public void addAll() {
        MorseListOutputStream actual = new MorseListOutputStream(Arrays.asList(
           new MorseStreamChunk(1, true),
           new MorseStreamChunk( 2, true)
        ));
        MorseListOutputStream os2 = new MorseListOutputStream(Arrays.asList(
            new MorseStreamChunk(3, true),
            new MorseStreamChunk( 4, true)
        ));
        MorseListOutputStream expected = new MorseListOutputStream(Arrays.asList(
                new MorseStreamChunk(1, true),
                new MorseStreamChunk( 2, true),
                new MorseStreamChunk(3, true),
                new MorseStreamChunk( 4, true)
        ));
        actual.addAll(os2);
        assertEquals(expected, actual);
    }

    @Test
    public void equals() {
        MorseStreamChunk[] l = new MorseStreamChunk[]{
                new MorseStreamChunk(1, true),
                new MorseStreamChunk( 3, false),
                new MorseStreamChunk(123, true)
        };
        MorseListOutputStream os1 = new MorseListOutputStream(Arrays.asList(l));
        MorseListOutputStream os2 = new MorseListOutputStream(Arrays.asList(l));
        assertEquals(os1, os2);
    }

    @Test
    public void toString_test() {
        MorseStreamChunk[] l = new MorseStreamChunk[]{
                new MorseStreamChunk(1, true),
                new MorseStreamChunk( 3, false),
                new MorseStreamChunk(123, true)
        };
        MorseListOutputStream os = new MorseListOutputStream(Arrays.asList(l));
        String expected = "{MorseListOutputStream - data:{\n" +
                l[0] + ",\n" +
                l[1] + ",\n" +
                l[2] + ",\n" +
                "}}";
        assertEquals(expected, os.toString());
    }
}