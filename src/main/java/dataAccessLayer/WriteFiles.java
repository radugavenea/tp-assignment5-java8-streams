package dataAccessLayer;

import org.joda.time.Hours;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by radu on 22.05.2017.
 */
public class WriteFiles {

    private String folderName;

    public WriteFiles(String folderName) {
        this.folderName = folderName;
    }


    // for 2.
    public void writeNoOfDistinctActionsInFile(Map<String, Integer> distinctActionsMap,String filename) throws IOException {
        createFile(filename);
        FileWriter writer = new FileWriter(folderName+filename);

            distinctActionsMap.entrySet().stream()
                    .forEach(e -> {
                        try {
                            writer.append(e.toString() + "\n");
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    });

        writer.close();

    }


    // for 3.
    public void writeDistinctActionsEachDaysFile(Map<Integer, Map<String, Integer>> actionsEachDayMap,String filename) throws IOException {
        createFile(filename);
        FileWriter writer = new FileWriter(folderName + filename);

        actionsEachDayMap.entrySet().stream()
                .forEach(e -> {
                    try {
                        writer.append(e.toString() + "\n");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                });

        writer.close();
    }


    // for 4.
    public void writeTotalDurationOfActivitiesInFile(Map<String, Hours> totalDurationOfActivitiesMap, String filename) throws IOException {
        createFile(filename);
        FileWriter writer = new FileWriter(folderName + filename);

        totalDurationOfActivitiesMap.entrySet().stream()
                .forEach(e -> {
                    try {
                        writer.append(e.toString() + "\n");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                });
        writer.close();
    }


    // for 5.
    public void writeLessThanFiveMinutes(List<String> lessThanFive, String filename) throws IOException {
        createFile(filename);
        FileWriter writer = new FileWriter(folderName + filename);

        lessThanFive.stream()
                .forEach(e -> {
                    try {
                        writer.append(e.toString() + "\n");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                });
        writer.close();
    }


    private void createFile(String filename) throws IOException {
        File file = new File(folderName + filename);
        file.getParentFile().mkdirs();
        FileWriter writer = new FileWriter(file);
    }

}
