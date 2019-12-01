package com.husker.editor.app.parameters;

import com.alee.laf.spinner.WebSpinner;
import com.husker.editor.app.Constants;
import com.husker.editor.app.Parameter;
import com.husker.editor.app.project.Project;

import javax.swing.*;
import java.awt.*;

public class IntegerParameter extends Parameter {
    public WebSpinner spinner;

    public IntegerParameter(String name){
        this(name, "");
    }
    public IntegerParameter(String name, String value) {
        super(name, value, Constants.ConstType.Number, "0");
    }

    public void addValueChangedListener(ParameterChanged listener) {
        spinner.addChangeListener(e -> listener.event(spinner.getValue().toString(), Project.getCurrentProject().Components.getSelectedComponent()));
    }

    public void setEnabled(boolean enabled) {
        spinner.setEnabled(enabled);
    }
    public boolean isEnabled() {
        return spinner.isEnabled();
    }

    public Component initComponent() {
        return spinner = new WebSpinner(new SpinnerNumberModel());
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
