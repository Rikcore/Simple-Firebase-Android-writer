package rikcore.chatfirebase;

import java.io.Serializable;

/**
 * Created by rikcore on 04/05/2018.
 */

public class Message implements Serializable {

    String color;
    int x;
    int y;
    String message;

    public Message(){

    }

    public Message(String color, int x, int y){
        this.color = color;
        this.x = x;
        this.y =y;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }
}
