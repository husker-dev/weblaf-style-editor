package com.husker.editor.app.listeners.code;

import com.husker.editor.app.events.CodeChangedEvent;

public abstract class CodeAdapter implements CodeListener {
    public void changed(CodeChangedEvent event) {
    }
}
