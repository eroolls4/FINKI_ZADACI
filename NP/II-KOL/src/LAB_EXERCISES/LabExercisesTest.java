package LAB_EXERCISES;

import java.util.*;
import java.util.stream.*;

class Student{

    String index;
    List<Integer> points;
    int year;


    final static int MAX_POINTS=10;



    public Student(String index, List<Integer> points) {
        this.index = index;
        this.points = points;
        int year=0;
    }



    public String getIndex() {
        return index;
    }

    public boolean hasFailed() {
        return points.size() < 8;
    }


    public double summaryPoints(){
        return points.stream().mapToDouble(i-> i).sum() / MAX_POINTS;
    }

    public double avgOfSummaryPoints(){
        double summary=summaryPoints();
        return summary / points.size();
    }



    public int getYear(){
        int yearOfEnrollment= 2000 + Integer.parseInt(index.substring(0,2));
        int today=2020;
        return today - yearOfEnrollment;
    }


    @Override
    public String toString() {
        return String.format("%s %s %.2f",index,hasFailed() ? "NO" : "YES",summaryPoints());
    }
}


class LabExercises{

    List<Student> students;

    Map<Integer,List<Double>> summaryPointsByYear;



    public LabExercises() {
        students=new ArrayList<>();
        summaryPointsByYear=new TreeMap<>();
    }

    final static Comparator<Student> byAvgPoints=Comparator.comparingDouble(Student::summaryPoints).thenComparing(Student::getIndex);
    final static Comparator<Student> failed=Comparator.comparing(Student::getIndex).thenComparing(Student::summaryPoints);

    public void addStudent(Student student) {
        students.add(student);

//        students.stream().filter(s-> !s.hasFailed()).forEach(s ->{
//            summaryPointsByYear.putIfAbsent(s.getYear(),new ArrayList<>());
//            summaryPointsByYear.get(s.getYear()).add(s.summaryPoints());
//        });

        if (!student.hasFailed()) {
            int year = student.getYear();
            double summaryPoints = student.summaryPoints();

            summaryPointsByYear.putIfAbsent(year, new ArrayList<>());
            summaryPointsByYear.get(year).add(summaryPoints);
        }

     //   System.out.println(studentsByYear);
    }

    public void printByAveragePoints(boolean ascending, int n) {
        if(ascending){
            students.stream().sorted(byAvgPoints).limit(n).forEach(System.out::println);
        }else{
            students.stream().sorted(byAvgPoints.reversed()).limit(n).forEach(System.out::println);
        }
    }


    public List<Student> failedStudents() {
        return students.stream().filter(Student::hasFailed).sorted(failed).collect(Collectors.toList());
    }

    public Map<Integer, Double> getStatisticsByYear() {
            Map<Integer, Double> res = new TreeMap<>();

            for (Map.Entry<Integer, List<Double>> entry : summaryPointsByYear.entrySet()) {
                int year = entry.getKey();
                double avg = entry.getValue().stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
                res.put(year, avg);
            }
            return res;
    }
}





public class LabExercisesTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LabExercises labExercises = new LabExercises();
        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            String[] parts = input.split("\\s+");
            String index = parts[0];
            List<Integer> points = Arrays.stream(parts).skip(1)
                    .mapToInt(Integer::parseInt)
                    .boxed()
                    .collect(Collectors.toList());

            labExercises.addStudent(new Student(index, points));
        }

        System.out.println("===printByAveragePoints (ascending)===");
        labExercises.printByAveragePoints(true, 100);
        System.out.println("===printByAveragePoints (descending)===");
        labExercises.printByAveragePoints(false, 100);
        System.out.println("===failed students===");
        labExercises.failedStudents().forEach(System.out::println);
        System.out.println("===statistics by year");
        labExercises.getStatisticsByYear().entrySet().stream()
                .map(entry -> String.format("%d : %.2f", entry.getKey(), entry.getValue()))
                .forEach(System.out::println);

    }
}