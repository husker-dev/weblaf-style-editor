package com.husker.editor.content.parameters;

import com.alee.laf.spinner.WebSpinner;
import com.husker.editor.content.constants.number.NumberConstant;
import com.husker.editor.core.Parameter;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class IntegerParameter extends Parameter {
    private WebSpinner spinner;

    public IntegerParameter(String name){
        this(name, null);
    }
    public IntegerParameter(String name, String group) {
        super(name, NumberConstant.class, group);
    }

    public void addValueChangedListener(Consumer<String> consumer) {
        spinner.addChangeListener(e -> consumer.accept(spinner.getValue().toString()));
    }

    public void setEnabled(boolean enabled) {
        spinner.setEnabled(enabled);
    }

    public Component initComponent() {
        spinner = new WebSpinner(new SpinnerNumberModel());
        JSpinner.DefaultEditor spinnerEditor = (JSpinner.DefaultEditor)spinner.getEditor();
        spinnerEditor.getTextField().setHorizontalAlignment(JTextField.LEFT);

        return spinner;
    }

    public void apply(String value){
        if(value.equals(""))
            spinner.setValue(0);
        else
            spinner.setValue(Integer.parseInt(value));
    }
    public String getValue(){
        return spinner.getValue().toString();
    }
}
