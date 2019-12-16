package com.husker.editor.app.window;


import com.alee.api.data.Orientation;
import com.alee.extended.dock.WebDockableFrame;
import com.alee.extended.dock.WebDockablePane;
import com.alee.extended.dock.data.DockableContentElement;
import com.alee.extended.dock.data.DockableFrameElement;
import com.alee.extended.dock.data.DockableListContainer;
import com.alee.extended.label.TextWrap;
import com.alee.extended.label.WebStyledLabel;
import com.alee.extended.layout.AlignLayout;
import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.menu.WebMenu;
import com.alee.laf.menu.WebMenuBar;
import com.alee.laf.menu.WebMenuItem;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.separator.WebSeparator;
import com.alee.managers.hotkey.Hotkey;
import com.alee.managers.style.StyleId;
import com.husker.editor.app.project.Project;
import com.husker.editor.app.project.StyleComponent;
import com.husker.editor.app.window.panels.code.CodePanel;
import com.husker.editor.app.window.panels.components.ComponentsPanel;
import com.husker.editor.app.window.panels.constants.ConstantsPanel;
import com.husker.editor.app.window.panels.parameters.ParameterPanel;
import com.husker.editor.app.window.panels.preview.PreviewPanel;
import com.husker.editor.app.window.panels.project.ProjectPanel;

import javax.swing.*;
import javax.swing.text.Style;
import java.awt.*;
import java.util.Map;

public class Frame extends JFrame {

    public static Frame context;

    private CustomDockablePanel parameters;
    private CustomDockablePanel components;
    private CustomDockablePanel code;
    private CustomDockablePanel constants;
    private PreviewPanel preview;

    private WebDockablePane dockablePane;
    private WebPanel backgroundPanel;

    public Frame(){

        context = this;

        setTitle("WebLaF Editor");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1100,700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setJMenuBar(setupMenu(new WebMenuBar()));
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);

        backgroundPanel = new WebPanel(){{
            setStyleId(StyleId.of("background"));
            setLayout(new AlignLayout());
            add(new WebPanel(){{
                setPreferredSize(700, 500);
                setStyleId(StyleId.of("background1"));
                setLayout(new VerticalFlowLayout());

                add(new WebLabel("WebLaF Style Editor"){{
                    setStyleId(StyleId.labelSeparator);
                    setHorizontalAlignment(CENTER);
                    setFont(new Font("Corbel", Font.PLAIN, 40));
                    setMargin(10, 0, 0, 0);
                }});
                add(new WebLabel("A tool that makes it easy to create and edit styles of WebLaF components."){{
                    setHorizontalAlignment(CENTER);
                    setFontSize(15);
                    setMargin(10, 0, 0, 0);
                }});
                add(new WebPanel(){{
                    setPreferredHeight(50);
                    setOpaque(false);
                }});

                add(new WebLabel("Beginning"){{
                    setStyleId(StyleId.labelSeparator);
                    setHorizontalAlignment(CENTER);
                    setFontSize(24);
                    setMargin(130, 0, 0, 0);
                }});
                add(new WebPanel(){{
                    setOpaque(false);
                    setLayout(new AlignLayout());
                    add(new WebButton("Create new project"){{
                        setHorizontalAlignment(CENTER);
                        setFontSize(16);
                        setMargin(20, 0, 0, 0);
                        addActionListener(e -> Project.createProject());
                    }});
                }});
                add(new WebLabel("or"){{
                    setHorizontalAlignment(CENTER);
                    setFontSize(16);
                    setMargin(10, 0, 10, 0);
                }});
                add(new WebPanel(){{
                    setOpaque(false);
                    setLayout(new AlignLayout());
                    add(new WebButton("Open existed project"){{
                        setHorizontalAlignment(CENTER);
                        setFontSize(16);
                    }});
                }});

                //add(new WebSeparator(StyleId.of("custom-separator"), WebSeparator.HORIZONTAL));
            }});
        }};
        dockablePane = new WebDockablePane(){{
            setStyleId(StyleId.dockableframeCompact);

            preview = new PreviewPanel();

            parameters = new CustomDockablePanel("Parameters", new ParameterPanel());
            parameters.setIcon(new ImageIcon("bin/parameters.png"));

            components = new CustomDockablePanel("Components", new ComponentsPanel());
            components.setIcon(new ImageIcon("bin/components.png"));

            code = new CustomDockablePanel("Code", new CodePanel());
            code.setIcon(new ImageIcon("bin/code.png"));

            constants = new CustomDockablePanel("Constants", new ConstantsPanel());
            constants.setIcon(new ImageIcon("bin/constants.png"));

            setContent(preview);
            addFrame(parameters);
            addFrame(components);
            addFrame(code);
            addFrame(constants);

            setState ( new DockableListContainer(Orientation.vertical){{
                DockableListContainer centerContainer = new DockableListContainer(Orientation.horizontal);
                centerContainer.add(0, new DockableFrameElement(components));
                centerContainer.add(1, new DockableListContainer(Orientation.vertical){{
                    setSize(new Dimension(350, 250));
                    add(0, new DockableContentElement());
                    add(1, new DockableFrameElement(code){{
                        setSize(new Dimension(200, 250));
                    }});
                }});
                centerContainer.add(2, new DockableListContainer(Orientation.vertical){{
                    setSize(new Dimension(350, 250));
                    add(0, new DockableFrameElement(parameters));
                    add(1, new DockableFrameElement(constants));
                }});
                add(0, centerContainer);
            }} );

        }};

        Project.addListener((event) -> {
            if(Project.getCurrentProject() == null){
                remove(dockablePane);
                add(backgroundPanel, BorderLayout.CENTER);
            }else {
                remove(backgroundPanel);
                add(dockablePane, BorderLayout.CENTER);
            }
        });
        add(backgroundPanel, BorderLayout.CENTER);

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
                                    Project.getCurrentProject().Components.addComponent(entry.getValue().newInstance().clone(Project.getCurrentProject()));
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
