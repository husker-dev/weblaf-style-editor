package com.husker.editor.app.project;

import com.alee.laf.button.WebButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.grouping.GroupPane;
import com.alee.laf.grouping.GroupPaneConstraints;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.husker.editor.app.Main;
import com.husker.editor.app.events.*;
import com.husker.editor.app.listeners.contants.ConstantsAdapter;
import com.husker.editor.app.listeners.editable_object.EditableObjectAdapter;
import com.husker.editor.app.listeners.parameter.*;
import com.husker.editor.app.tools.VisibleUtils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.function.Consumer;

public abstract class Parameter{

    // Static
    private static ArrayList<ParameterListener> listeners = new ArrayList<>();
    private static ImageIcon reset_img, reset_disabled_img;

    public static void addParameterListener(ParameterListener listener){
        listeners.add(listener);
    }
    static {
        reset_img = new ImageIcon("bin/reset.png");
        reset_disabled_img = new ImageIcon("bin/reset_disabled.png");

        EditableObject.addEditableObjectListener(new EditableObjectAdapter() {
            public void variableChanged(VariableChangedEvent event) {
                // Is editable object
                if(VisibleUtils.isEditableObject(event.getObject()))
                    // For every EditableObject's parameter
                    for(Parameter parameter : event.getObject().getParameters())
                        // If variable are equals
                        if(parameter.getBoundVariable().equals(event.getVariable()))
                            parameter.updateValue();
            }
            public void constantChanged(ConstantChangedEvent event) {
                // Is editable object
                if(VisibleUtils.isEditableObject(event.getObject())) {
                    // For every EditableObject's parameter
                    for (Parameter parameter : event.getObject().getParameters()) {
                        // If variable are equals
                        if (parameter.getBoundVariable().equals(event.getVariable())) {
                            parameter.updateConstants();
                            parameter.updateValue();
                        }
                    }
                }
            }
            public void selectedChanged(SelectedChangedEvent event) {
                // Is editable object
                if(VisibleUtils.isEditableObject(event.getObject()))
                    // For every EditableObject's parameter
                    for(Parameter parameter : event.getObject().getParameters())
                        parameter.applyObject(event.getObject());
            }
        });
        Constants.addConstantListener(new ConstantsAdapter() {
            public void newConstant(NewConstantEvent event) {
                update(event.getConstantType());
            }
            public void removed(ConstantRemovedEvent event) {
                update(event.getConstantType());
            }
            void update(Class<? extends Constant> type){
                EditableObject object = Project.getCurrentProject().getSelectedObject();

                if(VisibleUtils.isEditableObject(object)) {
                    // For every EditableObject's parameter
                    for (Parameter parameter : object.getParameters()) {
                        // If variable are equals
                        if (parameter.getBoundVariable().getConstantType() == type) {
                            parameter.updateConstants();
                            parameter.updateValue();
                        }
                    }
                }
            }
        });
    }

    // UI
    private WebPanel ui_content;

    private WebLabel ui_name;
    private WebComboBox ui_constants;
    private WebButton ui_reset;

    // Variables
    private String name;

    private StaticVariable variable;
    private EditableObject editable_object;
    private String group;
    private Class<? extends Constant> constant_type;
    private boolean visible = true;

    private boolean constants_event_enabled = true;

    public Parameter(String name, String group, Object... objects){
        this(name, null, group, objects);
    }
    public Parameter(String name, Class<? extends Constant> constant_type, String group, Object... objects){
        initObjects(objects);

        this.name = name;
        this.group = group;
        this.constant_type = constant_type;

        createUI();

        addValueChangedListener(value -> {
            if(!VisibleUtils.onEditableObject())
                return;
            if(variable != null && editable_object.getVariable(variable).getConstant().isEmpty()) {
                editable_object.setCustomValue(variable, value);
                if (editable_object != null && editable_object.isImplemented(variable))
                    ui_reset.setEnabled(!editable_object.getVariable(variable).getDefaultValue().equals(getValue()));
            }
        });
        Main.event(Parameter.class, listeners, listener -> listener.parametersChanged(new ParametersChangedEvent()));
    }

    public void applyObject(EditableObject object){
        editable_object = object;

        if(editable_object.getVariable(variable) != null)
            apply(editable_object.getVariableValue(variable));
        ui_reset.setEnabled(!editable_object.getVariable(variable).getDefaultValue().equals(getValue()));

        if(variable != null)
            Main.event(Parameter.class, listeners, listener -> listener.objectApplying(new ParameterApplyingEvent(editable_object.getProject(), this, editable_object)));

        if(constant_type != null)
            updateConstants();
        updateValue();
    }
    public void updateConstants(){
        constants_event_enabled = false;

        ui_constants.removeAllItems();
        ui_constants.addItem("Custom");
        for (String tag : editable_object.getProject().Constants.getConstants(constant_type))
            ui_constants.addItem(tag);
        ui_constants.setSelectedItem(editable_object.getVariable(variable).getConstant());

        constants_event_enabled = true;
    }
    public void updateValue(){
        try {
            Variable variable = editable_object.getVariable(this.variable);
            if (variable.getConstant().isEmpty()) {
                setEnabled(true);
                String old = getValue();
                setValue(variable.getValue());
                Main.event(Parameter.class, listeners, listener -> listener.valueChanged(new ParameterValueChangedEvent(this, old, getValue())));
            } else {
                setEnabled(false);
                String old = getValue();
                setValue(variable.getConstantValue());
                Main.event(Parameter.class, listeners, listener -> listener.valueChanged(new ParameterValueChangedEvent(this, old, getValue())));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


    public void bindVariable(StaticVariable variable){
        this.variable = variable;
    }
    public StaticVariable getBoundVariable(){
        return variable;
    }

    public void setVisible(boolean visible){
        this.visible = visible;
        Main.event(Parameter.class, listeners, listener -> listener.visibleChanged(new ParameterVisibleChangedEvent(this, visible)));
    }
    public boolean isVisible(){
        return visible;
    }

    public WebPanel getContent(){
        return ui_content;
    }

    public void setGroup(String group){
        this.group = group;
    }
    public String getGroup(){
        return group;
    }

    public abstract Component initComponent();
    protected abstract void addValueChangedListener(Consumer<String> consumer);
    protected void initObjects(Object[] objects){}
    protected abstract void apply(String value);
    public abstract String getValue();
    public abstract void setEnabled(boolean enabled);

    public void setValue(String value){
        if(!getValue().equals(value))
            apply(value);
    }

    public void setIcon(Icon icon){
        ui_name.setIcon(icon);
    }
    public Icon getIcon(){
        return ui_name.getIcon();
    }

    public Class<? extends Constant> getConstantType(){
        return constant_type;
    }

    public void onVisibleChanged(final Consumer<Boolean> visibleChanged){
        addParameterListener(new ParameterAdapter() {
            public void visibleChanged(ParameterVisibleChangedEvent event) {
                if(event.getParameter() == Parameter.this)
                    visibleChanged.accept(event.isVisible());
            }
        });
    }
    public void onValueChanged(final Consumer<String> valueChanged){
        addParameterListener(new ParameterAdapter() {
            public void valueChanged(ParameterValueChangedEvent event) {
                if(event.getParameter() == Parameter.this && event.getParameter().isVisible())
                    valueChanged.accept(event.getNewValue());
            }
        });
    }
    public void onApplying(final Consumer<EditableObject> applying){
        addParameterListener(new ParameterAdapter() {
            public void objectApplying(ParameterApplyingEvent event) {
                if(event.getParameter() == Parameter.this)
                    applying.accept(event.getObject());
            }
        });
    }

    private void createUI(){
        ui_content = new WebPanel();
        ui_content.setPreferredHeight(25);
        ui_content.setPadding(0, 0, 0, 5);

        this.ui_name = new WebLabel(name + ":");

        if(constant_type != null) {
            this.ui_constants = new WebComboBox() {{
                setPreferredWidth(20);
                setWidePopup(true);

                addItemListener(e -> {
                    if(!constants_event_enabled || getSelectedItem() == null)
                        return;

                    if (getSelectedItem() != null) {
                        if(getSelectedItem().toString().equals("Custom"))
                            editable_object.setConstant(variable, "");
                        else
                            editable_object.setConstant(variable, getSelectedItem().toString());
                    }
                });
            }};
        }

        ui_reset = new WebButton(){{
            setPreferredSize(26, 20);
            setIcon(reset_img);
            setDisabledIcon(reset_disabled_img);
            addActionListener(e -> {
                if(constant_type != null)
                    ui_constants.setSelectedIndex(0);
                apply(editable_object.getVariable(variable).getDefaultValue());
            });
        }};

        ui_content.setLayout(new GridLayout(1, 3));

        ui_content.add(this.ui_name);
        ui_content.add(this.initComponent());
        if(constant_type != null) {
            ui_content.add(new GroupPane() {{
                add(ui_reset);
                add(ui_constants, GroupPaneConstraints.FILL);
            }});
        }else
            ui_content.add(ui_reset);
    }
}
