package com.husker.editor.app;



import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.husker.editor.app.project.Project;

import java.awt.*;

public abstract class Parameter{
    IApplyParameter applyParameter;

    WebPanel panel;

    WebLabel name;
    Component component;
    WebComboBox constants;

    String current_const = "";

    String last_value = "";
    boolean last_value_saved = false;

    String component_value = "";
    String default_value = "";

    public Parameter(String name, String component_value, Constants.ConstType constType, String default_value){
        panel = new WebPanel();
        panel.setPreferredHeight(25);
        panel.setPadding(0, 0, 0, 5);

        this.component_value = component_value;
        this.default_value = default_value;

        this.name = new WebLabel(name + ":");
        this.constants = new WebComboBox(){{
            setPreferredWidth(20);
            addItem("Custom");
            setWidePopup(true);

            Constants.addListener((event, objects) -> {
                removeAllItems();
                addItem("Custom");
                for(String tag : Constants.getConstants(constType))
                    addItem(tag);
            });


            addItemListener(e -> {
                if(getSelectedItem() == null)
                    return;

                if(current_const.equals("") || !current_const.equals(getSelectedItem()))
                    current_const = getSelectedItem().toString();
                else if(current_const.equals(getSelectedItem()))
                    return;

                if(getSelectedItem() != null) {
                    if (getSelectedItem().equals("Custom")) {
                        Parameter.this.setEnabled(true);
                        Parameter.this.setValue(last_value);
                        last_value_saved = false;
                    }else {
                        if(!last_value_saved){
                            last_value_saved = true;
                            last_value = getValue();
                        }
                        Parameter.this.setEnabled(false);
                        Parameter.this.setValue(Constants.getConstant(constType, getSelectedItem().toString()));
                    }
                }
            });
        }};

        panel.setLayout(new GridLayout(1, 3));

        this.component = initComponent();

        panel.add(this.name);
        panel.add(this.component);
        panel.add(this.constants);

        addValueChangedListener((text, component) -> {
            if(Project.getCurrentProject() == null || Project.getCurrentProject().Components.getSelectedComponent() == null)
                return;
            if(!component_value.isEmpty())
                component.setValue(component_value, text);
        });
    }

    public void apply(StyleComponent component){
        if(!component_value.isEmpty())
            if(applyParameter != null)
                applyParameter.event(component);

        if(component.getValue(component_value) == null) {
            setValue(default_value);
            component.setValue(component_value, default_value);
        }else
            setValue(component.getValue(component_value));
    }

    public void setApplyParameter(IApplyParameter applyParameter){
        this.applyParameter = applyParameter;
    }

    public void setComponentValue(String value){
        component_value = value;
    }
    public String getComponentValue(){
        return component_value;
    }

    public WebPanel getPanel(){
        return panel;
    }

    public abstract void setValue(String value);
    public abstract String getValue();

    public abstract void setEnabled(boolean enabled);
    public abstract boolean isEnabled();

    public abstract Component initComponent();

    public abstract void addValueChangedListener(ParameterChanged listener);

    public interface ParameterChanged {
        void event(String text, StyleComponent component);
    }

    public interface IApplyParameter {
        void event(StyleComponent component);
    }
}
