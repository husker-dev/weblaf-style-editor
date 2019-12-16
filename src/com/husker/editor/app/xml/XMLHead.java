package com.husker.editor.app.xml;

import com.husker.editor.app.window.tools.CharUtils;

import java.util.*;

public class XMLHead {

    private ArrayList<XMLHead> heads = new ArrayList<>();
    private LinkedHashMap<String, XMLParameter> parameters = new LinkedHashMap<>();
    private String name;
    private String value;
    private String commentaries;

    public XMLHead(String name){
        this.name = name;
    }

    public XMLHead(){
        this("head");
    }

    public void addHead(XMLHead head){
        if(head == null)
            return;
        heads.add(head);
    }

    public ArrayList<String> getHeadsNames(){
        ArrayList<String> heads_names = new ArrayList<>();
        for(XMLHead head : this.heads)
            heads_names.add(head.name);
        return heads_names;
    }
    public XMLHead getHead(String name){
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

    public void setParameterByPath(String path, String parameter, String value){
        this.setParameterByPath(path, new XMLParameter(parameter, value));
    }
    public void setParameterByPath(String path, XMLParameter parameter){
        String[] heads = path.split("\\.");

        XMLHead current_head = this;
        for(String head : heads){
            if(!current_head.contains(head))
                current_head.addHead(new XMLHead(head));
            current_head = current_head.getHead(head);
        }
        current_head.addParameter(parameter);
    }

    public void setHeadByPath(String path, XMLHead new_head){
        String[] heads = path.split("\\.");

        XMLHead current_head = this;
        for(String head : heads){
            if(!current_head.contains(head))
                current_head.addHead(new XMLHead(head));
            current_head = current_head.getHead(head);
        }
        current_head.addHead(new_head);
    }
    public void setHeadByPath(String path){
        String[] heads = path.split("\\.");

        XMLHead current_head = this;
        for(String head : heads){
            if(!current_head.contains(head))
                current_head.addHead(new XMLHead(head));
            current_head = current_head.getHead(head);
        }
    }

    public void createHeadPath(String path){
        String[] heads = path.split("\\.");

        XMLHead current_head = this;
        for(String head : heads){
            if(!current_head.contains(head))
                current_head.addHead(new XMLHead(head));
            current_head = current_head.getHead(head);
        }
    }

    public void addParameter(XMLParameter parameter){
        parameters.put(parameter.getName(), parameter);
    }
    public void addParameter(String name, String value){
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

    public void setValue(String value){
        this.value = value;
    }
    public String getValue(){
        return value;
    }
    public boolean hasValue(){
        return value != null;
    }

    public void setCommentaries(String commentaries){
        this.commentaries = commentaries;
    }
    public String getCommentaries(){
        return commentaries;
    }

    public static XMLHead[] getHeadsFromString(String text){

        return null;
    }

    public static XMLHead fromString(String text, String comments){
        /*
            <!-- Comments -->
            <head parameter"value">
                <content/>
            </head>
         */
        XMLHead out;

        String head = text.split(">")[0];
        boolean full = !head.endsWith("/");
        if(!full)
            head = head.substring(0, head.length() - 1);

        String name;
        if(head.contains(" "))
            name = text.split(" ")[0].substring(1);
        else if(full)
            name = text.split(">")[0].substring(1);
        else
            name = text.split("/>")[0].substring(1);
        String params = head.replace("<" + name + " ", "");

        out = new XMLHead(name);

        for(int i = 0; i < params.split("\"").length; i += 2){
            if(params.split("\"").length - i < 2)
                break;
            String parameter_name = params.split("\"")[i].trim().substring(0, params.split("\"")[i].trim().length() - 1).trim();
            String parameter_value = params.split("\"")[i + 1].trim();
            out.addParameter(parameter_name, parameter_value);
        }

        if(full){
            // replacing closing head
            for(int i = text.length(); i >= 0; i--) {
                if (text.substring(i).startsWith("</" + name)) {
                    text = text.substring(0, i);
                    break;
                }
            }
            text = text.replaceFirst(head + ">", "");

            if(!text.contains("<") && !text.contains(">")) {
                out.setValue(text);
                return out;
            }

            char[] chars = text.toCharArray();
            String last_comment = "";
            boolean last_comment_applied = true;

            for(int i = 0; i < chars.length; i++){

                // Commentaries
                if(CharUtils.isText(chars, i, "<!--")){
                    while(CharUtils.isText(chars, i, "-->")) {
                        i++;
                        last_comment += chars[i];
                    }
                    i += 3;
                    last_comment_applied = false;
                }

                if(chars[i] == '<'){
                    String content = "<";

                    int to_close = 0;
                    while(true){

                        if(CharUtils.isText(chars, i, "<")) {
                            if(CharUtils.isText(chars, i, "</")) {
                                to_close--;

                                if(to_close == 0) {
                                    while(chars[i] != '>') {
                                        i++;
                                        content += chars[i];
                                    }
                                    break;
                                }

                            }else
                                to_close++;
                        }
                        if(CharUtils.isText(chars, i, "/>")) {
                            to_close--;
                            if(to_close == 0) {
                                content += chars[i + 1];
                                break;
                            }
                        }

                        i++;
                        content += chars[i];
                    }

                    XMLHead new_head;
                    if(!last_comment_applied) {
                        new_head = XMLHead.fromString(content, last_comment);
                        last_comment_applied = true;
                    }else
                        new_head = XMLHead.fromString(content, "");
                    out.addHead(new_head);
                }
            }
        }


        return out;
    }
}
