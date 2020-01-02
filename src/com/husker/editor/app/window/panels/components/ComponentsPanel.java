package com.husker.editor.app.window.panels.components;

import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.managers.style.StyleId;
import com.husker.editor.app.events.EditableObjectRemovedEvent;
import com.husker.editor.app.events.NewEditableObjectEvent;
import com.husker.editor.app.events.SelectedChangedEvent;
import com.husker.editor.app.events.VariableChangedEvent;
import com.husker.editor.app.listeners.editable_object.EditableObjectAdapter;
import com.husker.editor.app.project.EditableObject;
import com.husker.editor.app.project.Project;
import com.husker.editor.app.tools.MovableComponentList;


import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ComponentsPanel extends WebPanel {

    private MovableComponentList list;
    private WebScrollPane scroll;

    private HashMap<EditableObject, ComponentPanel> components = new HashMap<>();

    public ComponentsPanel(){
        setPreferredWidth(250);

        list = new MovableComponentList(){
            {
                EditableObject.addEditableObjectListener(new EditableObjectAdapter() {
                    public void newObject(NewEditableObjectEvent event) {
                        addComponent(event.getObject());
                    }
                    public void objectRemoved(EditableObjectRemovedEvent event) {
                        removeComponent(event.getObject());
                    }
                });
                Project.addListener(e -> {
                    resetComponents(Project.getCurrentProject().Components.getComponents());
                });
                addComponentReorderListener((component, i, i1) -> {
                    Project.getCurrentProject().Components.moveComponent(i, i1);
                });
            }
            void resetComponents(ArrayList<? extends EditableObject> components){
                ComponentsPanel.this.components.clear();
                while(getElementCount() != 0){
                    for(int i = 0; i < getElementCount(); i++)
                        this.removeElement(getElement(i));
                }
                for(EditableObject component : components)
                    addComponent(component);
                updateUI();
            }
            void addComponent(EditableObject component){
                ComponentPanel panel = new ComponentPanel(component);
                ComponentsPanel.this.components.put(component, panel);
                addElement(panel);
                updateUI();
            }
            void removeComponent(EditableObject component) {
                removeElement(ComponentsPanel.this.components.get(component));
                ComponentsPanel.this.components.remove(component);
                updateUI();
            }
        };
        scroll = new WebScrollPane(StyleId.scrollpaneUndecorated, list){{
            getVerticalScrollBar().setUnitIncrement(16);
        }};

        setLayout(new BorderLayout());
        add(scroll);

        EditableObject.addEditableObjectListener(new EditableObjectAdapter() {
            public void variableChanged(VariableChangedEvent event) {
                components.get(event.getObject()).onStyleUpdate();
            }
        });
        Project.addListener(e -> {
            setActive(Project.getCurrentProject() != null);
        });
    }

    public void setActive(boolean active){
        scroll.setVisible(active);
    }
}





