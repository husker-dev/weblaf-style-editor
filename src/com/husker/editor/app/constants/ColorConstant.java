package com.husker.editor.app.constants;

import com.husker.editor.app.constants.editors.ColorEditor;
import com.husker.editor.app.project.Constant;
import com.husker.editor.app.tools.Resources;

public class ColorConstant extends Constant {

    public ColorConstant() {
        super("0,0,0", "Color", new ColorEditor(), Resources.getImageIcon("constants/color.png"));
    }
}
