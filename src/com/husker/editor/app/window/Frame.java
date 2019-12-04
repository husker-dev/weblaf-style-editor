package com.husker.editor.app.window;


import com.alee.api.data.Orientation;
import com.alee.extended.dock.WebDockableFrame;
import com.alee.extended.dock.WebDockablePane;
import com.alee.extended.dock.data.DockableContentElement;
import com.alee.extended.dock.data.DockableFrameElement;
import com.alee.extended.dock.data.DockableListContainer;
import com.alee.laf.menu.WebMenu;
import com.alee.laf.menu.WebMenuBar;
import com.alee.laf.menu.WebMenuItem;
import com.alee.managers.hotkey.Hotkey;
import com.husker.editor.app.project.Project;
import com.husker.editor.app.window.panels.code.CodePanel;
import com.husker.editor.app.window.panels.components.ComponentsPanel;
import com.husker.editor.app.window.panels.constants.ConstantsPanel;
import com.husker.editor.app.window.panels.parameters.ParameterPanel;
import com.husker.editor.app.window.panels.preview.PreviewPanel;
import com.husker.editor.app.window.panels.projects.ProjectsPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Frame extends JFrame {

    public static Frame context;

    public CustomDockablePanel projects;
    public CustomDockablePanel parameters;
    public CustomDockablePanel components;
    public CustomDockablePanel code;
    public CustomDockablePanel constants;
    public PreviewPanel preview;

    public Frame(){
        context = this;

        setTitle("WebLaF Editor");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1100,700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setJMenuBar(setupMenu(new WebMenuBar()));

        add(new WebDockablePane(){{

            preview = new PreviewPanel();
            projects = new CustomDockablePanel("Projects", new ProjectsPanel());
            parameters = new CustomDockablePanel("Parameters", new ParameterPanel());
            components = new CustomDockablePanel("Components", new ComponentsPanel());
            code = new CustomDockablePanel("Code", new CodePanel());
            constants = new CustomDockablePanel("Constants", new ConstantsPanel());

            setContent(preview);
            addFrame(projects);
            addFrame(parameters);
            addFrame(components);
            addFrame(code);
            addFrame(constants);

            setState ( new DockableListContainer(Orientation.vertical){{
                DockableFrameElement northElement = new DockableFrameElement(projects);
                add(0, northElement);

                DockableListContainer centerContainer = new DockableListContainer(Orientation.horizontal);
                centerContainer.add(0, new DockableFrameElement(components));
                centerContainer.add(1, new DockableContentElement());
                centerContainer.add(2, new DockableListContainer(Orientation.vertical){{
                    setSize(new Dimension(300, 250));
                    add(0, new DockableFrameElement(parameters));
                    add(1, new DockableFrameElement(constants));
                }});
                add(1, centerContainer);

                final DockableListContainer southContainer = new DockableListContainer(Orientation.horizontal);
                southContainer.add(0, new DockableFrameElement(code));
                southContainer.setSize(new Dimension(200, 250));
                add(2, southContainer);
            }} );

        }}, BorderLayout.CENTER);

    }

    static WebMenuBar setupMenu(WebMenuBar menu){
        menu.add(new WebMenu("File"){{
            add(new WebMenu("New"){{
                add(new WebMenuItem("Skin"){{
                    setAccelerator(Hotkey.CTRL_N);
                    addActionListener((e) -> Project.addProject(new Project()));
                }});
            }});


            addSeparator();
            add(new WebMenuItem("Exit"){{
                setAccelerator(Hotkey.ALT_F4);
                addActionListener((e) -> System.exit(0));
            }});
        }});

        return menu;
    }


    public class CustomDockablePanel extends WebDockableFrame{

        public CustomDockablePanel(String name, Component component) {
            super(name.replace(" ","_").toLowerCase(), name);
            setClosable(false);
            setFocusable(false);
            setMaximizable(false);

            add(component);
        }
    }

}
