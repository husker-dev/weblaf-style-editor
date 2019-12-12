package com.husker.editor.app.window.panels.parameters;


import com.alee.extended.collapsible.WebCollapsiblePane;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.separator.WebSeparator;
import com.alee.managers.style.StyleId;
import com.husker.editor.app.project.Components;
import com.husker.editor.app.project.Parameter;
import com.husker.editor.app.project.Project;
import com.husker.editor.app.project.StyleComponent;
import com.husker.editor.app.window.tools.MovableComponentList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.husker.editor.app.project.listeners.component.ComponentEvent.Type.*;


public class ParameterPanel extends WebPanel {

    private WebScrollPane scroll;
    private MovableComponentList list;
    private StyleComponent selected_component;
    private HashMap<String, WebCollapsiblePane> groups = new HashMap<>();
    private HashMap<Parameter, WebSeparator> separators = new HashMap<>();

    public ParameterPanel(){
        setPreferredWidth(230);

        add(scroll = new WebScrollPane(list = new MovableComponentList(){{
            setShowReorderGrippers(false);
            setReorderingAllowed(false);
            setPadding(5, 0, 5, 0);

            Parameter.addVisibleChangedListener(() -> {
                apply(selected_component, false);
            });

            Components.addListener(e -> {
                if(e.getType().oneOf(Selected_Changed)) {
                    selected_component = (StyleComponent)e.getObjects()[0];
                    apply(selected_component, true);
                }
            });

        }}){{
            getVerticalScrollBar().setUnitIncrement(16);
            setStyleId(StyleId.scrollpaneUndecorated);
        }});

        Components.addListener(e -> {
            scroll.setVisible(!(Project.getCurrentProject() == null || Project.getCurrentProject().Components.getSelectedComponent() == null));
        });
        Project.addListener(e -> {
            scroll.setVisible(!(Project.getCurrentProject() == null || Project.getCurrentProject().Components.getSelectedComponent() == null));
        });

        prepareParameters();
    }

    public void prepareParameters(){
        ArrayList<String> groups = new ArrayList<>();
        ArrayList<Parameter> ungrouped = new ArrayList<>();

        for (Parameter parameter : StyleComponent.parameters) {
            if(parameter == null || !parameter.isVisible())
                continue;
            if(parameter.getGroup() == null)
                ungrouped.add(parameter);
            else if(!groups.contains(parameter.getGroup()))
                groups.add(parameter.getGroup());
        }

        for(Parameter parameter : ungrouped){
            list.addElement(parameter.getPanel());
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
                for(Parameter parameter : StyleComponent.parameters){
                    if(parameter.getGroup() != null && parameter.getGroup().equals(group) && parameter.isVisible()){
                        if(!first) {
                            WebSeparator separator = WebSeparator.createHorizontal();
                            addElement(separator);
                            separators.put(parameter, separator);
                        }else
                            first = false;

                        addElement(parameter.getPanel());
                    }
                }
            }});
            list.addElement(group_panel);
        }
        list.updateUI();
    }

    public void apply(StyleComponent component, boolean apply){
        for(Parameter parameter : StyleComponent.parameters){
            if(component != null && component.isParameterImplemented(parameter.getComponentVariable())) {
                if(apply)
                    parameter.apply(component);
                parameter.getPanel().setVisible(parameter.isVisible());
                if(separators.containsKey(parameter))
                    separators.get(parameter).setVisible(parameter.isVisible());
            }else {
                parameter.getPanel().setVisible(false);
                if(separators.containsKey(parameter))
                    separators.get(parameter).setVisible(false);
            }
        }

        for(Map.Entry<String, WebCollapsiblePane> entry : groups.entrySet()){
            boolean visible = false;
            for(Parameter parameter : StyleComponent.parameters)
                if(parameter.getGroup() != null && parameter.getGroup().equals(entry.getKey()) && parameter.getPanel().isVisible())
                    visible = true;
            entry.getValue().setVisible(visible);
        }
    }
}
