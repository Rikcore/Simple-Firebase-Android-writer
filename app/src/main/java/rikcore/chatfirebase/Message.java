package rikcore.chatfirebase;

import java.io.Serializable;

/**
 * Created by rikcore on 30/04/2018.
 */

public class Message implements Serializable {

    String id;
    String content;

    public Message(){

    }

    public Message (String id, String content){
        this.id = id;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
