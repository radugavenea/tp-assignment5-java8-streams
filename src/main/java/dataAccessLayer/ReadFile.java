package dataAccessLayer;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

/**
 * Created by radu on 21.05.2017.
 */
public class ReadFile {

    private List<MonitoredData> monitoredDatas = new ArrayList<>();
    DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

    public ReadFile(String filename) {
        readAsStream(filename);
    }


    private void readAsStream(String filename){
        try {
            Stream<String> stream = Files.lines(Paths.get(filename));
            stream.forEach(line -> addToMonitoredDatas(line));

//            stream.map(line -> line.split("\t"))
//                    .filter(e -> !e.equals(""))
//                    .distinct()
//                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void addToMonitoredDatas(String line){
        List<String> strings = new LinkedList<>(Arrays.asList(line.split("\t")));
        strings.removeIf(e -> e.equals(""));
        DateTime startTime = formatter.parseDateTime(strings.get(0));
        DateTime endTime = formatter.parseDateTime(strings.get(1));
        String activity = strings.get(2);
        MonitoredData md = new MonitoredData(startTime, endTime, activity);
        monitoredDatas.add(md);
    }


    public List<MonitoredData> getMonitoredDatas() {
        return monitoredDatas;
    }

    public void setMonitoredDatas(List<MonitoredData> monitoredDatas) {
        this.monitoredDatas = monitoredDatas;
    }
}
