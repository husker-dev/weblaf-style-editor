package com.husker.editor.app.window.panels.code;

import com.alee.extended.syntax.SyntaxPreset;
import com.alee.extended.syntax.WebSyntaxArea;
import com.alee.extended.syntax.WebSyntaxScrollPane;
import com.alee.laf.panel.WebPanel;
import com.alee.managers.style.StyleId;
import com.husker.editor.app.project.Components;
import com.husker.editor.app.project.Project;
import com.husker.editor.app.project.StyleComponent;
import com.husker.editor.app.project.listeners.project.ProjectEvent;

import java.awt.*;

import static com.husker.editor.app.project.listeners.component.ComponentEvent.Type.*;


public class CodePanel extends WebPanel {

    private WebSyntaxArea sourceViewer;
    private WebSyntaxScrollPane scroll;

    public CodePanel(){
        setPreferredHeight(200);

        sourceViewer = new WebSyntaxArea("", SyntaxPreset.xml, SyntaxPreset.viewable);
        sourceViewer.applyPresets(SyntaxPreset.base, SyntaxPreset.margin, SyntaxPreset.size, SyntaxPreset.historyLimit);
        scroll = sourceViewer.createScroll(StyleId.syntaxareaScrollUndecorated);
        add(scroll, BorderLayout.CENTER);

        Components.addListener(e -> {
            if(e.getType().oneOf(Selected_Changed, Style_Changed))
                updateText();
        });
        Project.addListener(e -> {
            if(e.getType().oneOf(ProjectEvent.Type.Changed))
                updateText();
        });
    }

    public void updateText(){
        StyleComponent component = Project.getCurrentProject().Components.getSelectedComponent();
        if(component != null)
            sourceViewer.setText(component.getXMLStyle().toString());
        else
            sourceViewer.setText("");
        scroll.setEnabled(!(Project.getCurrentProject() == null || Project.getCurrentProject().Components.getSelectedComponent() == null));
    }
}
