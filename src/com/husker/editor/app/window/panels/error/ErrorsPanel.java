package com.husker.editor.app.window.panels.error;

import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.managers.style.StyleId;
import com.husker.editor.app.events.ErrorAddedEvent;
import com.husker.editor.app.events.ErrorRemovedEvent;
import com.husker.editor.app.listeners.error.ErrorsListener;
import com.husker.editor.app.project.Errors;
import com.husker.editor.app.project.Error;

import java.util.HashMap;

public class ErrorsPanel extends WebPanel {

    private HashMap<Error, ErrorPanel> panels = new HashMap<>();

    public ErrorsPanel(){
        add(new WebScrollPane(StyleId.scrollpaneUndecorated, new WebPanel(){{
            setLayout(new VerticalFlowLayout());

            Errors.addErrorsListener(new ErrorsListener() {
                public void added(ErrorAddedEvent event) {
                    ErrorPanel panel = new ErrorPanel(event.getError());
                    panels.put(event.getError(), panel);
                    add(panel);
                }
                public void removed(ErrorRemovedEvent event) {
                    if(panels.containsKey(event.getError()))
                        remove(panels.remove(event.getError()));
                }
            });
        }}));
    }
}
