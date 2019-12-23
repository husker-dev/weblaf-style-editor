package com.husker.editor.app.project;

import com.husker.editor.app.project.listeners.code.CodeListener;

import java.util.ArrayList;

public class Code {
    private static ArrayList<CodeListener> listeners = new ArrayList<>();

    public static void addActionListener(CodeListener listener){
        listeners.add(listener);
    }

    public static void event(String text){
        System.out.println("EVENT Components");
        for(CodeListener listener : listeners)
            listener.event(text);
    }
}
