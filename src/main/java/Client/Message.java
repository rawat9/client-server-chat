package Client;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Message implements Serializable {
    private String senderID;
    private String content;
    private String receiverID;
    private Calendar cal;
    private String timestamp;

    public Message(String senderID, String content, String receiverID) {
        this.timestamp = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
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

    public String getTimestamp() {
        return timestamp;
    }
}

