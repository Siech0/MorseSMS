package com.siech0.morsesms;

import java.util.Iterator;
import java.util.List;

public interface IMorseOutputStream extends Iterable<IMorseStreamChunk> {
    Iterator<IMorseStreamChunk> iterator();
    void add(IMorseStreamChunk chunk);
    void addAll(IMorseStreamChunk[] data);
    void addAll(List<IMorseStreamChunk> data);
    void addAll(IMorseOutputStream other);
    List<IMorseStreamChunk> data();
}
