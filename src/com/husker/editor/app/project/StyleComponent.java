package com.husker.editor.app.project;

import com.husker.editor.app.components.Styled_Button;
import com.husker.editor.app.components.Styled_Label;
import com.husker.editor.app.parameters.*;
import com.husker.editor.app.xml.XMLHead;
import com.husker.editor.app.xml.XMLParameter;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class StyleComponent implements Cloneable{

    // Static

    public static HashMap<String, Class<? extends StyleComponent>> components = new HashMap<String, Class<? extends StyleComponent>>(){{
        put("Button", Styled_Button.class);
        put("Label", Styled_Label.class);
    }};

    public static class Parameters {
        public static final String ID = "id";
        public static final String EXTENDS = "extends";
        public static final String DECORATIONS = "decorations";
        public static final String OVERWRITE_DECORATIONS = "overwrite_decorations";
        public static final String ROUND = "round";
        public static final String SHAPE_ENABLED = "shape_enabled";
        public static final String INNER_SHADOW_WIDTH = "inner_shadow_width";
        public static final String OUTER_SHADOW_WIDTH = "outer_shadow_width";
        public static final String BORDER_COLOR = "border_color";
        public static final String INNER_SHADOW_COLOR = "inner_shadow_color";
        public static final String OUTER_SHADOW_COLOR = "outer_shadow_color";
        public static final String BACKGROUND_TYPE = "background_type";
        public static final String GRADIENT_TYPE = "gradient_type";
        public static final String GRADIENT_FROM = "gradient_from";
        public static final String GRADIENT_TO = "gradient_to";
        public static final String BACKGROUND_COLOR = "background_color";

        public static final String BUTTON_SHOW_ICON = "button_show_icon";
        public static final String BUTTON_SHOW_TEXT = "button_show_text";

        private static final String KIT_BASE = getKit(ID, EXTENDS, DECORATIONS, OVERWRITE_DECORATIONS);
        public static final String KIT_SHAPE = getKit(ROUND, SHAPE_ENABLED);
        public static final String KIT_INNER_SHADOW = getKit(INNER_SHADOW_COLOR, INNER_SHADOW_WIDTH);
        public static final String KIT_OUTER_SHADOW = getKit(OUTER_SHADOW_COLOR, OUTER_SHADOW_WIDTH);
        public static final String KIT_BORDER = getKit(BORDER_COLOR);
        public static final String KIT_BACKGROUND = getKit(BACKGROUND_TYPE, GRADIENT_TYPE, GRADIENT_FROM, GRADIENT_TO, BACKGROUND_COLOR);
        public static final String KIT_BUTTON_CONTENT = getKit(BUTTON_SHOW_ICON, BUTTON_SHOW_TEXT);

        private static String getKit(String... parameters){
            String out = "@";
            for(String parameter : parameters)
                out += parameter + ",";
            out = out.substring(0, out.length() - 1);
            return out;
        }
    }

    private static Parameter[] parameters = new Parameter[]{
            // Base
            new TextParameter("Id", Parameters.ID),
            new TextParameter("Extends", Parameters.EXTENDS),
            new BooleanParameter("Decorations", Parameters.DECORATIONS),
            new BooleanParameter("Overwrite base", Parameters.OVERWRITE_DECORATIONS),

            // Shape
            new BooleanParameter("Enable", Parameters.SHAPE_ENABLED, "Shape"),
            new IntegerParameter("Round", Parameters.ROUND, "Shape"),

            // Shadow Inner
            new IntegerParameter("Width", Parameters.INNER_SHADOW_WIDTH, "Inner shadow"),
            new ColorParameter("Color", Parameters.INNER_SHADOW_COLOR, "Inner shadow"),

            // Shadow outer
            new IntegerParameter("Width", Parameters.OUTER_SHADOW_WIDTH, "Outer shadow"),
            new ColorParameter("Color", Parameters.OUTER_SHADOW_COLOR, "Outer shadow"),

            // Border
            new ColorParameter("Color", Parameters.BORDER_COLOR, "Border"),

            // Background
            new ComboParameter("Background", Parameters.BACKGROUND_TYPE, "Background", new String[]{"Gradient","Color"}){{
                addActionListener(() -> {
                    boolean visible = getValue().equals("Gradient");
                    getParameter(Parameters.GRADIENT_TYPE).setVisible(visible);
                    getParameter(Parameters.GRADIENT_TO).setVisible(visible);
                    getParameter(Parameters.GRADIENT_FROM).setVisible(visible);

                    getParameter(Parameters.BACKGROUND_COLOR).setVisible(!visible);
                    Parameter.visibleUpdate();
                });
            }},

            new ComboParameter("Gradient type", Parameters.GRADIENT_TYPE, "Background", new String[]{"linear", "radial"}),
            new Point2DParameter("Gradient from", Parameters.GRADIENT_FROM, "Background"),
            new Point2DParameter("Gradient to", Parameters.GRADIENT_TO, "Background"),
            new ColorParameter("Color", Parameters.BACKGROUND_COLOR, "Background"),

            // Button content
            new BooleanParameter("Show text", Parameters.BUTTON_SHOW_TEXT, "Content"),
            new BooleanParameter("Show icon", Parameters.BUTTON_SHOW_ICON, "Content")
    };

    private static HashMap<String, Variable> variables = new HashMap<>();

    static {
        addVariable(Parameters.ID);
        addVariable(Parameters.EXTENDS);
        addVariable(Parameters.DECORATIONS, "true");
        addVariable(Parameters.OVERWRITE_DECORATIONS, "false");

        // Round
        addVariable(Parameters.ROUND, "0");
        addVariable(Parameters.SHAPE_ENABLED, "false");

        // Background
        addVariable(Parameters.BACKGROUND_TYPE,"Color");

        addVariable(Parameters.GRADIENT_TYPE,"linear");
        addVariable(Parameters.GRADIENT_FROM,"0.0,0.0");
        addVariable(Parameters.GRADIENT_TO,"0.0,1.0");
        addVariable(Parameters.BACKGROUND_COLOR,"255,255,255");

        // Border
        addVariable(Parameters.BORDER_COLOR,"0,0,0,0");

        // Shadow inner
        addVariable(Parameters.INNER_SHADOW_WIDTH,"0");
        addVariable(Parameters.INNER_SHADOW_COLOR,"0,0,0");

        // Shadow outer
        addVariable(Parameters.OUTER_SHADOW_COLOR,"0,0,0");
        addVariable(Parameters.OUTER_SHADOW_WIDTH,"0");

        // Button content
        addVariable(Parameters.BUTTON_SHOW_TEXT,"false");
        addVariable(Parameters.BUTTON_SHOW_ICON,"false");
    }

    static private void addVariable(String name, Variable variable){
        variables.put(name, variable);
    }
    static private void addVariable(String name, String default_value){
        addVariable(name, new Variable(default_value));
    }
    static private void addVariable(String name){
        addVariable(name, new Variable());
    }

    static Parameter getParameter(String variable){
        for(Parameter parameter : parameters)
            if(parameter.getComponentVariable().equals(variable))
                return parameter;
        return null;
    }

    // Object

    String name;
    String type;
    String title;
    Project project;

    ArrayList<StyleComponent> child_components = new ArrayList<>();

    HashMap<String, Variable> implemented_variables = new HashMap<>();

    public StyleComponent(String title, String type){
        this.title = title;
        this.type = type;

        addImplementedParameters(Parameters.KIT_BASE);
    }

    public StyleComponent clone(Project project){
        try{
            StyleComponent component = (StyleComponent) super.clone();
            component.project = project;

            // Copying variables
            HashMap<String, Variable> new_variables = new HashMap<>();
            for(Map.Entry<String, Variable> entry : implemented_variables.entrySet())
                new_variables.put(entry.getKey() + "", entry.getValue().clone());
            component.implemented_variables = new_variables;

            return component;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public String getTitle(){
        return title;
    }

    public Project getProject(){
        return project;
    }

    public Parameter[] getParameters(){
        ArrayList<Parameter> param = new ArrayList<>();
        for(Parameter parameter : parameters)
            if(implemented_variables.containsKey(parameter.getComponentVariable()))
                param.add(parameter);
        return param.toArray(new Parameter[0]);
    }

    public XMLHead getXMLStyle(){
        return getXMLStyle(false);
    }
    public XMLHead getXMLStyle(boolean preview){
        XMLHead head = new XMLHead("style");

        if(preview)
            head.addParameter("id", "preview");
        else
            applyParameterOnCustom(head, "id", Parameters.ID);
        head.addParameter("type", type);
        applyParameterOnCustom(head, "extends", Parameters.EXTENDS);
        applyParameterOnCustom(head, "painter.decorations.decoration", "visible", Parameters.DECORATIONS);
        applyParameterOnCustom(head, "painter.decorations", "overwrite", Parameters.OVERWRITE_DECORATIONS);

        if(getVariable(Parameters.SHAPE_ENABLED) != null) {
            if (getVariableValue(Parameters.SHAPE_ENABLED).equals("true")) {
                applyHeadOnCustom(head, "painter.decorations.decoration.WebShape", Parameters.SHAPE_ENABLED);
                applyParameterOnCustom(head, "painter.decorations.decoration.WebShape", "round", Parameters.ROUND);
            }
        }

        applyParameterOnCustom(head, "painter.decorations.decoration.LineBorder", "color", Parameters.BORDER_COLOR);

        if(!areVariablesDefault(Parameters.INNER_SHADOW_WIDTH, Parameters.INNER_SHADOW_COLOR)) {
            head.setHeadByPath("painter.decorations.decoration", new XMLHead("WebShadow") {{
                addParameter("type", "inner");

                if(!isVariableDefault(Parameters.INNER_SHADOW_WIDTH))
                    addParameter("width", getVariableValue(Parameters.INNER_SHADOW_WIDTH));

                if(!isVariableDefault(Parameters.INNER_SHADOW_COLOR))
                    addParameter("color", getVariableValue(Parameters.INNER_SHADOW_COLOR));
            }});
        }

        if(!areVariablesDefault(Parameters.OUTER_SHADOW_WIDTH, Parameters.OUTER_SHADOW_COLOR)) {
            head.setHeadByPath("painter.decorations.decoration", new XMLHead("WebShadow") {{
                addParameter("type", "outer");

                if(!isVariableDefault(Parameters.OUTER_SHADOW_WIDTH))
                    addParameter("width", getVariableValue(Parameters.OUTER_SHADOW_WIDTH));

                if(!isVariableDefault(Parameters.OUTER_SHADOW_COLOR))
                    addParameter("color", getVariableValue(Parameters.OUTER_SHADOW_COLOR));
            }});
        }

        if(getVariable(Parameters.BACKGROUND_TYPE) != null) {
            if (getVariableValue(Parameters.BACKGROUND_TYPE).equals("Gradient")){
                applyParameterOnCustom(head, "painter.decorations.decoration.GradientBackground", "type", Parameters.GRADIENT_TYPE);
                applyParameterOnCustom(head, "painter.decorations.decoration.GradientBackground", "from", Parameters.GRADIENT_FROM);
                applyParameterOnCustom(head, "painter.decorations.decoration.GradientBackground", "to", Parameters.GRADIENT_TO);
            }else{
                head.setParameterByPath("painter.decorations.decoration.ColorBackground", "color", getVariableValue(Parameters.BACKGROUND_COLOR));
            }
        }

        if(!isVariableDefault(Parameters.BUTTON_SHOW_ICON) && getVariableValue(Parameters.BUTTON_SHOW_ICON).equals("true"))
            head.setParameterByPath("painter.decorations.decoration.ButtonLayout.ButtonIcon", "constraints", "icon");
        if(!isVariableDefault(Parameters.BUTTON_SHOW_TEXT) && getVariableValue(Parameters.BUTTON_SHOW_TEXT).equals("true"))
            head.setParameterByPath("painter.decorations.decoration.ButtonLayout.ButtonText", "constraints", "text");

        return head;
    }

    public abstract Component createPreviewComponent();

    public void setVariable(String name, String value){
        implemented_variables.get(name).setValue(value);
        Components.doEvent(Components.ComponentEvent.Style_Changed, this);
    }
    public Variable getVariable(String name){
        return implemented_variables.get(name);
    }
    public String getVariableValue(String name){
        return getVariable(name).getValue();
    }
    public void setCustomValue(String name, String default_value){
        implemented_variables.put(name, new Variable(default_value));
    }

    public void doEvent(Components.ComponentEvent event, Object... objects){
        Components.doEvent(event, this, objects);
    }

    public void addChildComponent(StyleComponent component){
        child_components.add(component);
        doEvent(Components.ComponentEvent.New_Child, component);
    }
    public ArrayList<StyleComponent> getChildComponents(){
        return child_components;
    }
    public void removeChild(StyleComponent component){
        removeChild(getChildComponents().indexOf(component));
    }
    public void removeChild(int index){
        child_components.remove(index);
        doEvent(Components.ComponentEvent.Removed_Child);
    }
    public void moveChildComponent(int from, int to){
        StyleComponent component = child_components.get(from);
        child_components.remove(component);
        child_components.add(to, component);
    }

    public boolean areVariablesDefault(String... variables){
        for(String variable : variables)
            if (implemented_variables.containsKey(variable) && !isVariableDefault(variable))
                return false;
        return true;
    }

    public void applyParameterOnCustom(XMLHead head, String path, String parameter, String variable){
        if(!implemented_variables.containsKey(variable))
            return;
        if(!isVariableDefault(variable))
            head.setParameterByPath(path, new XMLParameter(parameter, getVariableValue(variable)));
    }
    public void applyParameterOnCustom(XMLHead head, String parameter, String variable){
        if(!implemented_variables.containsKey(variable))
            return;
        if(!isVariableDefault(variable))
            head.addParameter(parameter, implemented_variables.get(variable).getValue());
    }
    public void applyHeadOnCustom(XMLHead head, String path, String variable){
        if(!implemented_variables.containsKey(variable))
            return;
        if(!isVariableDefault(variable))
            head.setHeadByPath(path);
    }

    public boolean isVariableDefault(String variable){
        if(!implemented_variables.containsKey(variable))
            return true;
        if(getVariableValue(Parameters.OVERWRITE_DECORATIONS).equals("true"))
            return variables.get(variable).getDefaultValue().equals(getVariableValue(variable));
        return getVariable(variable).isDefaultValue();
    }

    public void addImplementedParameters(String... names){
        for(String name : names)
            if(name.startsWith("@"))
                for(String n : name.substring(1).split(","))
                    addImplementedParameter(n);
            else
                addImplementedParameter(name);
    }
    public void addImplementedParameter(String name){
        implemented_variables.put(name, variables.get(name).clone());
    }
    public boolean isParameterImplemented(String name){
        return implemented_variables.containsKey(name);
    }
}
