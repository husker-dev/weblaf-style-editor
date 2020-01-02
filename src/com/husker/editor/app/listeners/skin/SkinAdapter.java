package com.husker.editor.app.listeners.skin;

import com.husker.editor.app.events.LastSkinAppliedEvent;
import com.husker.editor.app.events.SkinAppliedEvent;
import com.husker.editor.app.events.SkinApplyingEvent;

public abstract class SkinAdapter implements SkinListener {
    public void lastApplied(LastSkinAppliedEvent event) {

    }
    public void applied(SkinAppliedEvent event) {

    }
    public void applying(SkinApplyingEvent event) {

    }
}
