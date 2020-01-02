package com.husker.editor.app.project;

import com.husker.editor.app.Main;
import com.husker.editor.app.events.ConstantChangedEvent;
import com.husker.editor.app.events.ProjectChangedEvent;
import com.husker.editor.app.events.SelectedChangedEvent;
import com.husker.editor.app.events.VariableChangedEvent;
import com.husker.editor.app.listeners.editable_object.EditableObjectAdapter;
import com.husker.editor.app.listeners.project.ProjectListener;
import com.husker.editor.app.skin.CustomSkin;

import java.util.ArrayList;


public class Project {

    // Static
    private static ArrayList<ProjectListener> listeners = new ArrayList<>();
    private static Project current_project;

    public static void setProject(Project project){
        Project old = current_project;
        current_project = project;
        Main.event(Project.class, listeners, listener -> listener.projectChanged(new ProjectChangedEvent(project, old)));
    }
    public static void createProject(){
        setProject(new Project());
    }

    public static void addListener(ProjectListener listener){
        listeners.add(listener);
    }
    public static Project getCurrentProject(){
        return current_project;
    }

    // Variables
    private String name = "Unnamed";

    public final Components Components = new Components(this);
    public final Errors Errors = new Errors(this);
    public final Constants Constants = new Constants(this);

    private EditableObject selected_object;

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public EditableObject getSelectedObject(){
        return selected_object;
    }
    public void setSelectedObject(EditableObject object){
        if(selected_object != null)
            selected_object.setSelected(false);
        selected_object = object;
        if(selected_object != null)
            selected_object.setSelected(true);
    }
    public void resetSelectedObject(){
        setSelectedObject(null);
    }
}
