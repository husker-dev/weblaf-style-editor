package com.husker.editor.app.parameters;

import com.husker.editor.app.constants.NumberConstant;
import com.husker.editor.app.project.Parameter;
import com.husker.editor.app.tools.DoubleSpinner;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class DoubleParameter extends Parameter {

    private DoubleSpinner spinner;
    private float step;

    public DoubleParameter(String name) {
        this(name, null, 0.1);
    }
    public DoubleParameter(String name, String group) {
        this(name, group, 0.1);
    }
    public DoubleParameter(String name, float step) {
        this(name, null, step);
    }
    public DoubleParameter(String name, String group, double step) {
        super(name, NumberConstant.class, group, step);
    }

    public void apply(String value) {
        if(value.equals(""))
            spinner.setValue(0);
        else
            spinner.setValue(Float.parseFloat(value));
    }

    public String getValue() {
        return spinner.getDouble().toString();
    }

    public void setEnabled(boolean enabled) {
        spinner.setEnabled(enabled);
    }

    public void initObjects(Object[] objects){
        step = (float)objects[0];
    }

    public Component initComponent() {
        spinner = new DoubleSpinner(step);
        JSpinner.DefaultEditor spinnerEditor = (JSpinner.DefaultEditor)spinner.getEditor();
        spinnerEditor.getTextField().setHorizontalAlignment(JTextField.LEFT);

        return spinner;
    }

    public void addValueChangedListener(Consumer<String> consumer) {
        spinner.addChangeListener(e -> consumer.accept(spinner.getValue().toString()));
    }
}
