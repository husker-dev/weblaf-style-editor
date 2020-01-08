package com.husker.editor.core.listeners.code;

import com.husker.editor.core.events.CodeChangedEvent;

public abstract class CodeAdapter implements CodeListener {
    public void changed(CodeChangedEvent event) {
    }
}
