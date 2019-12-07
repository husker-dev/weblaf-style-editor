package com.husker.editor.app.parameters;

import com.alee.laf.checkbox.WebCheckBox;
import com.husker.editor.app.project.Parameter;

import java.awt.*;

public class BooleanParameter extends Parameter {

    WebCheckBox checkBox;

    public BooleanParameter(String name, String variable){
        this(name, variable, null);
    }
    public BooleanParameter(String name, String component_variable, String group) {
        super(name, component_variable, null, group);
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

    public void addValueChangedListener(ParameterChanged listener) {
        checkBox.addChangeListener(e -> listener.event(getValue()));
    }
}
