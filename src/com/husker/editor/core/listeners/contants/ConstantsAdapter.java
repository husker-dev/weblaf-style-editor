package com.husker.editor.core.listeners.contants;

import com.husker.editor.core.events.ConstantRemovedEvent;
import com.husker.editor.core.events.ConstantRenamedEvent;
import com.husker.editor.core.events.NewConstantEvent;
import com.husker.editor.core.events.ConstantValueChangedEvent;

public abstract class ConstantsAdapter implements ConstantsListener {
    public void newConstant(NewConstantEvent event) {}
    public void renamed(ConstantRenamedEvent event) {}
    public void removed(ConstantRemovedEvent event) {}
    public void valueChanged(ConstantValueChangedEvent event) {}
}
