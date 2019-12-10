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


public class ParameterPanel extends WebPanel {

    private WebScrollPane scroll;
    private MovableComponentList list;
    private StyleComponent selected_component;

    public ParameterPanel(){
        setPreferredWidth(230);

        Components.addListener((event, objects) -> {
            scroll.setVisible(!(Project.getCurrentProject() == null || Project.getCurrentProject().Components.getSelectedComponent() == null));
        });
        Project.addListener((event, objects) -> {
            scroll.setVisible(!(Project.getCurrentProject() == null || Project.getCurrentProject().Components.getSelectedComponent() == null));
        });

        add(scroll = new WebScrollPane(list = new MovableComponentList(){{
            setShowReorderGrippers(false);
            setReorderingAllowed(false);
            setPadding(5, 0, 5, 0);

            Parameter.addVisibleChangedListener(() -> {
                showAll(selected_component, false);
            });

            Components.addListener((event, objects) -> {
                if(event.oneOf(Components.ComponentEvent.Selected_Changed)) {
                    selected_component = (StyleComponent)objects[0];
                    showAll(selected_component, true);
                }
            });

        }}){{
            getVerticalScrollBar().setUnitIncrement(16);
            setVisible(false);
            setStyleId(StyleId.scrollpaneUndecorated);
        }});
    }

    public void showAll(StyleComponent component, boolean apply){

        // Clear panel
        while(list.getElementCount() != 0){
            for(int i = 0; i < list.getElementCount(); i++)
                list.removeElement(list.getElement(i));
        }
        if(component == null)
            return;

        ArrayList<String> groups = new ArrayList<>();
        ArrayList<Parameter> ungrouped = new ArrayList<>();

        for (Parameter parameter : component.getParameters()) {
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
            group_panel.setFocusable(false);
            group_panel.setMargin(5, 0, 0, 5);
            group_panel.setTitle(group);
            group_panel.setContent(new MovableComponentList(){{
                setPadding(5, 0, 5, 0);
                setShowReorderGrippers(false);
                setReorderingAllowed(false);

                boolean first = true;
                for(Parameter parameter : component.getParameters()){
                    if(parameter.getGroup() != null && parameter.getGroup().equals(group) && parameter.isVisible()){
                        if(!first)
                            addElement(WebSeparator.createHorizontal());
                        else
                            first = false;

                        addElement(parameter.getPanel());
                    }
                }
            }});
            list.addElement(group_panel);
        }

        for(Parameter parameter : component.getParameters())
            if(apply)
                parameter.apply(component);

        list.updateUI();


    }
}
