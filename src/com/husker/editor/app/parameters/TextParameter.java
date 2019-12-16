package com.husker.editor.app.parameters;

import com.alee.extended.image.WebImage;
import com.alee.laf.text.WebTextField;
import com.husker.editor.app.project.Constants;
import com.husker.editor.app.project.Parameter;
import com.husker.editor.app.project.listeners.parameter.ParameterChangedListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class TextParameter extends Parameter {

    private WebTextField textField;

    public TextParameter(String name){
        this(name, null);
    }
    public TextParameter(String name, String group) {
        super(name, Constants.ConstType.Text, group);
    }

    public void setValue(String value) {
        textField.setText(value);
    }
    public String getValue() {
        return textField.getText();
    }

    public void setEnabled(boolean enabled) {
        textField.setEnabled(enabled);
    }
    public boolean isEnabled() {
        return textField.isEnabled();
    }

    public Component initComponent() {
        textField = new WebTextField();
        textField.setPreferredWidth(50);
        textField.setTrailingComponent(new WebImage(new ImageIcon("bin/text_field.png")));
        return textField;
    }

    public void addValueChangedListener(ParameterChangedListener listener) {
        textField.getDocument().addDocumentListener(new DocumentListener() {
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
                listener.event(textField.getText());
            }
        });
    }
}


