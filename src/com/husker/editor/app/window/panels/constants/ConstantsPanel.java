package com.husker.editor.app.window.panels.constants;

import com.alee.laf.button.WebButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.grouping.GroupPane;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.toolbar.WebToolBar;
import com.alee.managers.style.StyleId;
import com.husker.editor.app.Constants;
import com.husker.editor.app.window.components.MovableComponentList;

import java.awt.*;
import java.util.ArrayList;


public class ConstantsPanel extends WebPanel {

    WebComboBox comboBox;
    WebButton add;
    MovableComponentList list;
    WebScrollPane scroll;


    public ConstantsPanel(){
        setLayout(new BorderLayout());

        comboBox = new WebComboBox(new String[]{"Text", "Number", "Color"});
        add = new WebButton("+"){{
            addActionListener(e -> {
                if(comboBox.getSelectedItem().equals("Text"))
                    Constants.addConstant(Constants.ConstType.Text);
                if(comboBox.getSelectedItem().equals("Number"))
                    Constants.addConstant(Constants.ConstType.Number);
                if(comboBox.getSelectedItem().equals("Color"))
                    Constants.addConstant(Constants.ConstType.Color);
            });
        }};

        add(new WebToolBar(){{
            add(new GroupPane(comboBox, add));
        }}, BorderLayout.NORTH);

        list = new MovableComponentList(){{
            Constants.addListener((event, objects) -> {
                if(event.oneOf(Constants.ConstEvent.New)){
                    String name = objects[0].toString();


                }
            });
        }};
        scroll = new WebScrollPane(list){{
            getVerticalScrollBar().setUnitIncrement(16);
            setStyleId(StyleId.scrollpaneUndecorated);
        }};
    }
}
