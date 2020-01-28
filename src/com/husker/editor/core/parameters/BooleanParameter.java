package com.husker.editor.core.parameters;

import com.alee.laf.checkbox.WebCheckBox;
import com.husker.editor.core.Parameter;

import java.awt.*;
import java.util.function.Consumer;

public class BooleanParameter extends Parameter {

    private WebCheckBox checkBox;

    public BooleanParameter(String name){
        this(name, null);
    }
    public BooleanParameter(String name, String group) {
        super(name, group);
    }

    public void apply(String value) {
        checkBox.setSelected(value.equals("true"));
    }

    public String getValue() {
        return checkBox.isSelected() ? "true" : "false";
    }

    public void setEnabled(boolean enabled) {
        checkBox.setEnabled(enabled);
    }

    public boolean getBooleanValue(){
        return checkBox.isSelected();
    }

    public Component initComponent() {
        checkBox = new WebCheckBox();
        return checkBox;
    }

    public void addValueChangedListener(Consumer<String> consumer) {
        checkBox.addChangeListener(e -> consumer.accept(getValue()));
    }
}
