import java.util.Date;

/**
 * Created by radu on 21.05.2017.
 */
public class MonitoredData {

    private Date startTime;
    private Date endTime;
    private String activityLabel;

    public MonitoredData(Date startTime, Date endTime, String activityLabel) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.activityLabel = activityLabel;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getActivityLabel() {
        return activityLabel;
    }

    public void setActivityLabel(String activityLabel) {
        this.activityLabel = activityLabel;
    }
}
