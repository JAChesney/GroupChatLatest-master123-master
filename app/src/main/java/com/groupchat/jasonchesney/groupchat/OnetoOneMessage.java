package com.groupchat.jasonchesney.groupchat;

class OnetoOneMessage {

    private String message;
    private String cname;
    private String Userid;

    public OnetoOneMessage() {}

    public OnetoOneMessage(String message, String cname, String Userid) {
        this.message = message;
        this.cname = cname;
        this.Userid = Userid;
    }

    public String getMessage() {
        return message;
    }

    public String getCname() {
        return cname;
    }

    public String getUserid() {
        return Userid;
    }
}

