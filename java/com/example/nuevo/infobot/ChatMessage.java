package com.example.nuevo.infobot;

/**
 * Created by shyam on 7/13/2016.
 */
public class ChatMessage {
    public boolean left;
    public String message,time;
    public int imageId;
    public ChatMessage(boolean left, String message,int imageId,String time) {
        super();
        this.left = left;
        this.message = message;
        this.imageId=imageId;
        this.time=time;
    }
}
