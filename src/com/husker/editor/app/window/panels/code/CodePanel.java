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
    private int edited = 0;

    public CodePanel(){
        setPreferredHeight(200);

        sourceViewer = new WebSyntaxArea(SyntaxPreset.xml, SyntaxPreset.editable);
        sourceViewer.applyPresets(SyntaxPreset.base, SyntaxPreset.margin, SyntaxPreset.size, SyntaxPreset.historyLimit);
        scroll = sourceViewer.createScroll(StyleId.syntaxareaScrollUndecorated);
        add(scroll, BorderLayout.CENTER);

        sourceViewer.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                warn();
            }
            public void removeUpdate(DocumentEvent e) {
                warn();
            }
            public void insertUpdate(DocumentEvent e) {
                warn();
            }
            public void warn() {
                if(edited == 0)
                    Code.event(sourceViewer.getText());
                else
                    edited--;
            }
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
        edited++;
        StyleComponent component = Project.getCurrentProject().Components.getSelectedComponent();
        if(component != null)
            sourceViewer.setText("<!--test--> \n" + component.getXMLStyle().toString());
        else
            sourceViewer.setText("");
        scroll.setEnabled(!(Project.getCurrentProject() == null || Project.getCurrentProject().Components.getSelectedComponent() == null));
    }
}
