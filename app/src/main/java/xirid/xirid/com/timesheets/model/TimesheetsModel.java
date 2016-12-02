package xirid.xirid.com.timesheets.model;

/**
 * Created by yasar on 25/11/16.
 */
public class TimesheetsModel {

    private String name, startDate, endDate, hours, status;


    public TimesheetsModel(String startDate, String endDate, String hours, String status) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.hours = hours;
        this.status = status;
    }

    public TimesheetsModel(String name, String startDate, String endDate, String hours, String status) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.hours = hours;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
