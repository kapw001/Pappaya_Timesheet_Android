package xirid.xirid.com.timesheets.model;

/**
 * Created by yasar on 25/11/16.
 */
public class AddDates {

    private String dates, hours, comments;

    public AddDates(String dates, String hours, String comments) {
        this.dates = dates;
        this.hours = hours;
        this.comments = comments;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
