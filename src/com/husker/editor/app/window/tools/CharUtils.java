package com.husker.editor.app.window.tools;

public class CharUtils {

    public static boolean isText(char[] chars, int from, String text){
        for(int i = 0; i < text.length(); i++)
            if (i > chars.length || chars[from + i] != text.charAt(i))
                return false;

        return true;
    }

    public static String toString(char[] chars, int from){
        String out = "";
        for(int i = from; i < chars.length; i++)
            out += chars[i];
        return out;
    }
}
