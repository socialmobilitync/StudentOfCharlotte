package pritam.com.studentofcharlotte;

import java.util.List;

/**
 * Created by LibraryGuest2 on 7/16/2016.
 */
public class Notification {

    String notifDesc,notifId;
    boolean isRead;

    public String getNotifDesc() {
        return notifDesc;
    }

    public void setNotifDesc(String notifDesc) {
        this.notifDesc = notifDesc;
    }

    public String getNotifId() {
        return notifId;
    }

    public void setNotifId(String notifId) {
        this.notifId = notifId;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}

