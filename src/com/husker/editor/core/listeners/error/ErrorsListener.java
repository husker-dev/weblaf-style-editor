package com.husker.editor.core.listeners.error;

import com.husker.editor.core.events.ErrorAddedEvent;
import com.husker.editor.core.events.ErrorRemovedEvent;

public interface ErrorsListener {
    void added(ErrorAddedEvent event);
    void removed(ErrorRemovedEvent event);
}
