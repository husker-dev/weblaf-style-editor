package com.husker.editor.content.constants.number;

import com.husker.editor.core.Constant;
import com.husker.editor.core.tools.Resources;


public class NumberConstant extends Constant {
    public NumberConstant() {
        super("0", "Number", new NumberEditor(), Resources.getImageIcon("constants/number.png"));
    }
}
