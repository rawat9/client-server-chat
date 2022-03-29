package Client;

import java.io.Serializable;

public class Message implements Serializable {
    private String senderID;
    private String content;
    private String receiverID;

    public Message(String senderID, String content, String receiverID) {
        this.senderID = senderID;
        this.content = content;
        this.receiverID = receiverID;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }
}
