package com.husker.editor.app;

import com.alee.laf.WebLookAndFeel;
import com.alee.managers.style.StyleManager;
import com.husker.editor.app.project.Constants;
import com.husker.editor.app.project.Project;
import com.husker.editor.app.skin.CustomSkin;
import com.husker.editor.app.window.Frame;

public class Main {

    public static Frame frame;

    public static void main(String[] args){

        WebLookAndFeel.install();
        StyleManager.setSkin(new CustomSkin());

        frame = new Frame();
        frame.setVisible(true);

        Project.setProject(new Project());

        Constants.setConstant(Constants.ConstType.Text, "Text Const 1", "Text 123");
        Constants.setConstant(Constants.ConstType.Text, "Text Const 2", "Text 123 HELLO");
        Constants.setConstant(Constants.ConstType.Number, "Number Const 1", "123");
        Constants.setConstant(Constants.ConstType.Number, "Number Const 2", "12332");

    }
}
