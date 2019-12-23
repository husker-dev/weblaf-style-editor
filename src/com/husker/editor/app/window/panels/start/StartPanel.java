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

import javax.swing.*;
import java.awt.*;

public class StartPanel extends WebPanel {

    public StartPanel(){
        setStyleId(StyleId.of("background"));
        setLayout(new AlignLayout());
        add(new WebPanel(){{
            setPreferredSize(700, 500);
            setStyleId(StyleId.of("background1"));
            setLayout(new BorderLayout());

            add(new WebPanel(){{
                setOpaque(false);
                setLayout(new VerticalFlowLayout());

                add(new WebLabel("WebLaF Style Editor"){{
                    setStyleId(StyleId.labelSeparator);
                    setHorizontalAlignment(CENTER);
                    setFont(new Font("Corbel", Font.PLAIN, 40));
                    setMargin(15, 0, 0, 0);
                }});
                add(new WebLabel("'Write less, do more'"){{
                    setHorizontalAlignment(CENTER);
                    setFontSize(16);
                    setMargin(5, 0, 0, 0);
                }});
                add(new WebPanel(){{
                    setOpaque(false);
                    setLayout(new AlignLayout());
                    setMargin(40, 0, 0, 0);
                    add(new WebPanel(){{
                        setOpaque(false);
                        setLayout(new HorizontalFlowLayout());
                        add(new WebImage(new ImageIcon("bin/github_logo.png")));
                        add(new WebImage(new ImageIcon("bin/check_github.png")));
                        add(new WebImage(new ImageIcon("bin/weblaf_logo.png")));
                    }});
                }});
            }}, BorderLayout.NORTH);

            add(new WebPanel(){{
                setOpaque(false);
                setLayout(new VerticalFlowLayout());

                add(new WebLabel("Beginning"){{
                    setStyleId(StyleId.labelSeparator);
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
                add(new WebLabel("or"){{
                    setHorizontalAlignment(CENTER);
                    setFontSize(15);
                    setMargin(10, 0, 10, 0);
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
}
