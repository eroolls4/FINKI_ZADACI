package STUDENTS_KOLII_2023;



import java.util.*;
import java.util.function.*;
import java.util.stream.*;


class Student{
    String index;
    String name;
    int firstMidtermPoints;
    int secondMidtermPoints;
    int labPoints;


    public Student(String index, String name) {
        this.index = index;
        this.name = name;
        this.firstMidtermPoints = 0;
        this.secondMidtermPoints = 0;
        this.labPoints = 0;
    }

    public double getTotalPoints(){
        return  firstMidtermPoints * 0.45 + secondMidtermPoints * 0.45 + labPoints;
    }

    public boolean studentHasPassed(){
        return getTotalPoints() > 50.00;
    }

    public int getStudentGrade() {
//        int grade;
//        double p = getTotalPoints();
//        if (p > 50.00 && p < 60.00) {
//            grade = 6;
//        } else if (p > 60.00 && p < 70.00) {
//            grade = 7;
//        } else if (p > 70 && p < 80.00) {
//            grade = 8;
//        } else if (p > 80.00 && p < 90.00) {
//            grade = 9;
//        } else if((p > 90 && p < 100) || (p > 100)) {
//           grade=10;
//        }else{
//            grade=5;
//        }
//        return grade;
        int g = (int) getTotalPoints() / 10 + 1;
        if (g > 10) {
            g = 10;
        }
        if (g < 5) {
            g = 5;
        }
        return g;
    }


    @Override
    public String toString() {
        return String.format("ID: %s Name: %s First midterm: %d Second midterm %d Labs: %d Summary points: %.2f Grade: %d",index,name,firstMidtermPoints,secondMidtermPoints,labPoints,getTotalPoints(),getStudentGrade());
    }
}


interface UpdatePoints{
    void updatePoints(Student s,int points) throws Exception;
}


class AdvancedProgrammingCourse{
    List<Student> students;

    Map<String,UpdatePoints> updatePointsByActivity;

    Map<String, BiConsumer<Student,Integer>>  studentPointsBYACIVITY;
    Map<String,Student> studentsByID;



    final static Comparator<Student> cmp=Comparator.comparingDouble(Student::getTotalPoints).reversed();


    void init(){
        studentPointsBYACIVITY.put("midterm1", (s,points) -> {
            if (points > 100) throw new RuntimeException("Invalid points");
            s.firstMidtermPoints = points;
        });

        studentPointsBYACIVITY.put("midterm2", (s,points) -> {
            if (points > 100) throw new RuntimeException("Invalid points");
            s.secondMidtermPoints = points;
        });

        studentPointsBYACIVITY.put("labs", (s,points) -> {
            if (points > 10) throw new RuntimeException("Invalid points");
            s.labPoints = points;
        });
    }

    public AdvancedProgrammingCourse() {
        students=new ArrayList<>();
        studentsByID=new HashMap<>();
        studentPointsBYACIVITY=new HashMap<>();



         init();

        updatePointsByActivity.put("midterm1", ((s, points) -> {
            if(points > 100) throw new Exception();
            s.firstMidtermPoints=points;
        }));

           updatePointsByActivity.put("midterm2", ((s, points) -> {
            if(points > 100) throw new Exception();
            s.secondMidtermPoints=points;
        }));

           updatePointsByActivity.put("labs", ((s, points) -> {
            if(points > 10) throw new Exception();
            s.labPoints=points;
        }));
    }



    public void addStudent (Student s){
        students.add(s);
        studentsByID.put(s.index,s);
    }

    public void updateStudent (String idNumber, String activity, int points){
//        try {
//            updatePointsByActivity.get(activity).updatePoints(studentsByID.get(idNumber),points);
//
//        } catch (Exception e) {
//            //
//        }
        try {
            Student student = studentsByID.get(idNumber);
            if (student == null) {
                return;
            }
            BiConsumer<Student, Integer> updateFunction = studentPointsBYACIVITY.get(activity);
            if (updateFunction != null) {
               updateFunction.accept(student,points);
            }
        } catch (Exception e) {
            //
        }

    }

    public List<Student> getFirstNStudents (int n){
        return studentsByID.values()
                           .stream()
                           .filter(Student::studentHasPassed)
                           .sorted(cmp)
                           .limit(n)
                           .collect(Collectors.toList());
    }


    public Map<Integer,Integer> getGradeDistribution(){
        Map<Integer,Integer> gradeDistribution=studentsByID.values().stream().collect(Collectors.toMap(Student::getStudentGrade,g -> 1,Integer::sum,TreeMap::new));
        for(int i=5;i<=10;i++){
            gradeDistribution.computeIfAbsent(i,k -> 0);
        }
        return gradeDistribution;
    }

    public void printStatistics(){
        DoubleSummaryStatistics ds=studentsByID.values().stream()
                                                        .filter(Student::studentHasPassed)
                                                        .mapToDouble(Student::getTotalPoints)
                                                        .summaryStatistics();


        System.out.printf("Count: %d Min: %.2f Average: %.2f Max: %.2f",ds.getCount(),ds.getMin(),ds.getAverage(),ds.getMax());
    }

}


public class CourseTest {

    public static void printStudents(List<Student> students) {
        students.forEach(System.out::println);
    }

    public static void printMap(Map<Integer, Integer> map) {
        map.forEach((k, v) -> System.out.printf("%d -> %d%n", k, v));
    }

    public static void main(String[] args) {
        AdvancedProgrammingCourse advancedProgrammingCourse = new AdvancedProgrammingCourse();

        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split("\\s+");

            String command = parts[0];

            if (command.equals("addStudent")) {
                String id = parts[1];
                String name = parts[2];
                advancedProgrammingCourse.addStudent(new Student(id, name));
            } else if (command.equals("updateStudent")) {
                String idNumber = parts[1];
                String activity = parts[2];
                int points = Integer.parseInt(parts[3]);
                advancedProgrammingCourse.updateStudent(idNumber, activity, points);
            } else if (command.equals("getFirstNStudents")) {
                int n = Integer.parseInt(parts[1]);
                printStudents(advancedProgrammingCourse.getFirstNStudents(n));
            } else if (command.equals("getGradeDistribution")) {
                printMap(advancedProgrammingCourse.getGradeDistribution());
            } else {
                advancedProgrammingCourse.printStatistics();
            }
        }
    }
}
