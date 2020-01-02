package com.husker.editor.app.listeners.skin;

import com.husker.editor.app.events.LastSkinAppliedEvent;
import com.husker.editor.app.events.SkinAppliedEvent;
import com.husker.editor.app.events.SkinApplyingEvent;

public interface SkinListener {
    void lastApplied(LastSkinAppliedEvent event);
    void applied(SkinAppliedEvent event);
    void applying(SkinApplyingEvent event);
}
