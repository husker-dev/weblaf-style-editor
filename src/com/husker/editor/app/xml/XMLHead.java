package com.husker.editor.app.xml;

import java.util.*;

public class XMLHead {

    ArrayList<XMLHead> heads = new ArrayList<>();
    LinkedHashMap<String, XMLParameter> parameters = new LinkedHashMap<>();
    String name;

    public XMLHead(String name){
        this.name = name;
    }

    public XMLHead(){
        this("head");
    }

    public void addXMLHead(XMLHead head){
        if(head == null)
            return;
        heads.add(head);
    }

    public ArrayList<String> getXMLHeadsNames(){
        ArrayList<String> heads_names = new ArrayList<>();
        for(XMLHead head : this.heads)
            heads_names.add(head.name);
        return heads_names;
    }
    public XMLHead getXMLHead(String name){
        for(XMLHead head : heads)
            if(head.name.equals(name))
                return head;
        return null;
    }
    public boolean contains(String head_name){
        for(XMLHead head : heads)
            if(head.name.equals(head_name))
                return true;
        return false;
    }

    public void setParameterByPath(String path, XMLParameter parameter){
        String[] heads = path.split("\\.");

        XMLHead current_head = this;
        for(String head : heads){
            if(!current_head.contains(head))
                current_head.addXMLHead(new XMLHead(head));
            current_head = current_head.getXMLHead(head);
        }
        current_head.addXMLParameter(parameter);
    }

    public void setHeadByPath(String path, XMLHead new_head){
        String[] heads = path.split("\\.");

        XMLHead current_head = this;
        for(String head : heads){
            if(!current_head.contains(head))
                current_head.addXMLHead(new XMLHead(head));
            current_head = current_head.getXMLHead(head);
        }
        current_head.addXMLHead(new_head);
    }

    public void addXMLParameter(XMLParameter parameter){
        parameters.put(parameter.getName(), parameter);
    }
    public void addXMLParameter(String name, String value){
        XMLParameter parameter = new XMLParameter(name, value);
        parameters.put(parameter.getName(), parameter);
    }

    public String toString(){
        return toString(0);
    }

    public String toString(int tab){
        String tab_str = "";
        for (int i = 0; i < tab; i++)
            tab_str += "    ";

        String line_head = tab_str + "<" + name + "";
        for(Map.Entry<String, XMLParameter> entry : parameters.entrySet())
            line_head += " " + entry.getValue().toString();

        if(heads.size() == 0)
            return line_head + " />\n";

        line_head += ">\n";
        String line_content = "";
        for(XMLHead head : heads)
            line_content += head.toString(tab + 1);

        String line_end = tab_str + "</" + name + ">\n";

        return line_head + line_content + line_end;
    }
}
