package com.husker.editor.app.project;

import com.alee.laf.button.WebButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.grouping.GroupPane;
import com.alee.laf.grouping.GroupPaneConstraints;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;

import javax.swing.*;
import java.awt.*;

public abstract class Parameter{
    IApplyParameter applyParameter;

    WebPanel panel;

    WebLabel name;
    Component component;
    WebComboBox constants;
    WebButton reset;

    String current_const = "";
    String last_value = "";
    boolean last_value_saved = false;

    String component_variable;
    Constants.ConstType constType;

    StyleComponent current_component;

    String group;

    public static ImageIcon reset_img, reset_disabled_img;
    static {
        reset_img = new ImageIcon("bin/reset.png");
        reset_disabled_img = new ImageIcon("bin/reset_disabled.png");
    }

    public Parameter(String name, String component_variable, Constants.ConstType constType, String group, Object[]... objects){
        initObjects(objects);

        panel = new WebPanel();
        panel.setPreferredHeight(25);
        panel.setPadding(0, 0, 0, 5);

        this.component_variable = component_variable;
        this.constType = constType;
        this.group = group;

        this.name = new WebLabel(name + ":");

        if(constType != null) {
            this.constants = new WebComboBox() {{
                setPreferredWidth(20);
                addItem("Custom");
                setWidePopup(true);

                Constants.addListener((event, objects) -> {
                    removeAllItems();
                    addItem("Custom");
                    for (String tag : Constants.getConstants(constType))
                        addItem(tag);
                });


                addItemListener(e -> {
                    if (getSelectedItem() == null)
                        return;

                    if (current_const.equals("") || !current_const.equals(getSelectedItem()))
                        current_const = getSelectedItem().toString();
                    else if (current_const.equals(getSelectedItem()))
                        return;

                    if (getSelectedItem() != null) {
                        if (getSelectedItem().equals("Custom")) {
                            Parameter.this.setEnabled(true);
                            Parameter.this.setValue(last_value);
                            last_value_saved = false;
                        } else {
                            if (!last_value_saved) {
                                last_value_saved = true;
                                last_value = getValue();
                            }
                            Parameter.this.setEnabled(false);
                            Parameter.this.setValue(Constants.getConstant(constType, getSelectedItem().toString()));
                        }
                    }
                });
            }};
        }

        reset = new WebButton(){{
            setPreferredSize(26, 20);
            setIcon(reset_img);
            setDisabledIcon(reset_disabled_img);
            addActionListener(e -> {
                if(constType != null)
                    constants.setSelectedIndex(0);
                setValue(current_component.getVariable(component_variable).getDefaultValue());
            });
        }};

        panel.setLayout(new GridLayout(1, 3));

        this.component = initComponent();

        panel.add(this.name);
        panel.add(this.component);
        if(constType != null) {
            panel.add(new GroupPane() {{
                add(reset);
                add(constants, GroupPaneConstraints.FILL);
            }});
        }else{
            panel.add(reset);
        }

        addValueChangedListener(text -> {
            if(Project.getCurrentProject() == null || Project.getCurrentProject().Components.getSelectedComponent() == null)
                return;
            if(!component_variable.isEmpty())
                current_component.setVariable(component_variable, text);

            reset.setEnabled(!current_component.getVariable(component_variable).getDefaultValue().equals(getValue()));
        });
    }

    public void apply(StyleComponent component){
        current_component = component;
        if(!component_variable.isEmpty())
            if(applyParameter != null)
                applyParameter.event(component);

        if(component.getVariable(component_variable) != null)
            setValue(component.getVariableValue(component_variable));
        reset.setEnabled(!current_component.getVariable(component_variable).getDefaultValue().equals(getValue()));
    }

    public void setApplyParameter(IApplyParameter applyParameter){
        this.applyParameter = applyParameter;
    }

    public void setComponentValue(String value){
        component_variable = value;
    }
    public String getComponentVariable(){
        return component_variable;
    }

    public WebPanel getPanel(){
        return panel;
    }

    public void setGroup(String group){
        this.group = group;
    }
    public String getGroup(){
        return group;
    }

    public void initObjects(Object[][] objects){}

    public abstract void setValue(String value);
    public abstract String getValue();

    public abstract void setEnabled(boolean enabled);
    public abstract boolean isEnabled();

    public abstract Component initComponent();

    public abstract void addValueChangedListener(ParameterChanged listener);

    public interface ParameterChanged {
        void event(String text);
    }

    public interface IApplyParameter {
        void event(StyleComponent component);
    }
}
