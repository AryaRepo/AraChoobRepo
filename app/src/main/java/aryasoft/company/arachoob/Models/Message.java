package aryasoft.company.arachoob.Models;

public class Message {

    private int userId;
    private int messageId;
    private String messageText;

    public int getUserId() {
        return userId;
    }

    public int getMessageId() {
        return messageId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
}
