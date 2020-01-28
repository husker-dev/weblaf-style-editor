package com.husker.editor.core;

import com.husker.editor.core.folders.ProjectFolder;
import com.husker.editor.core.events.ProjectChangedEvent;
import com.husker.editor.core.events.SelectedChangedEvent;
import com.husker.editor.core.listeners.project.ProjectListener;

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
    public final FolderRoot FolderRoot = new FolderRoot(this);

    private EditableObject selected_object;

    public Project(){
        FolderRoot.addFolder(new ProjectFolder(this));
    }

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
        Main.event(EditableObject.class, EditableObject.getListeners(), listener -> listener.selectedChanged(new SelectedChangedEvent(this, selected_object)));
    }
    public void resetSelectedObject(){
        setSelectedObject(null);
    }
}
