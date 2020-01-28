package com.husker.editor.content.style;

import com.husker.editor.core.Parameter;
import com.husker.editor.core.xml.XMLHead;

import java.util.function.UnaryOperator;

public class StyleParameterUtils {

    public static void checkHead(XMLHead head, String folder){
        if(head.containsHeadByPath(folder))
            head.createHeadByPath(folder);
    }

    public static XMLHead generateHead(String code){
        XMLHead head = XMLHead.fromString(code);
        if(head == null)
            head = new XMLHead("style");
        return head;
    }

    public static void editHead(Parameter parameter, UnaryOperator<XMLHead> applier){
        parameter.onValueChanged(value -> {
            XMLHead head = generateHead(parameter.getAppliedObject().getCode());

            applier.apply(head);
            parameter.getAppliedObject().setCode(head.toString());
        });
    }
}
