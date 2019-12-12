package com.husker.editor.app.project;

import com.husker.editor.app.project.listeners.project.ProjectEvent;
import com.husker.editor.app.project.listeners.project.ProjectListener;

import java.util.ArrayList;

import static com.husker.editor.app.project.listeners.project.ProjectEvent.Type.*;


public class Project {

    private static ArrayList<ProjectListener> listeners = new ArrayList<>();
    private static Project current_project;

    public static void doEvent(ProjectEvent.Type event, Object... objects){
        doEvent(new ProjectEvent(event, objects));
    }
    public static void doEvent(ProjectEvent event){
        for(ProjectListener listener : listeners)
            listener.event(event);
    }

    public static void setProject(Project project){
        current_project = project;
        current_project.Components.setSelectedComponent(null);
        doEvent(Changed);
    }

    public static void addListener(ProjectListener listener){
        listeners.add(listener);
    }
    public static Project getCurrentProject(){
        return current_project;
    }
    // ----------

    private String name = "Unnamed";

    public final com.husker.editor.app.project.Components Components = new Components();

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

}
