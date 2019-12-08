package com.husker.editor.app.parameters;

import com.alee.laf.combobox.WebComboBox;
import com.husker.editor.app.project.Parameter;

import java.awt.*;

public class ComboParameter extends Parameter {

    String[] items;
    WebComboBox combo;

    public ComboParameter(String name, String component_variable, String[] items) {
        this(name, component_variable, null, items);
    }
    public ComboParameter(String name, String component_variable, String group, String[] items) {
        super(name, component_variable, null, group);

        for(String item : items)
            combo.addItem(item);
    }

    public void setValue(String value) {
        combo.setSelectedItem(value);
    }

    public String getValue() {
        return combo.getSelectedItem().toString();
    }

    public void setEnabled(boolean enabled) {
        combo.setEnabled(enabled);
    }

    public boolean isEnabled() {
        return combo.isEnabled();
    }

    public Component initComponent() {
        combo = new WebComboBox();
        combo.setWidePopup(true);
        return combo;
    }

    public void addValueChangedListener(ParameterChanged listener) {
        combo.addItemListener(e -> listener.event(getValue()));
    }
}
