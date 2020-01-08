package com.husker.editor.core.listeners.skin;

import com.husker.editor.core.events.LastSkinAppliedEvent;
import com.husker.editor.core.events.SkinAppliedEvent;
import com.husker.editor.core.events.SkinApplyingEvent;

public interface SkinListener {
    void lastApplied(LastSkinAppliedEvent event);
    void applied(SkinAppliedEvent event);
    void applying(SkinApplyingEvent event);
}
