package com.husker.editor.core;

import com.alee.laf.menu.WebPopupMenu;
import com.husker.editor.core.events.FolderCreatedEvent;
import com.husker.editor.core.events.FolderParameterChangedEvent;
import com.husker.editor.core.events.FolderRemovedEvent;
import com.husker.editor.core.events.FolderSelectedEvent;
import com.husker.editor.core.tools.Resources;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public abstract class FolderElement implements Comparable<FolderElement>{

    private Project project;
    private ArrayList<FolderElement> folders = new ArrayList<>();
    private Icon icon;
    private boolean sub_folders_enabled = true;
    private Class<? extends FolderElement>[] sub_folders_types;
    private String title = "";
    private String tag = "";
    private boolean removable = true;
    private FolderElement parent;
    private boolean opened = true;
    private boolean removed = false;

    public FolderElement(Project project){
        this(project, "", Resources.getImageIcon("folder.png"));
    }
    public FolderElement(Project project, Icon icon){
        this(project, "", icon);
    }
    public FolderElement(Project project, String title){
        this(project, title, Resources.getImageIcon("folder.png"));
    }
    public FolderElement(Project project, String title, Icon icon){
        this.title = title;
        this.icon = icon;
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
        folder.setParent(this);
        folders.add(folder);
        Collections.sort(folders);
        Main.event(FolderElement.class, FolderRoot.getListeners(), listener -> listener.folderCreated(new FolderCreatedEvent(project, folder)));
    }

    public void setIcon(Icon icon){
        this.icon = icon;
        parameterChangedEvent(FolderParameterChangedEvent.FolderParameter.ICON);
    }
    public Icon getIcon() {
        return icon;
    }

    public void setSubFolderTypes(Class<? extends FolderElement>... sub_folders){
        this.sub_folders_types = sub_folders;
    }
    public Class<? extends FolderElement>[] getSubFolderTypes(){
        return sub_folders_types;
    }

    public void setSubFoldersEnabled(boolean enabled){
        sub_folders_enabled = enabled;
    }
    public boolean isSubFoldersEnabled(){
        return sub_folders_enabled;
    }

    public void setTitle(String title){
        this.title = title;
        parameterChangedEvent(FolderParameterChangedEvent.FolderParameter.TAG);
    }
    public String getTitle() {
        return title;
    }

    public void setTag(String tag){
        this.tag = tag;
        parameterChangedEvent(FolderParameterChangedEvent.FolderParameter.TAG);
    }
    public String getTag() {
        return tag;
    }

    public void setRemovable(boolean removable){
        this.removable = removable;
    }
    public boolean isRemovable() {
        return removable;
    }

    public Project getProject(){
        return project;
    }

    public FolderRoot getFolderRoot(){
        return project.FolderRoot;
    }

    protected void setParent(FolderElement parent){
        this.parent = parent;
    }
    public FolderElement getParent(){
        return parent;
    }

    protected void onRemoved(){}
    public void removeChild(){
        for(FolderElement element : getFolders())
            element.remove();
        onRemoved();
        Main.event(FolderElement.class, FolderRoot.getListeners(), listener -> listener.folderRemoved(new FolderRemovedEvent(project, this)));
    }
    public void remove(FolderElement element){
        folders.remove(element);
        element.removeChild();
    }
    public void remove(){
        if(getParent() == null){
            getFolderRoot().removeFolder(this);
        }else
            getParent().remove(this);
    }

    public WebPopupMenu getPopupMenu(){
        return PopupMenuConstants.getPopupMenu(PopupMenuConstants.FOLDER);
    }

    protected void onSelected(){
        setOpened(!isOpened());
    }
    public void selected(){
        onSelected();
        Main.event(FolderElement.class, FolderRoot.getListeners(), listener -> listener.folderClicked(new FolderSelectedEvent(project, this)));
    }
    public int getAllParentCount(){
        int count = 0;
        FolderElement element = this;
        while((element = element.getParent()) != null)
            count++;
        return count;
    }
    public FolderElement[] getAllParents(){
        ArrayList<FolderElement> parents = new ArrayList<>();
        FolderElement element = this;
        while(true) {
            parents.add(element);
            if(element.getParent() == null)
                break;
            else
                element = element.getParent();
        }

        return parents.toArray(new FolderElement[0]);
    }
    public void setOpened(boolean opened){
        this.opened = opened;
        parameterChangedEvent(FolderParameterChangedEvent.FolderParameter.STATE);
    }
    public boolean isOpened(){
        return opened;
    }
    protected void parameterChangedEvent(FolderParameterChangedEvent.FolderParameter parameter){
        Main.event(FolderElement.class, FolderRoot.getListeners(), listener -> listener.folderParameterChanged(new FolderParameterChangedEvent(project, this, parameter)));
    }

    public void setSelected(){
        project.FolderRoot.setSelectedFolder(this);
    }
    public boolean isSelected(){
        return project.FolderRoot.getSelectedFolder() == this;
    }

    public void silentRemove(FolderElement element){
        folders.remove(element);
    }
    public void silentAdd(FolderElement folder){
        folder.setParent(this);
        folders.add(folder);
        Collections.sort(folders);
    }
    public void moveTo(FolderElement folder){
        getFolderRoot().moveFolder(this, folder);
    }

    public int compareTo(FolderElement element){
        int result = getClass().getName().compareTo(element.getClass().getName());
        if(result == 0)
            result = getTitle().compareTo(element.getTitle());
        if(result == 0)
            result = getTag().compareTo(element.getTag());
        return result;
    }
}
