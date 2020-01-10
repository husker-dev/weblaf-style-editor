package com.husker.editor.window.panels.code;

import com.alee.extended.syntax.SyntaxPreset;
import com.alee.extended.syntax.WebSyntaxArea;
import com.alee.extended.syntax.WebSyntaxScrollPane;
import com.alee.laf.panel.WebPanel;
import com.alee.managers.style.StyleId;
import com.husker.editor.core.EditableObject;
import com.husker.editor.core.Project;
import com.husker.editor.core.events.CodeChangedEvent;
import com.husker.editor.core.events.SelectedChangedEvent;
import com.husker.editor.core.listeners.editable_object.EditableObjectAdapter;
import com.husker.editor.core.tools.VisibleUtils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;


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
                    Project.getCurrentProject().getSelectedObject().setCode(sourceViewer.getText());
            }
            public void removeUpdate(DocumentEvent e) {}
            public void insertUpdate(DocumentEvent e) {}
        });

        EditableObject.addEditableObjectListener(new EditableObjectAdapter() {
            public void codeChanged(CodeChangedEvent event) {
                updateText();
            }
            public void selectedChanged(SelectedChangedEvent event) {
                updateText();
            }
        });
        Project.addListener(e -> updateText());
    }

    public void updateText(){
        SwingUtilities.invokeLater(() -> {
            scroll.setVisible(VisibleUtils.onEditableObject());
            String new_text;
            EditableObject component = Project.getCurrentProject().getSelectedObject();
            if(component != null)
                new_text = component.getCode();
            else
                new_text = "";

            if(!new_text.equals(sourceViewer.getText())){
                sourceViewer.setEditable(false);
                sourceViewer.setText(new_text);
                scroll.setEnabled(VisibleUtils.onEditableObject());
                sourceViewer.setEditable(true);
            }
        });
    }
}
