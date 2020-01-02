package com.husker.editor.app.project;

import com.husker.editor.app.Main;
import com.husker.editor.app.constants.ColorConstant;
import com.husker.editor.app.constants.NumberConstant;
import com.husker.editor.app.constants.TextConstant;
import com.husker.editor.app.events.ConstantRemovedEvent;
import com.husker.editor.app.events.ConstantRenamedEvent;
import com.husker.editor.app.events.NewConstantEvent;
import com.husker.editor.app.events.ConstantValueChangedEvent;
import com.husker.editor.app.listeners.contants.ConstantsListener;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Constants {

    // Static
    private static ArrayList<ConstantsListener> listeners = new ArrayList<>();
    private static ArrayList<Class<? extends Constant>> registered_constants = new ArrayList<>();
    static {
        registerConstant(TextConstant.class);
        registerConstant(ColorConstant.class);
        registerConstant(NumberConstant.class);
    }

    public static void registerConstant(Class<? extends Constant> constant){
        if(!registered_constants.contains(constant))
            registered_constants.add(constant);
    }
    public static void addConstantListener(ConstantsListener listener){
        listeners.add(listener);
    }
    public static Class<? extends Constant>[] getConstantTypes(){
        return registered_constants.toArray(new Class[0]);
    }
    public static String getDefaultValue(Class<? extends Constant> constant){
        try{
            return constant.newInstance().getDefaultValue();
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    public static String getTitle(Class<? extends Constant> constant){
        try{
            return constant.newInstance().getTitle();
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    public static Icon getIcon(Class<? extends Constant> constant){
        try{
            return constant.newInstance().getIcon();
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    public static ConstantEditor getEditor(Class<? extends Constant> constant){
        try{
            return constant.newInstance().getEditor();
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    // Variables
    private HashMap<Class<? extends Constant>, HashMap<String, String>> constants = new HashMap<>();
    private Project project;

    public Constants(Project project){
        this.project = project;
        for(Class<? extends Constant> type : registered_constants)
            constants.put(type, new HashMap<>());
    }

    public String[] getConstants(Class<? extends Constant> type){
        ArrayList<String> const_list = new ArrayList<>();
        if(constants.containsKey(type))
            for(Map.Entry<String, String> entry : constants.get(type).entrySet())
                const_list.add(entry.getKey());
        Collections.sort(const_list);
        return const_list.toArray(new String[0]);
    }

    public void setConstant(Class<? extends Constant> type, String name, String value){
        constants.get(type).put(name, value);
        Main.event(Constants.class, listeners, listener -> listener.valueChanged(new ConstantValueChangedEvent(project, type, name, value)));
    }

    public String getConstant(Class<? extends Constant> type, String name){
        return constants.get(type).get(name);
    }

    public void removeConstant(Class<? extends Constant> type, String name){
        constants.get(type).remove(name);
        Main.event(Constants.class, listeners, listener -> listener.removed(new ConstantRemovedEvent(project, type, name)));
    }

    public void renameConstant(Class<? extends Constant> type, String old_name, String new_name){
        String value = constants.get(type).get(old_name);
        constants.get(type).remove(old_name);
        constants.get(type).put(new_name, value);
        Main.event(Constants.class, listeners, listener -> listener.renamed(new ConstantRenamedEvent(project, type, old_name, new_name)));
    }

    public void addConstant(Class<? extends Constant> type){
        try {
            Constant constant = type.newInstance();
            for(int i = 0; true; i++){
                String name = "New constant" + (i == 0 ? "" : " " + i);
                if(getConstant(type, name) == null){
                    constants.get(type).put(name, constant.getDefaultValue());
                    Main.event(Constants.class, listeners, listener -> listener.newConstant(new NewConstantEvent(project, type, name)));
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
