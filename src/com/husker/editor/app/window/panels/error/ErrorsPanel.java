package com.husker.editor.app.window.panels.error;

import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.laf.button.WebButton;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.managers.style.StyleId;
import com.husker.editor.app.project.Errors;
import com.husker.editor.app.project.Error;
import com.husker.editor.app.project.listeners.error.ErrorsEvent;

import javax.swing.*;
import java.util.HashMap;

public class ErrorsPanel extends WebPanel {

    private HashMap<Error, ErrorPanel> panels = new HashMap<>();

    public ErrorsPanel(){
        add(new WebScrollPane(new WebPanel(){{
            setLayout(new VerticalFlowLayout());

            Errors.addListener(event -> {
                SwingUtilities.invokeLater(() -> {
                    if(event.getType().oneOf(ErrorsEvent.Type.Added)) {
                        ErrorPanel panel = new ErrorPanel((Error) event.getObjects()[0]);
                        panels.put((Error) event.getObjects()[0], panel);
                        add(panel);
                    }
                    if(event.getType().oneOf(ErrorsEvent.Type.Removed))
                        if(panels.containsKey(event.getObjects()[0]))
                            remove(panels.remove(event.getObjects()[0]));
                    updateUI();
                });
            });
        }}){{
            setStyleId(StyleId.scrollpaneUndecorated);
        }});
    }
}
