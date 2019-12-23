package com.husker.editor.app;

import com.alee.laf.WebLookAndFeel;
import com.alee.managers.style.StyleManager;
import com.husker.editor.app.project.*;
import com.husker.editor.app.project.Error;
import com.husker.editor.app.skin.CustomSkin;
import com.husker.editor.app.window.Frame;

public class Main {

    public static Frame frame;
    public static String current_version = "1.2";

    public static void main(String[] args){

        WebLookAndFeel.install();
        StyleManager.setSkin(new CustomSkin());

        frame = new Frame();
        frame.setVisible(true);

        UpdateManager.checkForUpdate();

        Project project = new Project();
        Project.setProject(project);

        project.Constants.setConstant(Constants.ConstType.Text, "Text Const 1", "Text 123");
        project.Constants.setConstant(Constants.ConstType.Text, "Text Const 2", "Text 123 HELLO");
        project.Constants.setConstant(Constants.ConstType.Number, "Number Const 1", "123");
        project.Constants.setConstant(Constants.ConstType.Number, "Number Const 2", "12332");

        Errors.Current.addError(new Error("test", "Title", "Text"));
        Errors.Current.removeError("test");

    }
}
