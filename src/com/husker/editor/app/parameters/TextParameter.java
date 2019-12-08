package com.husker.editor.app.parameters;

import com.alee.extended.image.WebImage;
import com.alee.laf.text.WebTextField;
import com.husker.editor.app.project.Constants;
import com.husker.editor.app.project.Parameter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class TextParameter extends Parameter {

    public WebTextField textField;

    public TextParameter(String name, String variable){
        this(name, variable, null);
    }
    public TextParameter(String name, String variable, String group) {
        super(name, variable, Constants.ConstType.Text, group);
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

    public void addValueChangedListener(ParameterChanged listener) {
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


