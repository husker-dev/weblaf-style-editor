package com.husker.editor.app.parameters;

import com.alee.laf.checkbox.WebCheckBox;
import com.husker.editor.app.project.Parameter;
import com.husker.editor.app.project.listeners.parameter.ParameterChangedListener;

import java.awt.*;

public class BooleanParameter extends Parameter {

    private WebCheckBox checkBox;

    public BooleanParameter(String name){
        this(name, null);
    }
    public BooleanParameter(String name, String group) {
        super(name, null, group);
    }

    public void setValue(String value) {
        checkBox.setSelected(value.equals("true"));
    }

    public String getValue() {
        return checkBox.isSelected() ? "true" : "false";
    }

    public void setEnabled(boolean enabled) {
        checkBox.setEnabled(enabled);
    }

    public boolean isEnabled() {
        return checkBox.isEnabled();
    }

    public Component initComponent() {
        checkBox = new WebCheckBox();
        return checkBox;
    }

    public void addValueChangedListener(ParameterChangedListener listener) {
        checkBox.addChangeListener(e -> listener.event(getValue()));
    }
}
