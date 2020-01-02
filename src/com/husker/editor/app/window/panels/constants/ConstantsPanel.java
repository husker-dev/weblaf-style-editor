package com.husker.editor.app.window.panels.constants;

import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.laf.button.WebButton;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.tabbedpane.WebTabbedPane;
import com.alee.laf.toolbar.WebToolBar;
import com.alee.managers.style.StyleId;
import com.husker.editor.app.events.ConstantRemovedEvent;
import com.husker.editor.app.events.ConstantRenamedEvent;
import com.husker.editor.app.events.ConstantValueChangedEvent;
import com.husker.editor.app.events.NewConstantEvent;
import com.husker.editor.app.listeners.contants.ConstantsAdapter;
import com.husker.editor.app.project.Constant;
import com.husker.editor.app.project.Constants;
import com.husker.editor.app.project.Project;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;


public class ConstantsPanel extends WebPanel {

    private HashMap<Class<? extends Constant>, WebPanel> types = new HashMap<>();

    public ConstantsPanel(){
        setLayout(new BorderLayout());

        add(new WebTabbedPane(WebTabbedPane.LEFT, WebTabbedPane.SCROLL_TAB_LAYOUT){{
            for (Class<? extends Constant> constant : Constants.getConstantTypes()){

                addTab(Constants.getTitle(constant), Constants.getIcon(constant), new WebScrollPane(StyleId.scrollpaneUndecorated, new WebPanel(){{
                    setLayout(new BorderLayout());
                    add(new WebToolBar(StyleId.toolbarAttachedNorth){{
                        setPreferredHeight(30);
                        add(new WebButton("+"){{
                            addActionListener(e -> Project.getCurrentProject().Constants.addConstant(constant));
                        }});
                    }}, BorderLayout.NORTH);
                    add(new WebPanel(){{
                        types.put(constant, this);
                        setLayout(new VerticalFlowLayout());
                    }});
                }}){{
                    getVerticalScrollBar().setUnitIncrement(16);
                }});
                getTab(getTabCount() - 1).setHorizontalAlignment(LEFT);
            }
        }});

        Constants.addConstantListener(new ConstantsAdapter() {
            public void newConstant(NewConstantEvent event) {
                updateType(event.getConstantType());
            }
            public void renamed(ConstantRenamedEvent event) {
                updateType(event.getConstantType());
            }
            public void removed(ConstantRemovedEvent event) {
                updateType(event.getConstantType());
            }
            public void valueChanged(ConstantValueChangedEvent event) {
                for(Component panel : types.get(event.getConstantType()).getComponents()) {
                    ConstantPanel constant_panel = (ConstantPanel) panel;
                    if (constant_panel.getConstant().equals(event.getConstant()))
                        constant_panel.setValue(event.getValue());
                }
            }
        });
        Project.addListener(e -> {
            for(Class<? extends Constant> type : Constants.getConstantTypes())
                updateType(type);
        });
    }

    public void updateType(Class<? extends Constant> type){
        SwingUtilities.invokeLater(() -> {
            ArrayList<String> constants = new ArrayList<>(Arrays.asList(Project.getCurrentProject().Constants.getConstants(type)));
            Collections.sort(constants);

            types.get(type).removeAll();
            for(String constant : constants)
                types.get(type).add(new ConstantPanel(type, constant));
            types.get(type).updateUI();
        });
    }

}
