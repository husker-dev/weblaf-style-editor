package com.husker.editor.app.listeners.contants;

import com.husker.editor.app.events.ConstantRemovedEvent;
import com.husker.editor.app.events.ConstantRenamedEvent;
import com.husker.editor.app.events.NewConstantEvent;
import com.husker.editor.app.events.ConstantValueChangedEvent;

public interface ConstantsListener {

    void newConstant(NewConstantEvent event);
    void renamed(ConstantRenamedEvent event);
    void removed(ConstantRemovedEvent event);
    void valueChanged(ConstantValueChangedEvent event);
}
