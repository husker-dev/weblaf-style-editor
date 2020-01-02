package com.husker.editor.app.parameters;

import com.alee.laf.combobox.WebComboBox;
import com.husker.editor.app.project.Parameter;

import java.awt.*;
import java.util.function.Consumer;

public class ComboParameter extends Parameter {

    private WebComboBox combo;

    public ComboParameter(String name, String[] items) {
        this(name, null, items);
    }
    public ComboParameter(String name, String group, String[] items) {
        super(name, group);

        for(String item : items)
            combo.addItem(item);
    }

    public void apply(String value) {
        combo.setSelectedItem(value);
    }

    public String getValue() {
        return combo.getSelectedItem().toString();
    }

    public void setEnabled(boolean enabled) {
        combo.setEnabled(enabled);
    }

    public Component initComponent() {
        combo = new WebComboBox();
        combo.setWidePopup(true);
        return combo;
    }

    public void addValueChangedListener(Consumer<String> consumer) {
        combo.addItemListener(e -> consumer.accept(getValue()));
    }
}
