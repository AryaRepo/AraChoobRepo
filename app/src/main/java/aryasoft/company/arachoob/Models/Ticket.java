package aryasoft.company.arachoob.Models;

public class Ticket {

    private int UserId;
    private String MessageTitle;
    private String MessageText;


    public int getUserId() {
        return UserId;
    }

    public String getMessageTitle() {
        return MessageTitle;
    }

    public String getMessageText() {
        return MessageText;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public void setMessageTitle(String messageTitle) {
        MessageTitle = messageTitle;
    }

    public void setMessageText(String messageText) {
        MessageText = messageText;
    }
}
