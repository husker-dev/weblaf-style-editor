package com.husker.editor.app.constants;

import com.husker.editor.app.constants.editors.NumberEditor;
import com.husker.editor.app.project.Constant;
import com.husker.editor.app.tools.Resources;


public class NumberConstant extends Constant {
    public NumberConstant() {
        super("0", "Number", new NumberEditor(), Resources.getImageIcon("constants/number.png"));
    }
}
