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



    public static class Parameters {
        public static final String ID = "id";
        public static final String EXTENDS = "extends";
        public static final String DECORATIONS = "decorations";
        public static final String ROUND = "round";
        public static final String INNER_SHADOW_WIDTH = "inner_shadow_width";
        public static final String OUTER_SHADOW_WIDTH = "outer_shadow_width";
        public static final String BORDER_COLOR = "border_color";
        public static final String INNER_SHADOW_COLOR = "inner_shadow_color";
        public static final String OUTER_SHADOW_COLOR = "outer_shadow_color";
        public static final String BACKGROUND_TYPE = "background_type";
        public static final String GRADIENT_TYPE = "gradient_type";
        public static final String GRADIENT_FROM = "gradient_from";
        public static final String GRADIENT_TO = "gradient_to";

        private static final String KIT_BASE = getKit(ID, EXTENDS, DECORATIONS);
        public static final String KIT_SHAPE = getKit(ROUND);
        public static final String KIT_INNER_SHADOW = getKit(INNER_SHADOW_COLOR, INNER_SHADOW_WIDTH);
        public static final String KIT_OUTER_SHADOW = getKit(OUTER_SHADOW_COLOR, OUTER_SHADOW_WIDTH);
        public static final String KIT_BORDER = getKit(BORDER_COLOR);
        public static final String KIT_BACKGROUND = getKit(BACKGROUND_TYPE, GRADIENT_TYPE, GRADIENT_FROM, GRADIENT_TO);

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

            // Shape
            new IntegerParameter("Round", Parameters.ROUND, "Shape"),

            // Shadow Inner
            new IntegerParameter("Width", Parameters.INNER_SHADOW_WIDTH, "Inner shadow"),
            new ColorParameter("Color", Parameters.INNER_SHADOW_COLOR, "Inner shadow"),

            // Shadow outer
            new IntegerParameter("Width", Parameters.OUTER_SHADOW_WIDTH, "Outer shadow"),
            new ColorParameter("Color", Parameters.OUTER_SHADOW_COLOR, "Outer shadow"),

            // Border
            new ColorParameter("Border color", Parameters.BORDER_COLOR, "Border"),

            // Background
            new ComboParameter("Background", Parameters.BACKGROUND_TYPE, "Background", new String[]{"Gradient","Color"}),

            new ComboParameter("Gradient type", Parameters.GRADIENT_TYPE, "Background", new String[]{"linear", "radial"}),
            new Point2DParameter("Gradient from", Parameters.GRADIENT_FROM, "Background"),
            new Point2DParameter("Gradient to", Parameters.GRADIENT_TO, "Background")
    };

    private static HashMap<String, Variable> variables = new HashMap<>();

    static {
        addVariable(Parameters.ID);
        addVariable(Parameters.EXTENDS);
        addVariable(Parameters.DECORATIONS, "true");

        // Round
        addVariable(Parameters.ROUND, "0");

        // Background
        addVariable(Parameters.BACKGROUND_TYPE,"Color");

        addVariable(Parameters.GRADIENT_TYPE,"linear");
        addVariable(Parameters.GRADIENT_FROM,"0.0,0.0");
        addVariable(Parameters.GRADIENT_TO,"0.0,1.0");

        // Border
        addVariable(Parameters.BORDER_COLOR,"0,0,0");

        // Shadow inner
        addVariable(Parameters.INNER_SHADOW_WIDTH,"0");
        addVariable(Parameters.INNER_SHADOW_COLOR,"0,0,0");

        // Shadow outer
        addVariable(Parameters.OUTER_SHADOW_COLOR,"0,0,0");
        addVariable(Parameters.OUTER_SHADOW_WIDTH,"0");
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

    public static HashMap<String, Class<? extends StyleComponent>> components = new HashMap<String, Class<? extends StyleComponent>>(){{
        put("Button", Styled_Button.class);
        put("Label", Styled_Label.class);
    }};

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
            for(Map.Entry<String, Variable> entry : variables.entrySet())
                new_variables.put(entry.getKey() + "", entry.getValue().clone());
            component.variables = new_variables;

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
            applyOnCustom(head, "id", Parameters.ID);
        head.addParameter("type", type);
        applyOnCustom(head, "extends", Parameters.EXTENDS);
        applyOnCustom(head, "painter.decorations.decoration", "visible", Parameters.DECORATIONS);


        applyOnCustom(head, "painter.decorations.decoration.WebShape", "round", Parameters.ROUND);
        applyOnCustom(head, "painter.decorations.decoration.LineBorder", "color", Parameters.BORDER_COLOR);

        if(!areVariablesDefault(Parameters.INNER_SHADOW_WIDTH, Parameters.INNER_SHADOW_COLOR)) {
            head.setHeadByPath("painter.decorations.decoration", new XMLHead("WebShadow") {{
                addParameter("type", "inner");

                if(!getVariable(Parameters.INNER_SHADOW_WIDTH).isDefaultValue())
                    addParameter("width", getVariableValue(Parameters.INNER_SHADOW_WIDTH));

                if(!getVariable(Parameters.INNER_SHADOW_COLOR).isDefaultValue())
                    addParameter("color", getVariableValue(Parameters.INNER_SHADOW_COLOR));
            }});
        }

        if(!areVariablesDefault(Parameters.OUTER_SHADOW_WIDTH, Parameters.OUTER_SHADOW_COLOR)) {
            head.setHeadByPath("painter.decorations.decoration", new XMLHead("WebShadow") {{
                addParameter("type", "outer");

                if(!getVariable(Parameters.OUTER_SHADOW_WIDTH).isDefaultValue())
                    addParameter("width", getVariableValue(Parameters.OUTER_SHADOW_WIDTH));

                if(!getVariable(Parameters.OUTER_SHADOW_COLOR).isDefaultValue())
                    addParameter("color", getVariableValue(Parameters.OUTER_SHADOW_COLOR));
            }});
        }

        if(getVariable(Parameters.BACKGROUND_TYPE) != null && getVariableValue(Parameters.BACKGROUND_TYPE).equals("Gradient")) {
            applyOnCustom(head, "painter.decorations.decoration.GradientBackground", "type", Parameters.GRADIENT_TYPE);
            applyOnCustom(head, "painter.decorations.decoration.GradientBackground", "from", Parameters.GRADIENT_FROM);
            applyOnCustom(head, "painter.decorations.decoration.GradientBackground", "to", Parameters.GRADIENT_TO);
        }

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
    public void setCustomVariable(String name, String default_value){
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
            if (isParameterImplemented(variable) && !getVariable(variable).isDefaultValue())
                return false;
        return true;
    }

    public void applyOnCustom(XMLHead head, String path, String parameter, String variable){
        if(!implemented_variables.containsKey(variable))
            return;
        if(!getVariable(variable).isDefaultValue())
            head.setParameterByPath(path, new XMLParameter(parameter, getVariableValue(variable)));
    }
    public void applyOnCustom(XMLHead head, String parameter, String variable){
        if(!implemented_variables.containsKey(variable))
            return;
        if(!getVariable(variable).isDefaultValue())
            head.addParameter(parameter, implemented_variables.get(variable).getValue());
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
