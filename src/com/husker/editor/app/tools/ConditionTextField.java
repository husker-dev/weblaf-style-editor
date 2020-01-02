package com.husker.editor.app.tools;

import com.alee.laf.text.WebTextField;
import com.alee.managers.style.StyleId;

import javax.swing.*;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.function.Predicate;

public class ConditionTextField extends WebTextField {

    private Runnable apply = null;
    private Predicate<String> predicate = null;
    private String last_text = "";

    public ConditionTextField () {
        this(StyleId.auto);
    }
    public ConditionTextField (final String text) {
        this(StyleId.auto, text);
    }
    public ConditionTextField (final int columns) {
        this(StyleId.auto, columns);
    }
    public ConditionTextField (final String text, final int columns){
        this(StyleId.auto, text, columns);
    }
    public ConditionTextField (final Document doc, final String text, final int columns){
        this(StyleId.auto, doc, text, columns);
    }
    public ConditionTextField (final StyleId id){
        this(id, null, null, 0);
    }
    public ConditionTextField (final StyleId id, final String text){
        this(id, null, text, 0);
    }
    public ConditionTextField (final StyleId id, final int columns){
        this(id, null, null, columns);
    }
    public ConditionTextField (final StyleId id, final String text, final int columns){
        this(id, null, text, columns);
    }
    public ConditionTextField (final StyleId id, final Document doc, final String text, final int columns){
        super(doc, text, columns);
        setStyleId(id);

        if(text != null)
            last_text = text;
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (predicate == null || predicate.test(getText())) {
                        if(!last_text.equals(getText()) && apply != null)
                            SwingUtilities.invokeLater(apply::run);
                        last_text = getText();
                    }else
                        Toolkit.getDefaultToolkit().beep();
                }
            }
        });
        addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                if (predicate == null || predicate.test(getText())) {
                    if(!last_text.equals(getText()) && apply != null)
                        SwingUtilities.invokeLater(apply::run);
                    last_text = getText();
                }else
                    setText(last_text);
            }
        });
    }

    public void setCondition(Predicate<String> predicate){
        this.predicate = predicate;
    }
    public void onApplied(Runnable runnable){
        apply = runnable;
    }

    public void setText(String text){
        super.setText(text);
        last_text = text;
    }
}
