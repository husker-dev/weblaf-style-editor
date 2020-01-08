package com.husker.editor.core;

import com.husker.editor.core.events.FolderCreatedEvent;
import com.husker.editor.core.events.FolderMovedEvent;
import com.husker.editor.core.events.SelectedFolderChanged;
import com.husker.editor.core.listeners.folder.FolderListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class FolderRoot {

    private static ArrayList<FolderListener> listeners = new ArrayList<>();
    public static void addFolderListener(FolderListener listener){
        listeners.add(listener);
    }
    public static ArrayList<FolderListener> getListeners(){
        return listeners;
    }

    private Project project;
    private ArrayList<FolderElement> folders = new ArrayList<>();
    private FolderElement selected;

    public FolderRoot(Project project){
        this.project = project;
    }

    public FolderElement[] getSubFolders(){
        ArrayList<FolderElement> folders = new ArrayList<>();
        for(FolderElement folder : getFolders()) {
            folders.add(folder);
            folders.addAll(Arrays.asList(folder.getSubFolders()));
        }
        return folders.toArray(new FolderElement[0]);
    }

    public FolderElement[] getFolders(){
        Collections.sort(folders);
        return folders.toArray(new FolderElement[0]);
    }
    public void addFolder(FolderElement folder){
        if(selected == null)
            selected = folder;
        folders.add(folder);
        folder.setParent(null);
        Collections.sort(folders);
        Main.event(FolderElement.class, listeners, listener -> listener.folderCreated(new FolderCreatedEvent(project, folder)));
    }
    public void removeFolder(FolderElement element){
        folders.remove(element);
        element.removeChild();
    }
    public Project gerProject(){
        return project;
    }

    public void setSelectedFolder(FolderElement folder){
        FolderElement old = selected;
        selected = folder;
        Main.event(FolderRoot.class, listeners, listener -> listener.selectedFolderChanged(new SelectedFolderChanged(project, selected, old)));
    }
    public FolderElement getSelectedFolder(){
        return selected;
    }

    public void silentRemove(FolderElement element){
        folders.remove(element);
    }
    public void silentAdd(FolderElement folder){
        folders.add(folder);
        folder.setParent(null);
        Collections.sort(folders);
    }

    public void moveFolder(FolderElement folder, FolderElement to){
        FolderElement from = folder.getParent();
        if(from == to)
            return;

        ArrayList<FolderElement> to_parents = new ArrayList<>(Arrays.asList(to.getAllParents()));
        if(to_parents.contains(folder))
            return;

        if(from == null)
            silentRemove(folder);
        else
            from.silentRemove(folder);

        if(to == null)
            silentAdd(folder);
        else
            to.silentAdd(folder);
        Main.event(FolderRoot.class, listeners, listener -> listener.folderMoved(new FolderMovedEvent(project, folder, from, to)));
    }
}
