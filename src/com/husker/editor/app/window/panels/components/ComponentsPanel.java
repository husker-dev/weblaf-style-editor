package com.husker.editor.app.window.panels.components;

import com.alee.extended.panel.ComponentReorderListener;
import com.alee.extended.statusbar.WebStatusBar;
import com.alee.laf.button.WebButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.grouping.GroupPane;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.toolbar.WebToolBar;
import com.alee.managers.style.StyleId;
import com.husker.editor.app.project.Components;
import com.husker.editor.app.project.Project;
import com.husker.editor.app.project.StyleComponent;
import com.husker.editor.app.window.components.MovableComponentList;


import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.husker.editor.app.project.Components.ComponentEvent.*;
import static com.husker.editor.app.project.Project.ProjectEvent.Current_Project_Changed;

public class ComponentsPanel extends WebPanel {

    WebComboBox combo;
    WebButton add;
    MovableComponentList list;
    WebScrollPane scroll;
    WebStatusBar statusBar;

    public HashMap<StyleComponent, ComponentPanel> components = new HashMap<>();

    public ComponentsPanel(){
        setPreferredWidth(250);

        combo = new WebComboBox(){{
            ArrayList<String> components = new ArrayList<>();
            for(Map.Entry<String, StyleComponent> entry : StyleComponent.components.entrySet())
                components.add(entry.getKey());
            Collections.sort(components);
            for(String component : components)
                addItem(component);
        }};
        add = new WebButton("+"){{
            addActionListener(e -> {
                if(Project.getCurrentProject() != null)
                    Project.getCurrentProject().Components.addComponent(StyleComponent.components.get(combo.getSelectedItem()).clone(Project.getCurrentProject()));
            });
        }};
        list = new MovableComponentList(){{
                Project.addListener((event, objects) -> {
                    if(event.oneOf(Current_Project_Changed) && Project.getCurrentProject() != null)
                        resetComponents(Project.getCurrentProject().Components.getComponents());
                });
                Components.addListener((event, objects) -> {
                    if(event.oneOf(New))
                        addComponent((StyleComponent) objects[0]);
                    if(event.oneOf(Removed))
                        removeComponent((StyleComponent) objects[0]);

                });
                addComponentReorderListener((component, i, i1) -> {
                    Project.getCurrentProject().Components.moveComponent(i, i1);
                });
            }
            void resetComponents(ArrayList<StyleComponent> components){
                ComponentsPanel.this.components.clear();
                while(getElementCount() != 0){
                    for(int i = 0; i < getElementCount(); i++)
                        this.removeElement(getElement(i));
                }
                for(StyleComponent component : components)
                    addComponent(component);
                updateUI();
            }
            void addComponent(StyleComponent component){
                ComponentPanel panel = new ComponentPanel(component);
                ComponentsPanel.this.components.put(component, panel);
                addElement(panel);
                updateUI();
            }
            void removeComponent(StyleComponent component) {
                removeElement(ComponentsPanel.this.components.get(component));
                ComponentsPanel.this.components.remove(component);
                updateUI();
            }
        };
        scroll = new WebScrollPane(list){{
            getVerticalScrollBar().setUnitIncrement(16);
            setStyleId(StyleId.scrollpaneUndecorated);
        }};

        statusBar = new WebStatusBar(){{
            WebLabel count = new WebLabel("Components: -1");
            add(count);
            Project.addListener((event, objects) -> {
                if(Project.getCurrentProject() != null)
                    count.setText("Components: " + Project.getCurrentProject().Components.getComponents().size());
            });
            Components.addListener((event, objects) -> {
                count.setText("Components: " + Project.getCurrentProject().Components.getComponents().size());
            });

        }};

        setLayout(new BorderLayout());
        add(new WebToolBar(){{
            add(new GroupPane(combo, add));
        }}, BorderLayout.NORTH);
        add(scroll);
        add(statusBar, BorderLayout.SOUTH);


        Project.addListener((event, objects) -> setActive(Project.getCurrentProject() != null));
        Components.addListener((event, objects) -> {
            if(event.oneOf(Components.ComponentEvent.Selected_Changed))
                for(Map.Entry<StyleComponent, ComponentPanel> entry : components.entrySet())
                    entry.getValue().setSelected(Project.getCurrentProject().Components.getSelectedComponent() == entry.getKey());
            if(event.oneOf(Style_Changed))
                components.get(objects[0]).onStyleUpdate();

        });
        setActive(false);
    }

    public void setActive(boolean active){
        combo.setEnabled(active);
        add.setEnabled(active);
        scroll.setVisible(active);
        statusBar.setVisible(active);
    }
}





