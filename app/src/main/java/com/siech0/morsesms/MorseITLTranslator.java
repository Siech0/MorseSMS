package com.siech0.morsesms;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.List;
import java.util.HashMap;

public class MorseITLTranslator implements IMorseTranslator {
    static private final Map<String, List<IMorseStreamChunk>> map;
    static private final List<IMorseStreamChunk> CODE_ERROR = chunksFromStringStatic("........");
    static private final List<IMorseStreamChunk> CODE_END_OF_WORK = chunksFromStringStatic("...-.-");
    static private final List<IMorseStreamChunk> CODE_START = chunksFromStringStatic("-.-.-");
    static private final List<IMorseStreamChunk> CODE_CHAR_SPACE = chunksFromStringStatic("   ");
    static private final List<IMorseStreamChunk> CODE_WORD_SPACE = chunksFromStringStatic("       ");

    @Override
    public IMorseOutputStream translate(String input){
        if(input == null){
            return null;
        }

        String[] words = input.split(" ");
        if(words == null) {
            return null;
        }

        MorseListOutputStream os = new MorseListOutputStream();
        os.addAll(CODE_START);
        os.addAll(CODE_WORD_SPACE);
        if(words.length > 0) {
            os.addAll(this.translateWord(words[0]));
        }
        for(int i = 1; i < words.length; ++i) {
            os.addAll(CODE_WORD_SPACE);
            os.addAll(this.translateWord(words[i]));
        }
        os.addAll(CODE_WORD_SPACE);
        os.addAll(CODE_END_OF_WORK);
        return os;
    }

    private MorseListOutputStream translateWord(String input){
        MorseListOutputStream os = new MorseListOutputStream();
        if(input.length() > 0){
            int c = input.codePointAt(0);
            MorseListOutputStream chunks = getChunkData(c);
            os.addAll(chunks);
        }
        for(int i = 1; i < input.length(); ++i){
            int c = input.codePointAt(i);
            MorseListOutputStream chunks = getChunkData(c);
            if(c != ' '){
                os.addAll(CODE_CHAR_SPACE);
            }
            os.addAll(chunks);
        }
        return os;
    }

    private MorseListOutputStream getChunkData(int codepoint){
        codepoint = Character.toLowerCase(codepoint);
        List<IMorseStreamChunk> chunks = map.get(new String(Character.toChars(codepoint)));
        if(chunks == null){
            return new MorseListOutputStream(CODE_ERROR);
        } else {
            return new MorseListOutputStream(chunks);
        }
    }

    public String toMorseString(IMorseOutputStream os){
        Iterator<IMorseStreamChunk> it = os.iterator();
        StringBuilder sb = new StringBuilder();
        while(it.hasNext()){
            IMorseStreamChunk chunk = it.next();
            if(chunk.active() == false) {
                for(int i = 0; i < chunk.units(); ++i){
                    sb.append(' ');
                }
            } else {
                if(chunk.units() == 1){
                    sb.append('.');
                } else if(chunk.units() == 3) {
                    sb.append('-');
                } else {
                    sb.append(chunk.units());
                }
            }
        }
        return sb.toString();
    }

    public List<IMorseStreamChunk> chunksFromString(String input){
        return chunksFromStringStatic(input);
    }
    
    private static List<IMorseStreamChunk> chunksFromStringStatic(String input){
        List<IMorseStreamChunk> chunks = new ArrayList<IMorseStreamChunk>();
        for(int i = 0; i < input.length(); ++i) {
            char c = input.charAt(i);
            switch(c){
                case '.':
                    chunks.add(new MorseStreamChunk(1, true));
                    break;
                case '-':
                    chunks.add(new MorseStreamChunk(3, true));
                    break;
                case ' ':
                    chunks.add(new MorseStreamChunk(1, false));
                    break;
                default:
                    break;
            }
        }
        return chunks;
    }

    @Override
    public IMorseOutputStream wordSpace(){
        return new MorseListOutputStream(CODE_WORD_SPACE);
    }
    @Override
    public IMorseOutputStream charSpace(){
        return new MorseListOutputStream(CODE_CHAR_SPACE);
    }

    //Massive static map initialization.
    static {
        map = new HashMap<String, List<IMorseStreamChunk>>();
        map. put("a", chunksFromStringStatic(". - "));
        map. put("b", chunksFromStringStatic("- . . . "));
        map. put("c", chunksFromStringStatic("- . - . "));
        map. put("d", chunksFromStringStatic("- . . "));
        map. put("e", chunksFromStringStatic(". "));
        map. put("f", chunksFromStringStatic(". . - . "));
        map. put("g", chunksFromStringStatic("- - . "));
        map. put("h", chunksFromStringStatic(". . . . "));
        map. put("i", chunksFromStringStatic(". . "));
        map. put("j", chunksFromStringStatic(". - - - "));
        map. put("k", chunksFromStringStatic("- . - "));
        map. put("l", chunksFromStringStatic(". - . . "));
        map. put("m", chunksFromStringStatic("- - "));
        map. put("n", chunksFromStringStatic("- . "));
        map. put("o", chunksFromStringStatic("- - - "));
        map. put("p", chunksFromStringStatic(". - - . "));
        map. put("q", chunksFromStringStatic("- - . - "));
        map. put("r", chunksFromStringStatic(". - . "));
        map. put("s", chunksFromStringStatic(". . . "));
        map. put("t", chunksFromStringStatic("- "));
        map. put("u", chunksFromStringStatic(". . - "));
        map. put("v", chunksFromStringStatic(". . . - "));
        map. put("w", chunksFromStringStatic(". - - "));
        map. put("x", chunksFromStringStatic("- . . - "));
        map. put("y", chunksFromStringStatic("- . - - "));
        map. put("z", chunksFromStringStatic("- - . . "));
        map. put(" ", CODE_WORD_SPACE);
        map. put("0",  chunksFromStringStatic("- - - - - "));
        map. put("1",  chunksFromStringStatic(". - - - - "));
        map. put("2",  chunksFromStringStatic(". . - - - "));
        map. put("3",  chunksFromStringStatic(". . . - - "));
        map. put("4",  chunksFromStringStatic(". . . . - "));
        map. put("5",  chunksFromStringStatic(". . . . . "));
        map. put("6",  chunksFromStringStatic("- . . . . "));
        map. put("7",  chunksFromStringStatic("- - . . . "));
        map. put("8",  chunksFromStringStatic("- - - . . "));
        map. put("9",  chunksFromStringStatic("- - - - . "));
        map. put(". ",  chunksFromStringStatic(". - . - . - "));
        map. put(",",  chunksFromStringStatic("- - . . - - "));
        map. put("?",  chunksFromStringStatic(". . - - . . "));
        map. put("'",  chunksFromStringStatic(". - - - - . "));
        map. put("!",  chunksFromStringStatic("- . - . - - "));
        map. put("/",  chunksFromStringStatic("- . . - . "));
        map. put("(",  chunksFromStringStatic("- . - - . "));
        map. put(")",  chunksFromStringStatic("- . - - . - "));
        map. put("&",  chunksFromStringStatic(". - . . . "));
        map. put(":",  chunksFromStringStatic("- - - . . . "));
        map. put(";",  chunksFromStringStatic("- . - . - . "));
        map. put("=",  chunksFromStringStatic("- . . . - "));
        map. put("+",  chunksFromStringStatic(". - . - . "));
        map. put("- ",  chunksFromStringStatic("- . . . . - "));
        map. put("_",  chunksFromStringStatic(". . - - . - "));
        map. put("\"",  chunksFromStringStatic(". - . . - . "));
        map. put("$",  chunksFromStringStatic(". . . - . . - "));
        map. put("@",  chunksFromStringStatic(". - - . - . "));
        map. put("à",  chunksFromStringStatic(". - - . - "));
        map. put("À",  chunksFromStringStatic(". - - . - "));
        map. put("Å",  chunksFromStringStatic(". - - . - "));
        map. put("ä",  chunksFromStringStatic(". - . - "));
        map. put("Ä",  chunksFromStringStatic(". - . - "));
        map. put("Æ",  chunksFromStringStatic(". - . - "));
        map. put("æ",  chunksFromStringStatic(". - . - "));
        map. put("Ą",  chunksFromStringStatic(". - . - "));
        map. put("ą",  chunksFromStringStatic(". - . - "));
        map. put("å",  chunksFromStringStatic(". - - . - "));
        map. put("ć",  chunksFromStringStatic("- . - . . . "));
        map. put("Ć",  chunksFromStringStatic("- . - . . . "));
        map. put("ĉ",  chunksFromStringStatic("- . - . . . "));
        map. put("Ĉ",  chunksFromStringStatic("- . - . . . "));
        map. put("ç",  chunksFromStringStatic("- . - . . . "));
        map. put("Ç",  chunksFromStringStatic("- . - . . . "));
        map. put("đ", chunksFromStringStatic(". . - . . "));
        map. put("Đ", chunksFromStringStatic(". . - . . "));
        map. put("ð", chunksFromStringStatic(". . - - . "));
        map. put("Ð", chunksFromStringStatic(". . - - . "));
        map. put("é", chunksFromStringStatic(". . - . . "));
        map. put("É", chunksFromStringStatic(". . - . . "));
        map. put("è", chunksFromStringStatic(". - . . - "));
        map. put("È", chunksFromStringStatic(". - . . - "));
        map. put("ę", chunksFromStringStatic(". . - . . "));
        map. put("Ę", chunksFromStringStatic(". . - . . "));
        map. put("ĝ", chunksFromStringStatic("- - . - . "));
        map. put("Ĝ", chunksFromStringStatic("- - . - . "));
        map. put("ĥ", chunksFromStringStatic("- - - - "));
        map. put("Ĥ", chunksFromStringStatic("- - - - "));
        map. put("ĵ", chunksFromStringStatic(". - - - . "));
        map. put("Ĵ", chunksFromStringStatic(". - - - . "));
        map. put("ł", chunksFromStringStatic(". - . . - "));
        map. put("Ł", chunksFromStringStatic(". - . . - "));
        map. put("ń", chunksFromStringStatic("- - . - - "));
        map. put("Ń", chunksFromStringStatic("- - . - - "));
        map. put("ñ", chunksFromStringStatic("- - . - - "));
        map. put("Ñ", chunksFromStringStatic("- - . - - "));
        map. put("ó", chunksFromStringStatic("- - - . "));
        map. put("Ó", chunksFromStringStatic("- - - . "));
        map. put("ö", chunksFromStringStatic("- - - . "));
        map. put("Ö", chunksFromStringStatic("- - - . "));
        map. put("ø", chunksFromStringStatic("- - - . "));
        map. put("Ø", chunksFromStringStatic("- - - . "));
        map. put("ś", chunksFromStringStatic(". . . - . . . "));
        map. put("Ś", chunksFromStringStatic(". . . - . . . "));
        map. put("ŝ", chunksFromStringStatic(". . . - . "));
        map. put("Ŝ", chunksFromStringStatic(". . . - . "));
        map. put("š", chunksFromStringStatic("- - - - "));
        map. put("Š", chunksFromStringStatic("- - - - "));
        map. put("þ", chunksFromStringStatic(". - - . . "));
        map. put("Þ", chunksFromStringStatic(". - - . . "));
        map. put("ü", chunksFromStringStatic(". . - - "));
        map. put("Ü", chunksFromStringStatic(". . - - "));
        map. put("ŭ", chunksFromStringStatic(". . - - "));
        map. put("Ŭ", chunksFromStringStatic(". . - - "));
        map. put("ź", chunksFromStringStatic("- - . . - . "));
        map. put("Ź", chunksFromStringStatic("- - . . - . "));
        map. put("ż", chunksFromStringStatic("- - . . - "));
        map. put("Ż", chunksFromStringStatic("- - . . - "));
    }
}
