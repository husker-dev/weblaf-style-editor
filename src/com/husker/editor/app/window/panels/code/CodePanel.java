package com.husker.editor.app.window.panels.code;

import com.alee.extended.syntax.SyntaxPreset;
import com.alee.extended.syntax.WebSyntaxArea;
import com.alee.extended.syntax.WebSyntaxScrollPane;
import com.alee.laf.panel.WebPanel;
import com.alee.managers.style.StyleId;
import com.husker.editor.app.project.Components;
import com.husker.editor.app.project.Project;
import com.husker.editor.app.project.StyleComponent;
import com.husker.editor.app.project.Code;
import com.husker.editor.app.project.listeners.project.ProjectEvent;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

import static com.husker.editor.app.project.listeners.component.ComponentEvent.Type.*;


public class CodePanel extends WebPanel {

    private WebSyntaxArea sourceViewer;
    private WebSyntaxScrollPane scroll;

    public CodePanel(){
        setPreferredHeight(200);

        sourceViewer = new WebSyntaxArea(SyntaxPreset.xml, SyntaxPreset.editable);
        sourceViewer.applyPresets(SyntaxPreset.base, SyntaxPreset.margin, SyntaxPreset.size, SyntaxPreset.historyLimit);
        scroll = sourceViewer.createScroll(StyleId.syntaxareaScrollUndecorated);
        add(scroll, BorderLayout.CENTER);

        sourceViewer.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                if(sourceViewer.isEditable())
                    Code.event(sourceViewer.getText());
            }
            public void removeUpdate(DocumentEvent e) {}
            public void insertUpdate(DocumentEvent e) {}
        });

        Components.addListener(e -> {
            if(e.getType().oneOf(Selected_Changed, Style_Changed))
                updateText();
            scroll.setVisible(Project.getCurrentProject().Components.getSelectedComponent() != null);
        });
        Project.addListener(e -> {
            if(e.getType().oneOf(ProjectEvent.Type.Changed))
                updateText();
            scroll.setVisible(Project.getCurrentProject().Components.getSelectedComponent() != null);
        });
    }

    public void updateText(){
        String new_text;
        StyleComponent component = Project.getCurrentProject().Components.getSelectedComponent();
        if(component != null)
            new_text = component.getXMLStyle().toString();
        else
            new_text = "";

        if(!new_text.equals(sourceViewer.getText())){
            sourceViewer.setEditable(false);
            sourceViewer.setText(new_text);
            scroll.setEnabled(!(Project.getCurrentProject() == null || Project.getCurrentProject().Components.getSelectedComponent() == null));
            sourceViewer.setEditable(true);
        }
    }
}
