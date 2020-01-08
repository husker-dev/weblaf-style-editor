package com.husker.editor.core;

import com.alee.laf.WebLookAndFeel;
import com.husker.editor.content.constants.color.ColorConstant;
import com.husker.editor.content.constants.number.NumberConstant;
import com.husker.editor.content.constants.text.TextConstant;
import com.husker.editor.core.skin.CustomSkin;
import com.husker.editor.core.tools.UpdateManager;
import com.husker.editor.window.Frame;

import java.util.ArrayList;
import java.util.function.Consumer;

public class Main {

    public static Frame frame;
    public static final String current_version = "1.2";
    public static final boolean event_output_enabled = false;

    public static void main(String[] args){
        WebLookAndFeel.install(CustomSkin.class);

        frame = new Frame();
        frame.setVisible(true);

        UpdateManager.checkForUpdate();

        Project project = new Project();
        Project.setProject(project);
        project.Constants.addConstant(NumberConstant.class);
        project.Constants.addConstant(NumberConstant.class);
        project.Constants.addConstant(TextConstant.class);
        project.Constants.addConstant(TextConstant.class);
        project.Constants.addConstant(ColorConstant.class);
        project.Constants.addConstant(ColorConstant.class);
    }

    public static <T> void event(Class clazz, ArrayList<T> listeners, Consumer<T> consumer){
        if(Main.event_output_enabled)
            System.out.println("EVENT " + clazz.getSimpleName());
        for(T listener : listeners) {
            try {
                consumer.accept(listener);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
