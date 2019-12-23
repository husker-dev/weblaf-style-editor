package com.husker.editor.app.tools;

import com.alee.laf.spinner.WebSpinner;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class FloatSpinner extends WebSpinner {

    private float step;

    public FloatSpinner(float step) {
        super(new SpinnerNumberModel(0.0, -1000, 1000, step));
        this.step = step;
        setPreferredWidth(20);

        JSpinner.DefaultEditor spinnerEditor = (JSpinner.DefaultEditor)getEditor();
        JTextField textField = spinnerEditor.getTextField();

        textField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                warn();
            }
            public void removeUpdate(DocumentEvent e) {
                warn();
            }
            public void insertUpdate(DocumentEvent e) {
                warn();
            }
            public void warn() {
                SwingUtilities.invokeLater(() -> {
                    if(textField.getText().equals("-0"))
                        textField.setText("0");
                });
            }
        });
    }

    public Float getFloat() {
        int round = (step + "").split("\\.")[1].length();
        float value = Float.parseFloat(getValue().toString());

        value = (float)(Math.round(value * Math.pow(10, round)) / Math.pow(10, round));

        return value;
    }

}