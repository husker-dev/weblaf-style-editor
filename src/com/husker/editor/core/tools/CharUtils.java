package com.husker.editor.core.tools;

public class CharUtils {

    public static boolean isText(char[] chars, int from, String text){
        try {
            String new_string = "";
            for (int i = from; i < chars.length; i++)
                if (chars[i] != ' ')
                    new_string += chars[i];
            chars = new_string.toCharArray();

            for (int i = 0; i < text.length(); i++)
                if (chars[i] != text.charAt(i))
                    return false;

        }catch (Exception ex){
            return false;
        }
        return true;
    }

    public static String toString(char[] chars, int from){
        String out = "";
        for(int i = from; i < chars.length; i++)
            out += chars[i];
        return out;
    }
}
