package xirid.xirid.com.timesheets.model;

/**
 * Created by yasar on 25/11/16.
 */
public class Projects {
    private String projectName, billablenonbillable;

    public Projects(String projectName, String billablenonbillable) {
        this.projectName = projectName;
        this.billablenonbillable = billablenonbillable;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDescription() {
        return billablenonbillable;
    }

    public void setDescription(String billablenonbillable) {
        this.billablenonbillable = billablenonbillable;
    }
}
