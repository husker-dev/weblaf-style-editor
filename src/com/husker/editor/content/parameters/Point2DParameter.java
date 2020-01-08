package com.husker.editor.content.parameters;

import com.alee.laf.grouping.GroupPane;
import com.alee.laf.grouping.GroupPaneConstraints;
import com.husker.editor.core.Parameter;
import com.husker.editor.core.tools.DoubleSpinner;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class Point2DParameter extends Parameter {

    private DoubleSpinner x;
    private DoubleSpinner y;
    private double step;

    public Point2DParameter(String name) {
        this(name, null, 0.1);
    }
    public Point2DParameter(String name, String group) {
        this(name, group, 0.1);
    }
    public Point2DParameter(String name, double step) {
        this(name, null, step);
    }
    public Point2DParameter(String name, String group, double step) {
        super(name, group, step);
    }

    public void apply(String value) {
        x.setValue(Double.parseDouble(value.split(",")[0]));
        y.setValue(Double.parseDouble(value.split(",")[1]));
    }

    public String getValue() {
        return x.getDouble() + "," + y.getDouble();
    }

    public void setEnabled(boolean enabled) {
        x.setEnabled(enabled);
        y.setEnabled(enabled);
    }

    public Component initComponent() {
        x = new DoubleSpinner(step);
        y = new DoubleSpinner(step);

        JSpinner.DefaultEditor spinnerEditorX = (JSpinner.DefaultEditor)x.getEditor();
        spinnerEditorX.getTextField().setHorizontalAlignment(JTextField.LEFT);

        JSpinner.DefaultEditor spinnerEditorY = (JSpinner.DefaultEditor)y.getEditor();
        spinnerEditorY.getTextField().setHorizontalAlignment(JTextField.LEFT);
        return new GroupPane(){{
            add(x, GroupPaneConstraints.FILL);
            add(y, GroupPaneConstraints.FILL);
        }};
    }

    public void addValueChangedListener(Consumer<String> consumer) {
        x.addChangeListener(e -> consumer.accept(getValue()));
        y.addChangeListener(e -> consumer.accept(getValue()));
    }

    public void initObjects(Object[] objects){
        step = (double)objects[0];
    }
}
