package com.husker.editor.app.window.panels.parameters;


import com.alee.extended.collapsible.WebCollapsiblePane;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.separator.WebSeparator;
import com.alee.managers.style.StyleId;
import com.husker.editor.app.events.ParameterVisibleChangedEvent;
import com.husker.editor.app.events.ParametersChangedEvent;
import com.husker.editor.app.events.SelectedChangedEvent;
import com.husker.editor.app.listeners.editable_object.EditableObjectAdapter;
import com.husker.editor.app.listeners.parameter.ParameterAdapter;
import com.husker.editor.app.project.*;
import com.husker.editor.app.tools.MovableComponentList;
import com.husker.editor.app.tools.VisibleUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ParameterPanel extends WebPanel {

    private WebScrollPane scroll;
    private MovableComponentList list;
    private EditableObject editable_object;
    private HashMap<String, WebCollapsiblePane> groups = new HashMap<>();
    private HashMap<Parameter, WebSeparator> separators = new HashMap<>();

    public ParameterPanel(){
        setPreferredWidth(230);

        add(scroll = new WebScrollPane(StyleId.scrollpaneUndecorated, list = new MovableComponentList(){{
            setShowReorderGrippers(false);
            setReorderingAllowed(false);
            setPadding(5, 0, 5, 0);
        }}){{
            getVerticalScrollBar().setUnitIncrement(16);
        }});

        Parameter.addParameterListener(new ParameterAdapter() {
            public void visibleChanged(ParameterVisibleChangedEvent event) {
                apply(editable_object, false);
            }
        });
        EditableObject.addEditableObjectListener(new EditableObjectAdapter() {
            public void selectedChanged(SelectedChangedEvent event) {
                editable_object = event.getObject();
                apply(editable_object, true);
                scroll.setVisible(VisibleUtils.onEditableObject());
            }
        });

        Project.addListener(e -> scroll.setVisible(VisibleUtils.onEditableObject()));
        Parameter.addParameterListener(new ParameterAdapter() {
            public void parametersChanged(ParametersChangedEvent event) {
                prepareParameters();
            }
        });
        prepareParameters();
    }

    public void prepareParameters(){
        groups.clear();
        separators.clear();
        while(list.getElementCount() > 0)
            for(int i = 0; i < list.getElementCount(); i++)
                list.removeElement(i);

        ArrayList<String> groups = new ArrayList<>();
        ArrayList<Parameter> ungrouped = new ArrayList<>();

        for (Parameter parameter : EditableObject.getStaticParameters()) {
            if(parameter == null || !parameter.isVisible())
                continue;
            if(parameter.getGroup() == null)
                ungrouped.add(parameter);
            else if(!groups.contains(parameter.getGroup()))
                groups.add(parameter.getGroup());
        }

        for(Parameter parameter : ungrouped){
            list.addElement(parameter.getContent());
            list.addElement(WebSeparator.createHorizontal());
        }

        for(String group : groups){
            WebCollapsiblePane group_panel = new WebCollapsiblePane();
            this.groups.put(group, group_panel);
            group_panel.setFocusable(false);
            group_panel.setMargin(5, 0, 0, 5);
            group_panel.setTitle(group);
            group_panel.setContent(new MovableComponentList(){{
                setPadding(5, 0, 5, 0);
                setShowReorderGrippers(false);
                setReorderingAllowed(false);

                boolean first = true;
                for(Parameter parameter : EditableObject.getStaticParameters()){
                    if(parameter.getGroup() != null && parameter.getGroup().equals(group) && parameter.isVisible()){
                        if(!first) {
                            WebSeparator separator = WebSeparator.createHorizontal();
                            addElement(separator);
                            separators.put(parameter, separator);
                        }else
                            first = false;

                        addElement(parameter.getContent());
                    }
                }
            }});
            list.addElement(group_panel);
        }
        list.updateUI();
    }

    public void apply(EditableObject component, boolean apply){
        for(Parameter parameter : StyleComponent.getStaticParameters()){
            if(component != null && component.isImplemented(parameter.getBoundVariable())) {
                if(apply)
                    parameter.applyObject(component);
                parameter.getContent().setVisible(parameter.isVisible());
                if(separators.containsKey(parameter))
                    separators.get(parameter).setVisible(parameter.isVisible());
            }else {
                parameter.getContent().setVisible(false);
                if(separators.containsKey(parameter))
                    separators.get(parameter).setVisible(false);
            }
        }

        for(Map.Entry<String, WebCollapsiblePane> entry : groups.entrySet()){
            boolean visible = false;
            for(Parameter parameter : StyleComponent.getStaticParameters())
                if(parameter.getGroup() != null && parameter.getGroup().equals(entry.getKey()) && parameter.getContent().isVisible())
                    visible = true;
            entry.getValue().setVisible(visible);
        }
    }
}
