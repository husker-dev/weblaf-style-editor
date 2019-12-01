package app.window;

import app.Project;
import app.window.panels.code.CodePanel;
import app.window.panels.components.ComponentsPanel;
import app.window.panels.constants.ConstantsPanel;
import app.window.panels.parameters.ParameterPanel;
import app.window.panels.preview.PreviewPanel;
import app.window.panels.projects.ProjectsPanel;
import com.alee.api.data.CompassDirection;
import com.alee.api.data.Orientation;
import com.alee.extended.dock.WebDockableFrame;
import com.alee.extended.dock.WebDockablePane;
import com.alee.extended.dock.data.DockableContentElement;
import com.alee.extended.dock.data.DockableFrameElement;
import com.alee.extended.dock.data.DockableListContainer;
import com.alee.laf.menu.WebMenu;
import com.alee.laf.menu.WebMenuBar;
import com.alee.laf.menu.WebMenuItem;
import com.alee.laf.panel.WebPanel;
import com.alee.managers.hotkey.Hotkey;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    public static Frame context;

    public Frame(){
        context = this;

        setTitle("WebLaF Editor");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1100,700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setJMenuBar(setupMenu(new WebMenuBar()));

        add(new WebDockablePane(){{
            setContent(new PreviewPanel());

            CustomDockablePanel projects = new CustomDockablePanel("Projects", new ProjectsPanel());
            CustomDockablePanel parameters = new CustomDockablePanel("Parameters", new ParameterPanel());
            CustomDockablePanel components = new CustomDockablePanel("Components", new ComponentsPanel());
            CustomDockablePanel code = new CustomDockablePanel("Code", new CodePanel());
            CustomDockablePanel constants = new CustomDockablePanel("Constants", new ConstantsPanel());

            addFrame(projects);
            addFrame(parameters);
            addFrame(components);
            addFrame(code);
            addFrame(constants);

            setState ( new DockableListContainer(Orientation.vertical){{
                DockableFrameElement northElement = new DockableFrameElement(projects);
                add(0, northElement);

                final DockableListContainer centerContainer = new DockableListContainer(Orientation.horizontal);
                centerContainer.add(0, new DockableFrameElement(components));
                centerContainer.add(1, new DockableContentElement());
                centerContainer.add(2, new DockableListContainer(Orientation.vertical){{
                    setSize(new Dimension(220, 250));
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
