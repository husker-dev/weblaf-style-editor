package com.husker.editor.core.listeners.folder;

import com.husker.editor.core.events.*;

public abstract class FolderAdapter implements FolderListener{
    public void folderCreated(FolderCreatedEvent event) {

    }
    public void folderRemoved(FolderRemovedEvent event) {

    }
    public void folderClicked(FolderSelectedEvent event) {

    }
    public void folderParameterChanged(FolderParameterChangedEvent event){

    }
    public void selectedFolderChanged(SelectedFolderChanged event) {

    }
    public void folderMoved(FolderMovedEvent event) {

    }
}
