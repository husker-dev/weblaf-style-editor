package app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class XMLHead {

    ArrayList<XMLHead> heads = new ArrayList<>();
    HashMap<String, XMLParameter> parameters = new HashMap<>();
    String name;

    public XMLHead(String name){
        this.name = name;
    }

    public XMLHead(){
        this("head");
    }

    public void addXMLHead(XMLHead head){
        heads.add(head);
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
