package dataAccessLayer;

import org.joda.time.DateTime;

/**
 * Created by radu on 21.05.2017.
 */
public class MonitoredData {

    private DateTime startTime;
    private DateTime endTime;
    private String activityLabel;

    public MonitoredData(DateTime startTime, DateTime endTime, String activityLabel) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.activityLabel = activityLabel;
    }

    public DateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(DateTime startTime) {
        this.startTime = startTime;
    }

    public DateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(DateTime endTime) {
        this.endTime = endTime;
    }

    public String getActivityLabel() {
        return activityLabel;
    }

    public void setActivityLabel(String activityLabel) {
        this.activityLabel = activityLabel;
    }

}
