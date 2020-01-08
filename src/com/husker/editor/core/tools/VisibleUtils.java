package com.husker.editor.core.tools;

import com.husker.editor.core.EditableObject;
import com.husker.editor.core.Project;

public class VisibleUtils {

    public static boolean onProject(){
        return Project.getCurrentProject() != null;
    }
    public static boolean onEditableObject(){
        return Project.getCurrentProject() != null && Project.getCurrentProject().getSelectedObject() != null;
    }
    public static boolean isEditableObject(EditableObject object){
        return onEditableObject() && Project.getCurrentProject().getSelectedObject() == object;
    }
}
