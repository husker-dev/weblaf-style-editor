package com.husker.editor.app.window.panels.code;

import com.alee.extended.syntax.SyntaxPreset;
import com.alee.extended.syntax.WebSyntaxArea;
import com.alee.laf.panel.WebPanel;
import com.alee.managers.style.StyleId;
import com.husker.editor.app.project.Components;
import com.husker.editor.app.project.Project;
import com.husker.editor.app.StyleComponent;

import java.awt.*;

import static com.husker.editor.app.project.Components.ComponentEvent.*;


public class CodePanel extends WebPanel {

    public CodePanel(){
        setPreferredHeight(200);

        final String emptyText = "";
        final WebSyntaxArea sourceViewer = new WebSyntaxArea ( emptyText, SyntaxPreset.xml, SyntaxPreset.viewable );
        sourceViewer.applyPresets ( SyntaxPreset.base, SyntaxPreset.margin, SyntaxPreset.size, SyntaxPreset.historyLimit );
        add ( sourceViewer.createScroll ( StyleId.syntaxareaScrollUndecorated ), BorderLayout.CENTER );

        Components.addListener((event, objects) -> {
            if(event.oneOf(Selected_Component_Changed, Style_Parameters_Changed)){
                StyleComponent component = Project.getCurrentProject().Components.getSelectedComponent();
                if(component != null)
                    sourceViewer.setText(component.getXMLStyle().toString());
                else
                    sourceViewer.setText("");
            }

            sourceViewer.setEnabled(!(Project.getCurrentProject() == null || Project.getCurrentProject().Components.getSelectedComponent() == null));


        });
    }
}
