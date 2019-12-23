package com.husker.editor.app.window.panels.constants;

import com.alee.laf.button.WebButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.grouping.GroupPane;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.toolbar.WebToolBar;
import com.alee.managers.style.StyleId;
import com.husker.editor.app.project.Constants;
import com.husker.editor.app.project.Project;
import com.husker.editor.app.tools.MovableComponentList;

import java.awt.*;


public class ConstantsPanel extends WebPanel {

    private WebComboBox comboBox;
    private WebButton add;
    private MovableComponentList list;
    private WebScrollPane scroll;


    public ConstantsPanel(){
        setLayout(new BorderLayout());

        comboBox = new WebComboBox(new String[]{"Text", "Number", "Color"});
        add = new WebButton("+"){{
            addActionListener(e -> {
                if(comboBox.getSelectedItem().equals("Text"))
                    Project.getCurrentProject().Constants.addConstant(Constants.ConstType.Text);
                if(comboBox.getSelectedItem().equals("Number"))
                    Project.getCurrentProject().Constants.addConstant(Constants.ConstType.Number);
                if(comboBox.getSelectedItem().equals("Color"))
                    Project.getCurrentProject().Constants.addConstant(Constants.ConstType.Color);
            });
        }};

        add(new WebToolBar(){{
            add(new GroupPane(comboBox, add));
        }}, BorderLayout.NORTH);

        list = new MovableComponentList(){{

        }};
        scroll = new WebScrollPane(list){{
            getVerticalScrollBar().setUnitIncrement(16);
            setStyleId(StyleId.scrollpaneUndecorated);
        }};
    }
}
