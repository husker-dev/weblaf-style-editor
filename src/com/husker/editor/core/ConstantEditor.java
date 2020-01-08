package com.husker.editor.core;

import java.awt.*;
import java.util.function.Consumer;

public abstract class ConstantEditor {

    private Component component;

    protected abstract Component initComponent();
    protected abstract void apply(String value);
    protected abstract String getValue();
    public abstract void onChanged(Consumer<String> changed);

    public ConstantEditor(){
        component = initComponent();
    }

    public Component getComponent(){
        return component;
    }
    public void setValue(String value){
        if(!getValue().equals(value))
            apply(value);
    }

}
