package com.husker.editor.core.listeners.contants;

import com.husker.editor.core.events.ConstantRemovedEvent;
import com.husker.editor.core.events.ConstantRenamedEvent;
import com.husker.editor.core.events.NewConstantEvent;
import com.husker.editor.core.events.ConstantValueChangedEvent;

public interface ConstantsListener {

    void newConstant(NewConstantEvent event);
    void renamed(ConstantRenamedEvent event);
    void removed(ConstantRemovedEvent event);
    void valueChanged(ConstantValueChangedEvent event);
}
