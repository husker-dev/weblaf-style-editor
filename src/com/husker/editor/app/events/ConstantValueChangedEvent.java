package com.husker.editor.app.events;

import com.husker.editor.app.project.Constant;
import com.husker.editor.app.project.Project;
import com.husker.editor.app.listeners.ProjectEvent;

public class ConstantValueChangedEvent extends ProjectEvent {

    private Class<? extends Constant> type;
    private String constant;
    private String old_value;

    public ConstantValueChangedEvent(Project project, Class<? extends Constant> type, String constant, String old_value){
        super(project);
        this.type = type;
        this.constant = constant;
        this.old_value = old_value;
    }

    public Class<? extends Constant> getConstantType(){
        return type;
    }
    public String getConstant(){
        return constant;
    }
    public String getValue(){
        return getProject().Constants.getConstant(type, constant);
    }
    public String getOldValue(){
        return old_value;
    }
}
