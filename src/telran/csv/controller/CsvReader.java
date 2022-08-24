package telran.csv.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CsvReader {
    private static final String FILE_NAME = "train.csv";

    public static void main(String[] args) throws FileNotFoundException {

        double total = totalFares(FILE_NAME);
        System.out.println("Total Fares: " + String.format("%.2f", total));

        double averageFareClass1 = avarageClassFare(FILE_NAME, "1");
        System.out.println("Average Fare for Class 1: " + String.format("%.2f", averageFareClass1));

        double averageFareClass2 = avarageClassFare(FILE_NAME, "2");
        System.out.println("Average Fare for Class 2: " + String.format("%.2f", averageFareClass2));

        double averageFareClass3 = avarageClassFare(FILE_NAME, "3");
        System.out.println("Average Fare for Class 3: " + String.format("%.2f", averageFareClass3));


        double notSurvived = deadOrAlive(FILE_NAME, "dead");
        System.out.println("Total not survived: " + String.format("%.0f", notSurvived));

        double survived = deadOrAlive(FILE_NAME, "alive");
        System.out.println("Total Survived: " + String.format("%.0f", survived));

        double menNotSurvived = deadOrAliveByType(FILE_NAME, "dead", "men");
        System.out.println("Total men not survived: " + String.format("%.0f", menNotSurvived));

        double menSurvived = deadOrAliveByType(FILE_NAME, "alive", "men");
        System.out.println("Total men survived: " + String.format("%.0f", menSurvived));

        double womenNotSurvived = deadOrAliveByType(FILE_NAME, "dead", "women");
        System.out.println("Total women not survived: " + String.format("%.0f", womenNotSurvived));

        double womenSurvived = deadOrAliveByType(FILE_NAME, "alive", "women");
        System.out.println("Total women survived: " + String.format("%.0f", womenSurvived));

        double kidsNotSurvived = deadOrAliveByType(FILE_NAME, "dead", "children");
        System.out.println("Total children not survived: " + String.format("%.0f", kidsNotSurvived));

        double kidsSurvived = deadOrAliveByType(FILE_NAME, "alive", "children");
        System.out.println("Total children survived: " + String.format("%.0f", kidsSurvived));


    }


    public static double deadOrAliveByType(String file, String deadOrAlive, String type) throws FileNotFoundException {
        List<List<String>> lines = csvReader(file);
        lines.remove(0);

        if (deadOrAlive.equals("dead") & type.equals("men")) {

            long count = lines.stream()
                    .collect(Collectors.toList()).stream()
                    .filter(c -> c.get(1).equals("0"))
                    .filter(c -> c.get(5).equals("male"))
                    .filter(c -> c.get(6).length() != 0)
                    .filter(c -> Double.parseDouble(c.get(6)) > 18)
                    .count();

            return count;
        }
        if (deadOrAlive.equals("alive") & type.equals("men")) {
            long count = lines.stream()
                    .collect(Collectors.toList()).stream()
                    .filter(c -> c.get(1).equals("1"))
                    .filter(c -> c.get(5).equals("male"))
                    .filter(c -> c.get(6).length() != 0)
                    .filter(c -> Double.parseDouble(c.get(6)) > 18)
                    .count();

            return count;
        }

        if (deadOrAlive.equals("dead") & type.equals("women")) {
            long count = lines.stream()
                    .collect(Collectors.toList()).stream()
                    .filter(c -> c.get(1).equals("0"))
                    .filter(c -> c.get(5).equals("female"))
                    .filter(c -> c.get(6).length() != 0)
                    .filter(c -> Double.parseDouble(c.get(6)) > 18)
                    .count();

            return count;
        }

        if (deadOrAlive.equals("alive") & type.equals("women")) {
            long count = lines.stream()
                    .collect(Collectors.toList()).stream()
                    .filter(c -> c.get(1).equals("1"))
                    .filter(c -> c.get(5).equals("female"))
                    .filter(c -> c.get(6).length() != 0)
                    .filter(c -> Double.parseDouble(c.get(6)) > 18)
                    .count();

            return count;
        }

        if (deadOrAlive.equals("dead") & type.equals("children")) {
            long count = lines.stream()
                    .collect(Collectors.toList()).stream()
                    .filter(c -> c.get(1).equals("0"))
                    .filter(c -> c.get(6).length() != 0)
                    .filter(c -> Double.parseDouble(c.get(6)) < 18)
                    .count();

            return count;
        }

        if (deadOrAlive.equals("alive") & type.equals("children")) {
            long count = lines.stream()
                    .collect(Collectors.toList()).stream()
                    .filter(c -> c.get(1).equals("1"))
                    .filter(c -> c.get(6).length() != 0)
                    .filter(c -> Double.parseDouble(c.get(6)) < 18)
                    .count();

            return count;
        }

        return 0;
    }


    public static double deadOrAlive(String file, String deadOrAlive) throws FileNotFoundException {
        List<List<String>> lines = csvReader(file);
        lines.remove(0);

        if (deadOrAlive.equals("dead")) {
            long count = lines.stream()
                    .collect(Collectors.toList()).stream()
                    .filter(c -> c.get(1).equals("0"))
                    .count();

            return count;
        }
        if (deadOrAlive.equals("alive")) {
            long count = lines.stream()
                    .collect(Collectors.toList()).stream()
                    .filter(c -> c.get(1).equals("1"))
                    .count();
            return count;
        }

        return 0;
    }


    public static double avarageClassFare(String file, String pClass) throws FileNotFoundException {
        List<List<String>> lines = csvReader(file);
        lines.remove(0);

        long count = lines.stream()
                .collect(Collectors.toList()).stream()
                .filter(c -> c.get(2).equals(pClass)).count();


        double total = lines.stream()
                .collect(Collectors.toList()).stream()
                .filter(c -> c.get(2).equals(pClass))
                .mapToDouble(l -> Double.parseDouble(l.get(10)))
                .reduce(0, (a, b) -> a + b);

        return total / count;
    }


    public static double totalFares(String file) throws FileNotFoundException {
        List<List<String>> lines = csvReader(file);
        lines.remove(0);


        double total = lines.stream()
                .collect(Collectors.toList()).stream()
                .mapToDouble(l -> Double.parseDouble(l.get(10)))
                .reduce(0, (a, b) -> a + b);


        return total;
    }


    public static List<List<String>> csvReader(String fileName) throws FileNotFoundException {
        FileReader csv = new FileReader(fileName);
        String line = null;
        List<List<String>> lines = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(csv)) {

            while ((line = bufferedReader.readLine()) != null) {
                List<String> lineS = Arrays.asList(line.split(","));
                lines.add(lineS);
            }

        } catch (Exception e) {
            e.getStackTrace();
        }

        return lines;


    }


}
