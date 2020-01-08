package com.husker.editor.content.constants.color;

import com.husker.editor.core.Constant;
import com.husker.editor.core.tools.Resources;

public class ColorConstant extends Constant {

    public ColorConstant() {
        super("0,0,0", "Color", new ColorEditor(), Resources.getImageIcon("constants/color.png"));
    }
}
