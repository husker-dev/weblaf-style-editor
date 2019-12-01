package app.window.panels.projects;

import app.Project;
import app.window.panels.WrapLayout;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.managers.style.StyleId;

import java.awt.*;

public class ProjectsPanel extends WebPanel {

    public ProjectsPanel(){
        setPreferredSize(new Dimension(0, 50));


        add(new WebScrollPane(new WebPanel(){{
            setStyleId(StyleId.panelNonOpaque);
            setLayout(new WrapLayout());

            Project.addListener((event, objects) -> {
                removeAll();
                for(Project project : Project.getProjects()) {
                    ProjectPanel panel = new ProjectPanel(project);
                    if (Project.getCurrentProject() == project)
                        panel.setSelected(true);
                    add(panel);
                }
                updateUI();
            });
        }}){{
            getVerticalScrollBar().setUnitIncrement(16);
            setStyleId(StyleId.scrollpaneUndecorated);
        }});

    }
}
