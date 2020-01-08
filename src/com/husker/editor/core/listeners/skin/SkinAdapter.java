package com.husker.editor.core.listeners.skin;

import com.husker.editor.core.events.LastSkinAppliedEvent;
import com.husker.editor.core.events.SkinAppliedEvent;
import com.husker.editor.core.events.SkinApplyingEvent;

public abstract class SkinAdapter implements SkinListener {
    public void lastApplied(LastSkinAppliedEvent event) {

    }
    public void applied(SkinAppliedEvent event) {

    }
    public void applying(SkinApplyingEvent event) {

    }
}
