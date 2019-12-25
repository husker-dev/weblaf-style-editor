package com.husker.editor.app.project;

import com.husker.editor.app.Main;
import com.husker.editor.app.project.listeners.contants.ConstantsEvent;
import com.husker.editor.app.project.listeners.contants.ConstantsListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Constants {

    static ArrayList<ConstantsListener> listeners = new ArrayList<>();

    public enum ConstType{
        Text,
        Number,
        Color
    }

    private HashMap<ConstType, HashMap<String, String>> consts = new HashMap<>();

    public Constants(){
        for(ConstType type : ConstType.values())
            consts.put(type, new HashMap<>());
    }

    public ArrayList<String> getConstants(ConstType type){
        ArrayList<String> const_list = new ArrayList<>();
        if(consts.containsKey(type))
            for(Map.Entry<String, String> entry : consts.get(type).entrySet())
                const_list.add(entry.getKey());
        Collections.sort(const_list);
        return const_list;
    }

    public void setConstant(ConstType type, String name, String value){
        consts.get(type).put(name, value);
        doEvent(ConstantsEvent.Type.ValueChanged, name);
    }

    public String getConstant(ConstType type, String name){
        return consts.get(type).get(name);
    }

    public void removeConstant(ConstType type, String name){
        consts.get(type).remove(name);
        doEvent(ConstantsEvent.Type.Removed, name);
    }

    public void renameConstant(ConstType type, String old_name, String new_name){
        String value = consts.get(type).get(old_name);
        consts.get(type).remove(old_name);
        consts.get(type).put(new_name, value);
        doEvent(ConstantsEvent.Type.Renamed, old_name, new_name);
    }

    public void addConstant(ConstType type){
        for(int i = 0; true; i++){
            String name = "New Constant " + (i == 0 ? "" : i);
            if(getConstant(type, name) == null){
                String value = "";
                if(type == ConstType.Text)
                    value = "Text";
                if(type == ConstType.Number)
                    value = "0";
                if(type == ConstType.Color)
                    value = "0.0.0";

                consts.get(type).put(name, value);
                doEvent(ConstantsEvent.Type.New, name);
                break;
            }
        }
    }

    public static void addListener(ConstantsListener listener){
        listeners.add(listener);
    }

    public static void doEvent(ConstantsEvent event){
        if(Main.event_output_enabled)
            System.out.println("EVENT Constants: " + event.getType().toString());
        for(ConstantsListener listener : listeners)
            listener.event(event);
    }
    public static void doEvent(ConstantsEvent.Type type, Object... objects){
        doEvent(new ConstantsEvent(type, objects));
    }
}
