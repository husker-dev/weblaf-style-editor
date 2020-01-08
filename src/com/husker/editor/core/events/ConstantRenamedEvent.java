package com.husker.editor.core.events;

import com.husker.editor.core.Constant;
import com.husker.editor.core.Project;
import com.husker.editor.core.listeners.ProjectEvent;

public class ConstantRenamedEvent extends ProjectEvent {
    private Class<? extends Constant> type;
    private String old_name;
    private String new_name;

    public ConstantRenamedEvent(Project project, Class<? extends Constant> type, String old_name, String new_name){
        super(project);
        this.type = type;
        this.old_name = old_name;
        this.new_name = new_name;
    }

    public Class<? extends Constant> getConstantType(){
        return type;
    }
    public String getOldName(){
        return old_name;
    }
    public String getNewName(){
        return new_name;
    }
}
