package leung.philip.a06united.Model;

/**
 * Created by Sing on 11/22/17.
 */

public class News {

    public String annoDate;
    public String annoTitle;
    public String announcement;

    public News(){

    }

    public News(String annoDate, String annoTitle, String announcement) {
        this.annoDate = annoDate;
        this.annoTitle = annoTitle;
        this.announcement = announcement;
    }

    public String getAnnoDate() {
        return annoDate;
    }

    public void setAnnoDate(String annoDate) {
        this.annoDate = annoDate;
    }

    public String getAnnoTitle() {
        return annoTitle;
    }

    public void setAnnoTitle(String annoTitle) {
        this.annoTitle = annoTitle;
    }

    public String getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
    }
}
