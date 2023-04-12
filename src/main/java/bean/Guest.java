package bean;

import java.util.ArrayList;

public class Guest {
    private int guestListId;
    private String guestName,guestEmail;


    public Guest() {
    }

    public int getGuestListId() {
        return guestListId;
    }

    public void setGuestListId(int guestListId) {
        this.guestListId = guestListId;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getGuestEmail() {
        return guestEmail;
    }

    public void setGuestEmail(String guestEmail) {
        this.guestEmail = guestEmail;
    }
}
