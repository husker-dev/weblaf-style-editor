package com.husker.editor.app.parameters;

import com.alee.laf.grouping.GroupPane;
import com.alee.laf.grouping.GroupPaneConstraints;
import com.alee.laf.panel.WebPanel;
import com.husker.editor.app.project.Parameter;
import com.husker.editor.app.project.listeners.parameter.ParameterChangedListener;
import com.husker.editor.app.window.tools.FloatSpinner;

import javax.swing.*;
import java.awt.*;

public class Point2DParameter extends Parameter {

    private FloatSpinner x;
    private FloatSpinner y;
    private float step;

    public Point2DParameter(String name) {
        this(name, null, 0.1f);
    }
    public Point2DParameter(String name, String group) {
        this(name, group, 0.1f);
    }
    public Point2DParameter(String name, float step) {
        this(name, null, step);
    }
    public Point2DParameter(String name, String group, float step) {
        super(name, null, group, new Object[]{step});
    }

    public void setValue(String value) {
        x.setValue(Float.parseFloat(value.split(",")[0]));
        y.setValue(Float.parseFloat(value.split(",")[1]));
    }

    public String getValue() {
        return x.getFloat() + "," + y.getFloat();
    }

    public void setEnabled(boolean enabled) {
        x.setEnabled(enabled);
        y.setEnabled(enabled);
    }

    public boolean isEnabled() {
        return x.isEnabled();
    }

    public Component initComponent() {
        x = new FloatSpinner(step);
        y = new FloatSpinner(step);

        JSpinner.DefaultEditor spinnerEditorX = (JSpinner.DefaultEditor)x.getEditor();
        spinnerEditorX.getTextField().setHorizontalAlignment(JTextField.LEFT);

        JSpinner.DefaultEditor spinnerEditorY = (JSpinner.DefaultEditor)y.getEditor();
        spinnerEditorY.getTextField().setHorizontalAlignment(JTextField.LEFT);
        return new GroupPane(){{
            add(x, GroupPaneConstraints.FILL);
            add(y, GroupPaneConstraints.FILL);
        }};
    }

    public void addValueChangedListener(ParameterChangedListener listener) {
        x.addChangeListener(e -> listener.event(getValue()));
        y.addChangeListener(e -> listener.event(getValue()));
    }

    public void initObjects(Object[][] objects){
        step = (float)objects[0][0];
    }
}
