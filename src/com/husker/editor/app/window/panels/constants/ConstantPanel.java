package com.husker.editor.app.window.panels.constants;

import com.alee.extended.window.PopOverDirection;
import com.alee.laf.button.WebButton;
import com.alee.laf.grouping.GroupPane;
import com.alee.laf.grouping.GroupPaneConstraints;
import com.alee.laf.menu.PopupMenuWay;
import com.alee.laf.menu.WebMenuItem;
import com.alee.laf.menu.WebPopupMenu;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.separator.WebSeparator;
import com.alee.managers.style.StyleId;
import com.husker.editor.app.project.Constant;
import com.husker.editor.app.project.ConstantEditor;
import com.husker.editor.app.project.Constants;
import com.husker.editor.app.project.Project;
import com.husker.editor.app.tools.ConditionTextField;
import com.husker.editor.app.tools.Resources;

import java.awt.*;

public class ConstantPanel extends WebPanel {

    private String constant;
    private ConstantEditor editor;

    public ConstantPanel(final Class<? extends Constant> type, final String constant){
        this.constant = constant;

        editor = Constants.getEditor(type);
        Component editor_component = editor.getComponent();

        editor.setValue(Project.getCurrentProject().Constants.getConstant(type, constant));
        editor.onChanged(text -> Project.getCurrentProject().Constants.setConstant(type, constant, text));

        setLayout(new BorderLayout());

        add(new WebPanel(){{
            setMargin(5, 5, 0, 5);
            setLayout(new GridLayout(1, 2));
            add(new WebPanel(){{
                setLayout(new BorderLayout());
                add(new ConditionTextField(StyleId.textfieldTransparent, constant){{
                    setCondition(text -> {
                        Constants constants = Project.getCurrentProject().Constants;
                        if(text.contains(">") || text.contains("<") || text.contains(";") || text.contains("/"))
                            return false;
                        if(text.replaceAll("\\s","").equals(""))
                            return false;
                        if(constants.getConstant(type, text) != null)
                            return false;
                        return true;
                    });
                    onApplied(() -> Project.getCurrentProject().Constants.renameConstant(type, constant, getText()));
                }});
                add(WebSeparator.createVertical(), BorderLayout.EAST);
            }});
            add(new GroupPane(){{
                add(editor_component, GroupPaneConstraints.FILL);
                WebButton button;
                add(button = new WebButton(Resources.getImageIcon("more.png")));

                final WebPopupMenu menu = new WebPopupMenu();
                menu.setPopupMenuWay(PopupMenuWay.belowEnd);
                menu.add(new WebMenuItem("Delete"){{
                    addActionListener(e -> Project.getCurrentProject().Constants.removeConstant(type, constant));
                }});
                button.addActionListener(e -> {
                    menu.setPopupMenuWay(PopupMenuWay.belowEnd);
                    menu.show(button, -button.getWidth() / 2, button.getHeight());
                });
            }});
        }});
        add(WebSeparator.createHorizontal(), BorderLayout.SOUTH);
    }

    public String getConstant(){
        return constant;
    }

    public void setValue(String value){
        editor.setValue(value);
    }
}
