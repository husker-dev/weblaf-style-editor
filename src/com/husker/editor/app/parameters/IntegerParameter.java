package com.husker.editor.app.parameters;

import com.alee.laf.spinner.WebSpinner;
import com.husker.editor.app.project.Constants;
import com.husker.editor.app.project.Parameter;
import com.husker.editor.app.project.listeners.parameter.ParameterChangedListener;

import javax.swing.*;
import java.awt.*;

public class IntegerParameter extends Parameter {
    private WebSpinner spinner;

    public IntegerParameter(String name){
        this(name, null);
    }
    public IntegerParameter(String name, String group) {
        super(name, Constants.ConstType.Number, group);
    }

    public void addValueChangedListener(ParameterChangedListener listener) {
        spinner.addChangeListener(e -> listener.event(spinner.getValue().toString()));
    }

    public void setEnabled(boolean enabled) {
        spinner.setEnabled(enabled);
    }
    public boolean isEnabled() {
        return spinner.isEnabled();
    }

    public Component initComponent() {
        spinner = new WebSpinner(new SpinnerNumberModel());
        JSpinner.DefaultEditor spinnerEditor = (JSpinner.DefaultEditor)spinner.getEditor();
        spinnerEditor.getTextField().setHorizontalAlignment(JTextField.LEFT);

        return spinner;
    }

    public void setValue(String value){
        if(value.equals(""))
            spinner.setValue(0);
        else
            spinner.setValue(Integer.parseInt(value));
    }
    public String getValue(){
        return spinner.getValue().toString();
    }
}
