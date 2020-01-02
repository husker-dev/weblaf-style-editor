package com.husker.editor.app.listeners.contants;

import com.husker.editor.app.events.ConstantRemovedEvent;
import com.husker.editor.app.events.ConstantRenamedEvent;
import com.husker.editor.app.events.NewConstantEvent;
import com.husker.editor.app.events.ConstantValueChangedEvent;

public abstract class ConstantsAdapter implements ConstantsListener {
    public void newConstant(NewConstantEvent event) {}
    public void renamed(ConstantRenamedEvent event) {}
    public void removed(ConstantRemovedEvent event) {}
    public void valueChanged(ConstantValueChangedEvent event) {}
}
