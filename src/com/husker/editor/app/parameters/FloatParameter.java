package com.husker.editor.app.parameters;

import com.husker.editor.app.project.Constants;
import com.husker.editor.app.project.Parameter;
import com.husker.editor.app.project.listeners.parameter.ParameterChangedListener;
import com.husker.editor.app.window.tools.FloatSpinner;

import javax.swing.*;
import java.awt.*;

public class FloatParameter extends Parameter {

    private FloatSpinner spinner;
    private float step;

    public FloatParameter(String name) {
        this(name, null, 0.1f);
    }
    public FloatParameter(String name, String group) {
        this(name, group, 0.1f);
    }
    public FloatParameter(String name, float step) {
        this(name, null, step);
    }
    public FloatParameter(String name, String group, float step) {
        super(name, Constants.ConstType.Number, group, new Object[]{step});
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

    public void addValueChangedListener(ParameterChangedListener listener) {
        spinner.addChangeListener(e -> listener.event(spinner.getValue().toString()));
    }
}
