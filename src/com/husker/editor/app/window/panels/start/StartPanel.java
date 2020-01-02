package com.husker.editor.app.window.panels.start;

import com.alee.extended.image.WebImage;
import com.alee.extended.layout.AlignLayout;
import com.alee.extended.layout.HorizontalFlowLayout;
import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.managers.style.StyleId;
import com.husker.editor.app.project.Project;
import com.husker.editor.app.tools.NetUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class StartPanel extends WebPanel {

    public StartPanel(){
        super(StyleId.of("background"));
        setLayout(new AlignLayout());
        add(new WebPanel(StyleId.of("background1")){{
            setPreferredSize(700, 500);
            setLayout(new BorderLayout());

            add(new WebPanel(){{
                setOpaque(false);
                setLayout(new VerticalFlowLayout());

                add(new WebLabel(StyleId.labelSeparator, "WebLaF Style Editor"){{
                    setHorizontalAlignment(CENTER);
                    setFont(new Font("Corbel", Font.PLAIN, 40));
                    setMargin(15, 0, 15, 0);
                }});
                add(new WebLabel("Tools that makes it easy to create style"){{
                    setHorizontalAlignment(CENTER);
                    setFont(new Font("Corbel", Font.PLAIN, 20));
                }});
                add(new WebPanel(){{
                    setOpaque(false);
                    setLayout(new AlignLayout());
                    setMargin(40, 0, 0, 0);
                    add(new WebPanel(){{
                        setOpaque(false);
                        setLayout(new HorizontalFlowLayout());
                        add(new WebImage(new ImageIcon("bin/github_logo.png")){{
                            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                            addMouseMotionListener(new MouseMotionAdapter() {
                                public void mouseMoved(MouseEvent e) {
                                    int range = 33;
                                    setCursor(Cursor.getPredefinedCursor(inRange(range, getSize(), e.getPoint()) ? Cursor.HAND_CURSOR : Cursor.DEFAULT_CURSOR));
                                }
                            });
                            addMouseListener(new MouseAdapter() {
                                public void mouseClicked(MouseEvent e) {
                                    if(e.getClickCount() == 1 && getCursor().equals(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)))
                                        NetUtils.openBrowserLink("https://github.com/husker-dev/weblaf-style-editor");
                                }
                            });
                        }});
                        add(new WebImage(new ImageIcon("bin/check_github.png")));
                        add(new WebImage(new ImageIcon("bin/weblaf_logo.png")){{
                            addMouseMotionListener(new MouseMotionAdapter() {
                                public void mouseMoved(MouseEvent e) {
                                    int range = 33;
                                    setCursor(Cursor.getPredefinedCursor(inRange(range, getSize(), e.getPoint()) ? Cursor.HAND_CURSOR : Cursor.DEFAULT_CURSOR));
                                }
                            });
                            addMouseListener(new MouseAdapter() {
                                public void mouseClicked(MouseEvent e) {
                                    if(e.getClickCount() == 1 && getCursor().equals(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)))
                                        NetUtils.openBrowserLink("https://github.com/mgarin/weblaf");
                                }
                            });
                        }});
                    }});
                }});

            }}, BorderLayout.NORTH);

            add(new WebPanel(){{
                setOpaque(false);
                setLayout(new VerticalFlowLayout());

                add(new WebLabel(StyleId.labelSeparator, "Beginning"){{
                    setHorizontalAlignment(CENTER);
                    setMargin(5, 0, 5, 0);
                    setFontSize(24);
                }});
                add(new WebPanel(){{
                    setOpaque(false);
                    setLayout(new AlignLayout());
                    setMargin(20, 0, 0, 0);
                    add(new WebButton("Create a new project"){{
                        setHorizontalAlignment(CENTER);
                        setFontSize(15);
                        setPreferredWidth(300);
                        addActionListener(e -> Project.createProject());
                    }});
                }});
                add(new WebPanel(){{
                    setOpaque(false);
                    setLayout(new AlignLayout());
                    setMargin(10, 0, 10, 0);
                    add(new WebLabel(StyleId.labelSeparator, "or"){{
                        setHorizontalAlignment(CENTER);
                        setFontSize(15);
                        setPreferredWidth(200);
                    }});
                }});
                add(new WebPanel(){{
                    setOpaque(false);
                    setLayout(new AlignLayout());
                    setMargin(0, 0, 30, 0);
                    add(new WebButton("Open existing project"){{
                        setHorizontalAlignment(CENTER);
                        setFontSize(15);
                        setPreferredWidth(300);
                    }});
                }});
            }}, BorderLayout.SOUTH);

        }});
    }

    private boolean inRange(int range, Dimension size, Point mouse){
        int center_x = (int)size.getWidth() / 2;
        int center_y = (int)size.getHeight() / 2;
        return Math.sqrt(Math.pow(center_x - mouse.x, 2) + Math.pow(center_y - mouse.y, 2)) < range;
    }
}
