package com.husker.editor.app.listeners.code;

import com.husker.editor.app.events.CodeChangedEvent;

public interface CodeListener {
    void changed(CodeChangedEvent event);
}
