package com.example.mandaleeyp.teamrawrapp.models;

/**
 * Created by rdicker on 7/13/17.
 */

public class Msg {

    private String senderID;
    private String receiverID;
    private String body;
    private String timeSent;
    private boolean readByRecipient;
//    private Request request;

    public Msg(String sender, String receiver, String m, String sentTime) {
        senderID = sender;
        receiverID = receiver;
        body = m;
        timeSent = sentTime;
        readByRecipient = false;
    }

//    public Msg(String user, String m, String sentTime, Request myRequest) {
//        userID = user;
//        body = m;
//        timeSent = sentTime;
//        readByRecipient = false;
//        request = myRequest;
//    }


    public String getSenderID() {
        return senderID;
    }

    public String getReceiverID() {
        return receiverID;
    }

    public String getBody() {
        return body;
    }

    public String getTimeSent() {
        return timeSent;
    }

    public boolean isReadByRecipient() {
        return readByRecipient;
    }
}
