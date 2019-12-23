package com.husker.editor.app.project;

import com.alee.laf.button.WebButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.grouping.GroupPane;
import com.alee.laf.grouping.GroupPaneConstraints;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.husker.editor.app.project.listeners.parameter.ParameterActionListener;
import com.husker.editor.app.project.listeners.parameter.ParameterApplyListener;
import com.husker.editor.app.project.listeners.parameter.ParameterChangedListener;
import com.husker.editor.app.project.listeners.parameter.ParameterVisibleListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract class Parameter{

    private ArrayList<ParameterApplyListener> apply_listener = new ArrayList<>();
    private ArrayList<ParameterActionListener> action_listener = new ArrayList<>();

    private WebPanel panel;

    private WebLabel name;
    private Component component;
    private WebComboBox constants;
    private WebButton reset;

    private String current_const = "";
    private String last_value = "";
    private boolean last_value_saved = false;

    private String variable_name = "";
    private AbstractEditableObject current_parameter_receiver;
    private String group;
    private Constants.ConstType const_type;

    private boolean visible = true;
    private static ArrayList<ParameterVisibleListener> visible_listener = new ArrayList<>();

    private static ImageIcon reset_img, reset_disabled_img;

    static {
        reset_img = new ImageIcon("bin/reset.png");
        reset_disabled_img = new ImageIcon("bin/reset_disabled.png");
    }

    public Parameter(String name, Constants.ConstType constType, String group, Object[]... objects){
        initObjects(objects);

        panel = new WebPanel();
        panel.setPreferredHeight(25);
        panel.setPadding(0, 0, 0, 5);

        this.group = group;
        this.const_type = constType;
        this.name = new WebLabel(name + ":");

        if(constType != null) {
            this.constants = new WebComboBox() {{
                setPreferredWidth(20);
                addItem("Custom");
                setWidePopup(true);

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
                            Parameter.this.setValue(Project.getCurrentProject().Constants.getConstant(constType, getSelectedItem().toString()));
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
                setValue(current_parameter_receiver.getVariable(variable_name).getDefaultValue());
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
            if(!variable_name.isEmpty())
                current_parameter_receiver.setCustomValue(variable_name, text);

            if(current_parameter_receiver != null && current_parameter_receiver.isImplemented(variable_name))
                reset.setEnabled(!current_parameter_receiver.getVariable(variable_name).getDefaultValue().equals(getValue()));
        });

        if(constType != null) {
            Constants.addListener(event -> updateConstants());
            Project.addListener(event -> updateConstants());
            updateConstants();
        }
    }

    public void apply(StyleComponent component){
        current_parameter_receiver = component;

        if(component.getVariable(variable_name) != null)
            setValue(component.getVariableValue(variable_name));
        reset.setEnabled(!current_parameter_receiver.getVariable(variable_name).getDefaultValue().equals(getValue()));

        if(!variable_name.isEmpty()){
            for(ParameterApplyListener listener : apply_listener)
                listener.event(component);
        }
    }

    public void addOnApplyListener(ParameterApplyListener applyParameter){
        apply_listener.add(applyParameter);
    }

    public void setVariableName(String value){
        variable_name = value;
    }
    public String getVariableName(){
        return variable_name;
    }

    public void setVisible(boolean visible){
        this.visible = visible;
        for(ParameterVisibleListener listener : visible_listener)
            listener.event();
    }
    public boolean isVisible(){
        return visible;
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

    public void setIcon(ImageIcon icon){
        name.setIcon(icon);
    }

    public abstract Component initComponent();

    public abstract void addValueChangedListener(ParameterChangedListener listener);

    public static void addVisibleChangedListener(ParameterVisibleListener listener){
        visible_listener.add(listener);
    }

    public void action(){
        System.out.println("EVENT Parameter");
        for(ParameterActionListener listener : action_listener)
            listener.event();
    }

    public void addActionListener(ParameterActionListener listener){
        action_listener.add(listener);
        addValueChangedListener(e -> listener.event());
        addOnApplyListener(e -> listener.event());
    }

    private void updateConstants(){
        constants.removeAllItems();
        constants.addItem("Custom");
        for (String tag : Project.getCurrentProject().Constants.getConstants(const_type))
            constants.addItem(tag);
    }
}
