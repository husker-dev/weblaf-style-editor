package com.husker.editor.app.listeners.error;

import com.husker.editor.app.events.ErrorAddedEvent;
import com.husker.editor.app.events.ErrorRemovedEvent;

public interface ErrorsListener {
    void added(ErrorAddedEvent event);
    void removed(ErrorRemovedEvent event);
}
