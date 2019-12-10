package com.husker.editor.app.project;

import com.alee.laf.optionpane.WebOptionPane;
import com.husker.editor.app.window.Frame;

import javax.swing.*;
import java.util.ArrayList;


public class Project {

    private static ArrayList<IProjectListener> listeners = new ArrayList<>();

    public enum ProjectEvent {
        Current_Project_Changed,
        New_Project,
        Removed_Project
        ;

        public boolean oneOf(ProjectEvent... events){
            for (ProjectEvent e : events)
                if(name().equals(e.name()))
                    return true;
            return false;
        }
    }

    private static Project current;
    private static ArrayList<Project> projects = new ArrayList<>();

    public static void doEvent(ProjectEvent event, Object... objects){
        for(IProjectListener listener : listeners)
            listener.event(event, objects);
    }

    public static void setCurrentProject(Project project){
        current = project;
        doEvent(ProjectEvent.Current_Project_Changed);
    }

    public static Project getCurrentProject(){
        return current;
    }

    public static void addProject(Project project){
        if(!projects.contains(project))
            projects.add(project);
        doEvent(ProjectEvent.New_Project);
        setCurrentProject(project);
    }
    public static void removeProject(Project project){
        projects.remove(project);
        doEvent(ProjectEvent.Removed_Project);
    }

    public static void resetProject(){
        setCurrentProject(null);
    }

    public static ArrayList<Project> getProjects(){
        return projects;
    }
    public static void addListener(IProjectListener listener){
        listeners.add(listener);
    }
    // ----------

    public String name = "Unnamed";

    public com.husker.editor.app.project.Components Components = new Components();

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public void showRenameDialog(){
        String text = (String) WebOptionPane.showInputDialog(Frame.context, "Name:", "Rename", JOptionPane.QUESTION_MESSAGE, null, null, getName());
        if(text == null)
            return;
        else
            setName(text);
    }

    public interface IProjectListener {
        void event(Project.ProjectEvent event, Object... objects);
    }


}
