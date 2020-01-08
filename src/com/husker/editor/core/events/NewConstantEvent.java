package com.husker.editor.core.events;

import com.husker.editor.core.Constant;
import com.husker.editor.core.Project;
import com.husker.editor.core.listeners.ProjectEvent;

public class NewConstantEvent extends ProjectEvent {
    private Class<? extends Constant> type;
    private String constant;

    public NewConstantEvent(Project project, Class<? extends Constant> type, String constant){
        super(project);
        this.type = type;
        this.constant = constant;
    }

    public Class<? extends Constant> getConstantType(){
        return type;
    }
    public String getConstant(){
        return constant;
    }
}
