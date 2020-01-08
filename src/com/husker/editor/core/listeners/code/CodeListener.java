package com.husker.editor.core.listeners.code;

import com.husker.editor.core.events.CodeChangedEvent;

public interface CodeListener {
    void changed(CodeChangedEvent event);
}
