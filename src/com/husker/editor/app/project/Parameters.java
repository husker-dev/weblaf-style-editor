package com.husker.editor.app.project;

import com.husker.editor.app.Main;
import com.husker.editor.app.project.listeners.parameters.ParametersActionListener;

import java.util.ArrayList;

public class Parameters {
    private static ArrayList<ParametersActionListener> listeners = new ArrayList<>();

    public static void addActionListener(ParametersActionListener listener){
        listeners.add(listener);
    }

    public static void event(){
        if(Main.event_output_enabled)
            System.out.println("EVENT Parameters");
        for(ParametersActionListener listener : listeners)
            listener.event();
    }
}
