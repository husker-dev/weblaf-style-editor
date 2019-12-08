package com.husker.editor.app.parameters;

import com.husker.editor.app.project.Constants;
import com.husker.editor.app.project.Parameter;
import com.husker.editor.app.window.tools.FloatSpinner;

import javax.swing.*;
import java.awt.*;

public class FloatParameter extends Parameter {

    FloatSpinner spinner;
    float step;

    public FloatParameter(String name, String component_variable) {
        this(name, component_variable, null, 0.1f);
    }
    public FloatParameter(String name, String component_variable, String group) {
        this(name, component_variable, group, 0.1f);
    }
    public FloatParameter(String name, String component_variable, float step) {
        this(name, component_variable, null, step);
    }
    public FloatParameter(String name, String component_variable, String group, float step) {
        super(name, component_variable, Constants.ConstType.Number, group, new Object[]{step});
    }

    public void setValue(String value) {
        if(value.equals(""))
            spinner.setValue(0);
        else
            spinner.setValue(Float.parseFloat(value));
    }

    public String getValue() {
        return spinner.getFloat().toString();
    }

    public void setEnabled(boolean enabled) {
        spinner.setEnabled(enabled);
    }

    public boolean isEnabled() {
        return spinner.isEnabled();
    }

    public void initObjects(Object[][] objects){
        step = (float)objects[0][0];
    }

    public Component initComponent() {
        spinner = new FloatSpinner(step);
        JSpinner.DefaultEditor spinnerEditor = (JSpinner.DefaultEditor)spinner.getEditor();
        spinnerEditor.getTextField().setHorizontalAlignment(JTextField.LEFT);

        return spinner;
    }

    public void addValueChangedListener(ParameterChanged listener) {
        spinner.addChangeListener(e -> listener.event(spinner.getValue().toString()));
    }
}
