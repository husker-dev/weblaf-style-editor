package com.husker.editor.window.panels.error;

import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.managers.style.StyleId;
import com.husker.editor.core.EditableObject;
import com.husker.editor.core.events.ErrorAddedEvent;
import com.husker.editor.core.events.ErrorRemovedEvent;
import com.husker.editor.core.events.SelectedChangedEvent;
import com.husker.editor.core.listeners.editable_object.EditableObjectAdapter;
import com.husker.editor.core.listeners.error.ErrorsListener;
import com.husker.editor.core.Errors;
import com.husker.editor.core.Error;
import com.husker.editor.core.tools.VisibleUtils;

import java.util.HashMap;

public class ErrorsPanel extends WebPanel {

    private HashMap<Error, ErrorPanel> panels = new HashMap<>();

    private WebScrollPane scroll;

    public ErrorsPanel(){
        add(scroll = new WebScrollPane(StyleId.scrollpaneUndecorated, new WebPanel(){{
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

            EditableObject.addEditableObjectListener(new EditableObjectAdapter() {
                public void selectedChanged(SelectedChangedEvent event) {
                    scroll.setVisible(VisibleUtils.onEditableObject());
                }
            });
        }}));
    }
}
