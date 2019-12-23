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
import com.alee.managers.style.StyleId;
import com.husker.editor.app.project.AbstractEditableObject;
import com.husker.editor.app.project.Project;
import com.husker.editor.app.project.StyleComponent;
import com.husker.editor.app.window.panels.code.CodePanel;
import com.husker.editor.app.window.panels.components.ComponentsPanel;
import com.husker.editor.app.window.panels.constants.ConstantsPanel;
import com.husker.editor.app.window.panels.error.ErrorsPanel;
import com.husker.editor.app.window.panels.parameters.ParameterPanel;
import com.husker.editor.app.window.panels.preview.PreviewPanel;
import com.husker.editor.app.window.panels.project.ProjectPanel;
import com.husker.editor.app.window.panels.start.StartPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class Frame extends JFrame {

    public static Frame context;

    private CustomDockablePanel parameters;
    private CustomDockablePanel components;
    private CustomDockablePanel code;
    private CustomDockablePanel errors;
    private CustomDockablePanel constants;
    private PreviewPanel preview;

    private WebDockablePane dockablePane;
    private StartPanel startPanel;

    public Frame(){

        context = this;

        setTitle("WebLaF Editor");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1100,700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setJMenuBar(setupMenu(new WebMenuBar()));
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);

        startPanel = new StartPanel();
        dockablePane = new WebDockablePane(){{
            setStyleId(StyleId.dockableframeCompact);

            preview = new PreviewPanel();

            parameters = new CustomDockablePanel("Parameters", new ParameterPanel());
            parameters.setIcon(new ImageIcon("bin/parameters.png"));

            components = new CustomDockablePanel("Components", new ComponentsPanel());
            components.setIcon(new ImageIcon("bin/components.png"));

            code = new CustomDockablePanel("Code", new CodePanel());
            code.setIcon(new ImageIcon("bin/code.png"));

            errors = new CustomDockablePanel("Errors", new ErrorsPanel());
            errors.setIcon(new ImageIcon("bin/error.png"));

            constants = new CustomDockablePanel("Constants", new ConstantsPanel());
            constants.setIcon(new ImageIcon("bin/constants.png"));

            setContent(preview);
            addFrame(parameters);
            addFrame(components);
            addFrame(code);
            addFrame(errors);
            addFrame(constants);

            setState(new DockableListContainer(Orientation.vertical){{
                DockableListContainer horizontal1 = new DockableListContainer(Orientation.horizontal){{
                    add(0, new DockableFrameElement(components));
                    add(1, new DockableContentElement());
                    add(2, new DockableListContainer(Orientation.vertical){{
                        setSize(new Dimension(350, 250));
                        add(0, new DockableFrameElement(parameters));
                        add(1, new DockableFrameElement(constants));
                    }});
                }};
                DockableListContainer horizontal2 = new DockableListContainer(Orientation.horizontal){{
                    setSize(new Dimension(200, 250));
                    add(0, new DockableFrameElement(code));
                    add(1, new DockableFrameElement(errors));
                }};
                add(0, horizontal1);
                add(1, horizontal2);
            }} );

        }};

        Project.addListener((event) -> {
            if(Project.getCurrentProject() == null){
                remove(dockablePane);
                add(startPanel, BorderLayout.CENTER);
            }else {
                remove(startPanel);
                add(dockablePane, BorderLayout.CENTER);
            }
        });
        add(startPanel, BorderLayout.CENTER);

        add(new ProjectPanel(), BorderLayout.NORTH);
    }

    static WebMenuBar setupMenu(WebMenuBar menu){
        menu.add(new WebMenu("File"){{
            add(new WebMenu("New"){{
                add(new WebMenuItem("Skin", new ImageIcon("bin/project.png")){{
                    setAccelerator(Hotkey.CTRL_N);
                    addActionListener((e) -> {
                        Project.createProject();
                    });
                }});
                addSeparator();
                add(new WebMenu("Style", new ImageIcon("bin/style.png")){{
                    setEnabled(false);
                    Project.addListener((event) -> setEnabled(Project.getCurrentProject() != null));

                    for(Map.Entry<String, Class<? extends StyleComponent>> entry : StyleComponent.components.entrySet()) {
                        add(new WebMenuItem(entry.getKey(), new ImageIcon("bin/style.png")) {{
                            addActionListener((e) -> {
                                try {
                                    Project.getCurrentProject().Components.addComponent((StyleComponent) AbstractEditableObject.newInstance(entry.getValue(), Project.getCurrentProject()));
                                }catch (Exception ex){
                                    ex.printStackTrace();
                                }
                            });
                        }});
                    }
                }});
            }});

            addSeparator();
            add(new WebMenuItem("Exit"){{
                setIcon(new ImageIcon("bin/exit_icon.png"));
                setAccelerator(Hotkey.ALT_F4);
                addActionListener((e) -> System.exit(0));
            }});
        }});

        return menu;
    }


    public static class CustomDockablePanel extends WebDockableFrame{

        public CustomDockablePanel(String name, Component component) {
            super(name.replace(" ","_").toLowerCase(), name);
            setClosable(false);
            setFocusable(false);
            setMaximizable(false);

            add(component);
        }
    }

}
