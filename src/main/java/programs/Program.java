package programs;

import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;
import pachet.MonitoredData;
import pachet.ReadFile;
import pachet.WriteFiles;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by radu on 21.05.2017.
 */
public class Program {

    public static void main(String[] args) throws IOException {
        ReadFile rf = new ReadFile("input/OrdonezA_ADLs.txt");
        WriteFiles wf = new WriteFiles("output/");


        // 1. Count the distinct days that appear in the monitoring data.

        System.out.println(
        rf.getMonitoredDatas().stream()
                .map(e -> e.getStartTime().getDayOfYear())
                .distinct()
                .count());


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        // 2. Determine a map of type <String, Integer> that maps to each distinct action type the number of
        // occurrences in the log. Write the resulting map into a text file.

        Map<String,Long> distinctActionsMapLong =  rf.getMonitoredDatas().stream()
                .collect(Collectors.groupingBy(
                        MonitoredData::getActivityLabel,
                        Collectors.counting()
                ));
        System.out.println("\n" + distinctActionsMapLong + "\n");

        Map<String,Integer> distinctActionsMapInteger =  rf.getMonitoredDatas().stream()
                .collect(Collectors.groupingBy(
                        MonitoredData::getActivityLabel,
                        Collectors.reducing(0,e -> 1, Integer::sum)
                ));


        wf.writeNoOfDistinctActionsInFile(distinctActionsMapInteger,"2.txt");



////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        // 3. Generates a data structure of type Map<Integer, Map<String, Integer>> that contains the activity
        // count for each day of the log (task number 2 applied for each day of the log) and writes the result
        // in a text file.


        // the main map key is the number of day in month
        Map<Integer, Map<String, Integer>> activitiesEachDayMap =
            rf.getMonitoredDatas().stream()
                .collect(Collectors.groupingBy(
                        e -> e.getStartTime().getDayOfMonth(),
                        Collectors.groupingBy(
                                MonitoredData::getActivityLabel,
                                Collectors.reducing(0, e -> 1, Integer::sum)
                        )));

        wf.writeDistinctActionsEachDaysFile(activitiesEachDayMap,"3.txt");

        System.out.println(activitiesEachDayMap + "\n");



////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        // 4. Determine a data structure of the form Map<String, DateTime> that maps for each activity the total
        // duration computed over the monitoring period. Filter the activities with total duration larger than
        // 10 hours. Write the result in a text file.


        Map <String,List<Seconds>> test =
                rf.getMonitoredDatas().stream()
                .collect(Collectors.groupingBy(MonitoredData::getActivityLabel,
                        Collectors.mapping(e -> {
                                return Seconds.secondsBetween(e.getStartTime(), e.getEndTime());
                            },
                            Collectors.toList())));



        Map<String,Hours> totalDurationOfActivitiesMap = rf.getMonitoredDatas().stream()
                .collect(Collectors.groupingBy(
                        MonitoredData::getActivityLabel,
                        Collectors.reducing(
                                Seconds.ZERO,
                                e -> {
                                    return Seconds.secondsBetween(e.getStartTime(),e.getEndTime());
                                },
                                (t1,t2) -> {
                                    return t1.plus(t2);
                                }
                        )
                )).entrySet().stream()
                .filter(e -> e.getValue().compareTo(Seconds.seconds(36000)) > 0)
//                .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));           // this displays it in seconds
                .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue().toStandardHours()));   // this displays it in hours

        wf.writeTotalDurationOfActivitiesInFile(totalDurationOfActivitiesMap,"4.txt");


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        // 5. Filter the activities that have 90% of the monitoring samples with duration less than 5 minutes,
        // collect the results in a List<String> containing only the distinct activity names and write the result
        // in a text file.



        Map<String,List<MonitoredData>> lessThanFiveMinActivities =
            rf.getMonitoredDatas().stream()
                .filter(e -> isLessThanFive(e))
                .collect(Collectors.groupingBy(
                        MonitoredData::getActivityLabel,
                        Collectors.toList()
                ));


        // contains all the activities names where there are activities less than 5 minutes (no 90% check)
        List<String> lessThanFive =
        rf.getMonitoredDatas().stream()
                .filter(e -> isLessThanFive(e))
                .collect(Collectors.groupingBy(
                        MonitoredData::getActivityLabel,
                        Collectors.toList()
                )).entrySet()
                .stream()
                .map(e -> e.getKey())
                .collect(Collectors.toList());

        wf.writeLessThanFiveMinutes(lessThanFive,"5.txt");

        ////////////////////////////    work in progress    /////////////////////////////////

        // Map< String, Map< String, List<MonitoredDate> >
        rf.getMonitoredDatas().stream()
                .collect(Collectors.groupingBy(
                        MonitoredData::getActivityLabel,
                        Collectors.groupingBy(
                                e -> {
                                    if(isLessThanFive(e)) return "less";
                                    else return "more";
                                }
                        )
                ));

        Map <String,Long> cici =
                rf.getMonitoredDatas().stream()
//                    .filter(e -> e.getActivityLabel().equals("Grooming"))
                    .collect(Collectors.groupingBy(
                            MonitoredData::getActivityLabel,
                            Collectors.groupingBy(e -> {
                                if(isLessThanFive(e)) return "less";
                                else return "more";
                            },Collectors.counting())
                    )).entrySet().stream()
                .map(e -> e.getValue())
                .findFirst().get();



        System.out.println("cici");
    }


    private static boolean isLessThanFive(MonitoredData e) {
        if(Minutes.minutesBetween(e.getStartTime(),e.getEndTime()).compareTo(Minutes.minutes(5)) < 0){
            return true;
        }
        return false;
    }


}
