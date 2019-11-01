package com.siech0.morsesms;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class MorseListOutputStream implements IMorseOutputStream {
    private List<IMorseStreamChunk> data;

    public MorseListOutputStream(){
        this.data = new ArrayList<IMorseStreamChunk>();
    }
    public MorseListOutputStream(List<IMorseStreamChunk> data) {
        this.data = new ArrayList<IMorseStreamChunk>(data);
    }

    public MorseListOutputStream(MorseStreamChunk[] data){
        this.data = new ArrayList<IMorseStreamChunk>(Arrays.asList(data));
    }

    public MorseListOutputStream(Collection<MorseStreamChunk> data){
        this.data = new ArrayList<IMorseStreamChunk>(data);
    }

    public MorseListOutputStream(MorseListOutputStream other){
        this.data = new ArrayList<IMorseStreamChunk>(other.data);
    }

    @Override
    public Iterator<IMorseStreamChunk> iterator() {
        return data.iterator();
    }

    @Override
    public void add(IMorseStreamChunk chunk){
        this.data.add(chunk);
    }

    @Override
    public void addAll(IMorseStreamChunk[] data){this.data.addAll(Arrays.asList(data));}

    @Override
    public void addAll(List<IMorseStreamChunk> data){
        this.data.addAll(data);
    }

    @Override
    public void addAll(IMorseOutputStream other){
        this.data.addAll(other.data());
    }

    @Override
    public List<IMorseStreamChunk> data(){
        return this.data;
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null){
            return false;
        }

        if(!MorseListOutputStream.class.isAssignableFrom(obj.getClass())){
            return false;
        }

        final MorseListOutputStream other = (MorseListOutputStream) obj;
        if(this.data.size() != other.data.size()){
            return false;
        }
        for(int i = 0; i < this.data.size(); ++i) {
            if(!this.data.get(i).equals(other.data.get(i))){
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("{MorseListOutputStream - data:{\n");
        Iterator<IMorseStreamChunk> it = this.data.iterator();
        while(it.hasNext()){
            IMorseStreamChunk chunk = it.next();
            sb.append(String.format("%s,\n", chunk.toString()));
        }
        sb.append("}}");
        return sb.toString();
    }
}
