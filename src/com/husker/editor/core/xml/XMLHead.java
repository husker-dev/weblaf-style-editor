package com.husker.editor.core.xml;

import com.husker.editor.core.tools.CharUtils;

import java.util.*;
import java.util.function.Predicate;

public class XMLHead {

    private ArrayList<XMLHead> heads = new ArrayList<>();
    private LinkedHashMap<String, XMLParameter> parameters = new LinkedHashMap<>();
    private String name;
    private String value;
    private ArrayList<String> comments = new ArrayList<>();

    public XMLHead(String name, String content){
        this.name = name;
        this.value = content;
    }
    public XMLHead(String name, String[]... parameters){
        this.name = name;
        for(String[] parameter : parameters)
            addParameter(parameter[0], parameter[1]);
    }
    public XMLHead(String[]... parameters){
        this("head", parameters);
    }

    public void addHead(XMLHead head){
        if(head == null)
            return;
        heads.add(head);
    }

    public String getName(){
        return name;
    }
    public XMLParameter[] getParameters(){
        ArrayList<XMLParameter> out = new ArrayList<>();
        for(Map.Entry<String, XMLParameter> entry : parameters.entrySet())
            out.add(entry.getValue());
        return out.toArray(new XMLParameter[0]);
    }

    public String[] getHeadsNames(){
        ArrayList<String> heads_names = new ArrayList<>();
        for(XMLHead head : getHeads())
            heads_names.add(head.name);
        return heads_names.toArray(new String[0]);
    }
    public XMLHead[] getHeads(){
        return heads.toArray(new XMLHead[0]);
    }

    public XMLHead[] getHeadsByName(String name){
        return getHeadsByName(name, null);
    }
    public XMLHead[] getHeadsByName(String name, Predicate<XMLHead> predicate){
        ArrayList<XMLHead> out = new ArrayList<>();
        for(XMLHead head : getHeads())
            if(head.getName().equals(name))
                if(predicate == null || predicate.test(head))
                    out.add(head);
        return out.toArray(new XMLHead[0]);
    }

    public XMLHead getHeadByName(String name){
        return getHeadByName(name, null);
    }
    public XMLHead getHeadByName(String name, Predicate<XMLHead> predicate){
        XMLHead[] heads = getHeadsByName(name, predicate);
        if(heads.length > 1)
            throw new RuntimeException("Predicate applies to more than two XMLHead");
        else
            return heads[0];
    }

    public void setParameterByPath(String path, String parameter, String value){
        this.setParameterByPath(path, new XMLParameter(parameter, value));
    }
    public void setParameterByPath(String path, XMLParameter parameter){
        createHeadByPath(path).addParameter(parameter);
    }

    public XMLHead createHeadByPath(String path, XMLHead new_head){
        String[] heads = path.split("\\.");

        XMLHead current_head = this;
        for(String head : heads){
            if(!current_head.containsHead(head))
                current_head.addHead(new XMLHead(head));
            current_head = current_head.getHeadsByName(head)[0];
        }
        current_head.addHead(new_head);
        return new_head;
    }
    public XMLHead createHeadByPath(String path){
        String[] heads = path.split("\\.");

        XMLHead current_head = this;
        for(String head : heads){
            if(!current_head.containsHead(head))
                current_head.addHead(new XMLHead(head));
            current_head = current_head.getHeadsByName(head)[0];
        }
        return current_head;
    }

    public boolean containsParameter(String path, String parameter){
        return getParameterByPath(path, parameter, null) != null;
    }
    public boolean containsHead(String head_name){
        return containsHead(head_name, null);
    }
    public boolean containsHead(String head_name, Predicate<XMLHead> predicate){
        for(XMLHead head : getHeads())
            if(head.getName().equals(head_name))
                return true;
        return false;
    }
    public boolean containsHeadByPath(String path, Predicate<XMLHead> predicate){
        String[] heads_names = path.split("\\.");
        String[] child_path = Arrays.copyOfRange(heads_names, 0, heads_names.length - 1);
        String head_name = path.split("\\.")[path.split("\\.").length - 1];

        XMLHead current_head = this;
        for(int i = 0; i < child_path.length; i++){
            String head = child_path[i];
            if(!current_head.containsHead(head))
                return false;
            current_head = current_head.getHeadsByName(head)[0];
        }
        return current_head.containsHead(head_name, predicate);
    }

    public XMLParameter getParameterByPath(String path, String parameter){
        return getParameterByPath(path, parameter, null);
    }
    public XMLParameter getParameterByPath(String path, String parameter, Predicate<XMLHead> predicate){
        return getHeadByPath(path, predicate).getParameter(parameter);
    }

    public XMLHead[] getHeadsByPath(String path){
        return getHeadsByPath(path, null);
    }
    public XMLHead[] getHeadsByPath(String path, Predicate<XMLHead> predicate){

        String[] heads_names = path.split("\\.");
        String[] child_path = Arrays.copyOfRange(heads_names, 0, heads_names.length - 1);
        String head_name = path.split("\\.")[path.split("\\.").length - 1];

        XMLHead current_head = this;
        for(int i = 0; i < child_path.length; i++){
            String head = child_path[i];
            if(!current_head.containsHead(head))
                return null;
            current_head = current_head.getHeadsByName(head)[0];
        }

        return current_head.getHeadsByName(head_name, predicate);
    }

    public XMLHead getHeadByPath(String path){
        return getHeadByPath(path, null);
    }
    public XMLHead getHeadByPath(String path, Predicate<XMLHead> predicate){
        XMLHead[] heads = getHeadsByPath(path, predicate);
        if(heads.length > 1)
            throw new RuntimeException("Predicate applies to more than two XMLHead");
        else if(heads.length == 0)
            return null;
        else
            return heads[0];
    }

    public void addParameter(XMLParameter parameter){
        parameters.put(parameter.getName(), parameter);
    }
    public void addParameter(String name, String value){
        XMLParameter parameter = new XMLParameter(name, value);
        parameters.put(parameter.getName(), parameter);
    }
    public XMLParameter getParameter(String name){
        return parameters.get(name);
    }
    public String getParameterValue(String name){
        return parameters.get(name).getValue();
    }

    public String toString(){
        return toString(0);
    }
    public String toString(int tab){
        String tab_str = "";
        for (int i = 0; i < tab; i++)
            tab_str += "    ";

        String line_comments = "";
        for(String comment : getComments())
            line_comments += tab_str + "<!--" + comment + "-->\n";

        String line_head = tab_str + "<" + getName() + "";
        for(XMLParameter parameter : getParameters())
            line_head += " " + parameter.toString();

        if(getHeads().length == 0 && getValue() == null)
            return line_comments + line_head + " />\n";

        String line_content = "";
        String line_end;
        if(getValue() == null) {
            line_head += ">\n";
            line_end = tab_str + "</" + getName() + ">\n";
            for (XMLHead head : getHeads())
                line_content += head.toString(tab + 1);
        }else {
            line_head += ">";
            line_end = "</" + getName() + ">\n";
            line_content = getValue();
        }

        return line_comments + line_head + line_content + line_end;
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

    public void addComment(String comment){
        this.comments.add(comment);
    }
    public String[] getComments(){
        return comments.toArray(new String[0]);
    }

    public static XMLHead fromString(String text){
        return fromString(text, new String[0]);
    }
    private static XMLHead fromString(String text, String[] comments){
        XMLHead out = null;

        ArrayList<String> comments_list = new ArrayList<>();
        Collections.addAll(comments_list, comments);
        if(text.isEmpty())
            return null;
        text = text.trim();

        if(comments_list.size() == 0) {
            for (int i = 0; ; i++) {
                char[] chars = text.toCharArray();
                if (CharUtils.isText(chars, i, "<!--")) {
                    String comment = "";
                    i += 4;
                    while (!CharUtils.isText(chars, i, "-->")) {
                        comment += chars[i];
                        i++;
                    }
                    i += 2;
                    comments_list.add(comment);
                    continue;
                }
                if(CharUtils.isText(chars, i, "<")) {
                    text = text.substring(i);
                    break;
                }
            }
        }
        if(!text.contains(">"))
            throw new RuntimeException();

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
        for(String comment : comments_list)
            out.addComment(comment);

        for(int i = 0; i < params.split("\"").length; i += 2){
            if(params.split("\"").length - i < 2)
                break;
            String parameter_name = params.split("\"")[i].trim().substring(0, params.split("\"")[i].trim().length() - 1).trim();
            String parameter_value = params.split("\"")[i + 1].trim();
            out.addParameter(parameter_name, parameter_value);
        }

        if(full){
            // replacing closing head
            boolean replaced = false;
            for(int i = text.length(); i >= 0; i--) {
                if (text.substring(i).startsWith("</" + name)) {
                    text = text.substring(0, i);
                    replaced = true;
                    break;
                }
            }
            if(!replaced)
                throw new RuntimeException();

            text = text.replaceFirst(head + ">", "");
            text = text.replaceAll("\\s+"," ");

            if(!text.contains("<") && !text.contains(">")) {
                if(!text.isEmpty())
                    out.setValue(text);
                return out;
            }

            char[] chars = text.toCharArray();
            ArrayList<String> last_comments = new ArrayList<>();
            boolean last_comment_applied = true;

            //System.out.println(text);

            for(int i = 0; i < chars.length; i++){

                // Commentaries
                if(CharUtils.isText(chars, i, "<!--")){
                    String comment = "";
                    i += 4;
                    while (!CharUtils.isText(chars, i, "-->")) {
                        comment += chars[i];
                        i++;
                    }
                    i += 2;
                    last_comments.add(comment);
                    last_comment_applied = false;
                }

                if(chars[i] == '<'){
                    String content = "<";

                    int to_close = 0;
                    while(true){

                        if(CharUtils.isText(chars, i, "<") && !CharUtils.isText(chars, i, "<!--")) {
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
                                content += chars[i + 2];
                                i+= 2;
                                break;
                            }
                        }

                        i++;
                        content += chars[i];
                    }

                    if(!content.contains(">") && !content.contains("<")){
                        out.setValue(content);
                    }else {
                        XMLHead new_head;
                        if (!last_comment_applied) {
                            new_head = XMLHead.fromString(content, last_comments.toArray(new String[0]));
                            last_comment_applied = true;
                        } else
                            new_head = XMLHead.fromString(content, new String[0]);
                        out.addHead(new_head);
                    }
                }
            }
        }
        return out;
    }
}
