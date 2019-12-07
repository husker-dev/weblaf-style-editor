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
import com.husker.editor.app.window.components.MovableComponentList;

import java.util.ArrayList;


public class ParameterPanel extends WebPanel {
    WebScrollPane scroll;

    public ParameterPanel(){
        setPreferredWidth(230);

        Components.addListener((event, objects) -> {
            scroll.setVisible(!(Project.getCurrentProject() == null || Project.getCurrentProject().Components.getSelectedComponent() == null));
        });
        Project.addListener((event, objects) -> {
            scroll.setVisible(!(Project.getCurrentProject() == null || Project.getCurrentProject().Components.getSelectedComponent() == null));
        });

        add(scroll = new WebScrollPane(new MovableComponentList(){{
            setShowReorderGrippers(false);
            setReorderingAllowed(false);
            setPadding(5, 0, 5, 0);

            Components.addListener((event, objects) -> {
                if(event.oneOf(Components.ComponentEvent.Selected_Changed)) {

                    StyleComponent component = (StyleComponent)objects[0];
                    // Clear panel
                    while(getElementCount() != 0){
                        for(int i = 0; i < getElementCount(); i++)
                            this.removeElement(getElement(i));
                    }
                    if(component == null)
                        return;

                    ArrayList<String> groups = new ArrayList<>();
                    ArrayList<Parameter> ungrouped = new ArrayList<>();

                    for (Parameter parameter : component.getParameters()) {
                        if(parameter.getGroup() == null)
                            ungrouped.add(parameter);
                        else if(!groups.contains(parameter.getGroup()))
                            groups.add(parameter.getGroup());
                    }

                    for(Parameter parameter : ungrouped){
                        parameter.apply(component);
                        addElement(parameter.getPanel());
                        addElement(WebSeparator.createHorizontal());
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
                                if(parameter.getGroup() != null && parameter.getGroup().equals(group)){
                                    parameter.apply(component);
                                    if(!first)
                                        addElement(WebSeparator.createHorizontal());
                                    else
                                        first = false;

                                    addElement(parameter.getPanel());
                                }
                            }
                        }});
                        addElement(group_panel);
                    }

                    updateUI();
                }
            });

        }}){{
            getVerticalScrollBar().setUnitIncrement(16);
            setVisible(false);
            setStyleId(StyleId.scrollpaneUndecorated);
        }});
    }
}
