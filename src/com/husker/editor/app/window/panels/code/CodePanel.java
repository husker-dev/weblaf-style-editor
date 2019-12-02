package com.husker.editor.app.window.panels.code;

import com.alee.extended.syntax.SyntaxPreset;
import com.alee.extended.syntax.WebSyntaxArea;
import com.alee.extended.syntax.WebSyntaxScrollPane;
import com.alee.laf.panel.WebPanel;
import com.alee.managers.style.StyleId;
import com.husker.editor.app.project.Components;
import com.husker.editor.app.project.Project;
import com.husker.editor.app.project.StyleComponent;

import java.awt.*;

import static com.husker.editor.app.project.Components.ComponentEvent.*;


public class CodePanel extends WebPanel {

    public CodePanel(){
        setPreferredHeight(200);

        final String emptyText = "";
        final WebSyntaxArea sourceViewer = new WebSyntaxArea(emptyText, SyntaxPreset.xml, SyntaxPreset.viewable);
        sourceViewer.applyPresets(SyntaxPreset.base, SyntaxPreset.margin, SyntaxPreset.size, SyntaxPreset.historyLimit);
        WebSyntaxScrollPane scroll = sourceViewer.createScroll(StyleId.syntaxareaScrollUndecorated);
        add(scroll, BorderLayout.CENTER);

        Components.addListener((event, objects) -> {
            if(event.oneOf(Selected_Changed, Style_Changed)){
                StyleComponent component = Project.getCurrentProject().Components.getSelectedComponent();
                if(component != null)
                    sourceViewer.setText(component.getXMLStyle().toString());
                else
                    sourceViewer.setText("");
            }

            scroll.setEnabled(!(Project.getCurrentProject() == null || Project.getCurrentProject().Components.getSelectedComponent() == null));
        });

        Project.addListener((event, objects) -> {
            scroll.setEnabled(!(Project.getCurrentProject() == null || Project.getCurrentProject().Components.getSelectedComponent() == null));
        });
    }
}
