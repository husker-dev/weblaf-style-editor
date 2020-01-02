package com.husker.editor.app.events;

import com.husker.editor.app.project.Constant;
import com.husker.editor.app.project.Project;
import com.husker.editor.app.listeners.ProjectEvent;

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
