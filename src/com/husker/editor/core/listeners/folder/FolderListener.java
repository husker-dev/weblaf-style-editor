package com.husker.editor.core.listeners.folder;

import com.husker.editor.core.events.*;

public interface FolderListener {
    void folderCreated(FolderCreatedEvent event);
    void folderRemoved(FolderRemovedEvent event);
    void folderClicked(FolderSelectedEvent event);
    void folderParameterChanged(FolderParameterChangedEvent event);
    void selectedFolderChanged(SelectedFolderChanged event);
    void folderMoved(FolderMovedEvent event);
}
