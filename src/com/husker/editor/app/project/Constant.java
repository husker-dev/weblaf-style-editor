package com.husker.editor.app.project;

import com.husker.editor.app.constants.editors.TextEditor;
import com.husker.editor.app.tools.Resources;

import javax.swing.*;

public class Constant {

    private String default_value, title;
    private Icon icon;
    private ConstantEditor editor;

    protected Constant(String default_value, String title){
        this(default_value, title, null, null);
    }
    protected Constant(String default_value, String title, ConstantEditor editor){
        this(default_value, title, editor, null);
    }
    protected Constant(String default_value, String title, Icon icon){
        this(default_value, title, null, icon);
    }
    protected Constant(String default_value, String title, ConstantEditor editor, Icon icon){
        this.default_value = default_value;
        this.title = title;
        this.icon = icon != null ? icon : Resources.getImageIcon("constants/default.png");
        this.editor = editor != null ? editor : new TextEditor();
    }

    public String getDefaultValue(){
        return default_value;
    }
    public String getTitle(){
        return title;
    }
    public Icon getIcon(){
        return icon;
    }
    public ConstantEditor getEditor(){
        return editor;
    }
}
