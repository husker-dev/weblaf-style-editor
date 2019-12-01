package com.husker.editor.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Constants {

    public enum ConstEvent{
        New,
        Removed,
        Renamed,
        ValueChanged
        ;
        public boolean oneOf(ConstEvent... events){
            for (ConstEvent e : events)
                if(name().equals(e.name()))
                    return true;
            return false;
        }

    }

    static ArrayList<IConstantListener> listeners = new ArrayList<>();

    public enum ConstType{
        Text,
        Number,
        Color
    }

    private static HashMap<ConstType, HashMap<String, String>> consts = new HashMap<>();

    static {
        for(ConstType type : ConstType.values())
            consts.put(type, new HashMap<>());
    }

    public static ArrayList<String> getConstants(ConstType type){
        ArrayList<String> const_list = new ArrayList<>();
        if(consts.containsKey(type))
            for(Map.Entry<String, String> entry : consts.get(type).entrySet())
                const_list.add(entry.getKey());
        Collections.sort(const_list);
        return const_list;
    }

    public static void setConstant(ConstType type, String name, String value){
        consts.get(type).put(name, value);
        event(ConstEvent.ValueChanged, name);
    }

    public static String getConstant(ConstType type, String name){
        return consts.get(type).get(name);
    }

    public static void removeConstant(ConstType type, String name){
        consts.get(type).remove(name);
        event(ConstEvent.Removed, name);
    }

    public static void renameConstant(ConstType type, String old_name, String new_name){
        String value = consts.get(type).get(old_name);
        consts.get(type).remove(old_name);
        consts.get(type).put(new_name, value);
        event(ConstEvent.Renamed, old_name, new_name);
    }

    public static void addConstant(ConstType type){
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
                event(ConstEvent.New, name);
                break;
            }
        }
    }

    public static void addListener(IConstantListener listener){
        listeners.add(listener);
    }

    public static void event(ConstEvent event, Object... objects){
        for(IConstantListener listener : listeners)
            listener.event(event, objects);
    }

    public interface IConstantListener{
        void event(ConstEvent event, Object... objects);
    }
}
