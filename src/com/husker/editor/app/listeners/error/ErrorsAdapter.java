package com.husker.editor.app.listeners.error;


import com.husker.editor.app.events.ErrorAddedEvent;
import com.husker.editor.app.events.ErrorRemovedEvent;

public abstract class ErrorsAdapter implements ErrorsListener {

    public void added(ErrorAddedEvent event) {

    }

    public void removed(ErrorRemovedEvent event) {

    }
}
