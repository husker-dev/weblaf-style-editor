package app.window.panels.projects;

import app.Project;
import com.alee.laf.button.WebToggleButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.menu.PopupMenuWay;
import com.alee.laf.menu.WebMenuItem;
import com.alee.laf.menu.WebPopupMenu;
import com.alee.managers.hotkey.Hotkey;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class ProjectPanel extends WebToggleButton {
    Project project;

    static ImageIcon close_1, close_2;
    static {
        close_1 = new ImageIcon("bin/close.png");
        close_2 = new ImageIcon("bin/close_h.png");
    }

    public ProjectPanel(Project project){
        this.project = project;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(120, 40));
        setMaximumSize(new Dimension(400, 40));
        setMinimumSize(new Dimension(120, 40));


        add(new WebLabel(project.getName()){{
            setHorizontalAlignment(SwingConstants.CENTER);
            setVerticalAlignment(SwingConstants.CENTER);

            FontMetrics metrics = getFontMetrics(getFont());
            int width = metrics.stringWidth(getText());
            if(width > getSize().width - 50)
                ProjectPanel.this.setPreferredSize(new Dimension(50 + width, 40));
        }}, BorderLayout.CENTER);

        add(new WebLabel(){{
            setPreferredSize(new Dimension(15, 15));
            setIcon(close_1);
            addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    setIcon(close_2);
                }
                public void mouseExited(MouseEvent e) {
                    setIcon(close_1);
                }
                public void mouseReleased(MouseEvent event){
                    if(event.getButton() == 1)
                        Project.removeProject(project);
                }
            });
        }}, BorderLayout.LINE_END);

        setBorder(new EmptyBorder(10, 10, 10, 10));

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if(e.getButton() == 3){
                    WebPopupMenu menu = new WebPopupMenu();
                    menu.add(new WebMenuItem("Rename"){{
                        setAccelerator(Hotkey.CTRL_R);
                        addActionListener((e) -> project.showRenameDialog());
                    }});
                    menu.setPopupMenuWay(PopupMenuWay.aboveMiddle);
                    menu.show(ProjectPanel.this, e.getX(), e.getY());
                }
            }
        });
        addActionListener((e) -> {
            if(isSelected())
                Project.setCurrentProject(project);
            else
                Project.resetProject();
        });

    }
}