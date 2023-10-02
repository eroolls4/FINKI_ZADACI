package STUDENT_RECORDS;

import java.io.*;
import java.util.*;
import java.util.stream.*;



class Print{
    public static String printStars(int level){

        return IntStream.range(0,level)
                        .mapToObj(i-> "*")
                        .collect(Collectors.joining());
    }
}


class Student{
    String code;
    String smer;
    List<Integer> grades;


    public Student(String code, String smer, List<Integer> grades) {
        this.code = code;
        this.smer = smer;
        this.grades = grades;
    }


    public String getCode() {
        return code;
    }

    public double getAverage(){
        return grades.stream()
                .mapToDouble(i->i)
                .average()
                .orElse(5.0);
    }

    public int getNrOfGrades(int grade){
        return (int) grades.stream().filter(i-> i.equals(grade)).count();
    }

    public String writeTable(){
        return String.format("%s %.2f",code,getAverage());
    }
}


class StudentFactory{
    public static Student createStudent(String line){
        String [] parts=line.split("\\s+");

        //ioqmx7 MT 10 8 10 8 10 7 6 9 9 9 6 8 6 6 9 9 8
        String code=parts[0];
        String smer=parts[1];
        List<Integer> grades=new ArrayList<>();

        Arrays.stream(parts)
                .skip(2)
                .mapToInt(Integer::parseInt)
                .forEach(grades::add);

        return new Student(code,smer,grades);
    }
}


class StudentRecords{


    List<Student> students;

    Map<String,List<Student>> studentsBySmer;

    Map<String,Map<Integer,Long>> gradesFreqBySmer;



    final static Comparator<Student> cmp=Comparator.comparingDouble(Student::getAverage).reversed().thenComparing(Student::getCode);


    public StudentRecords() {
        students =new ArrayList<>();
        studentsBySmer=new TreeMap<>();
        gradesFreqBySmer =new TreeMap<>();
    }

    public int readRecords(InputStream in) {
      students = new BufferedReader(new InputStreamReader(in))
                .lines()
                .map(StudentFactory::createStudent)
                .collect(Collectors.toList());

        students.forEach(student -> {
            studentsBySmer.putIfAbsent(student.smer, new ArrayList<>());
            studentsBySmer.get(student.smer).add(student);

            gradesFreqBySmer.putIfAbsent(student.smer, new TreeMap<>());

            Map<Integer, Long> gradeFreq = student.grades.stream()
                    .collect(Collectors.groupingBy(
                            grade -> grade,
                            Collectors.summingLong(grade -> 1L)
                    ));

            gradeFreq.forEach((grade, count) ->
                    gradesFreqBySmer.get(student.smer).merge(grade, count, (a, b) -> Long.sum(a, b))
            );
        });
    //    System.out.println(gradesFreqBySmer.toString());
        return students.size();
    }

    public void writeTable(OutputStream out) {
        PrintWriter pw = new PrintWriter(out, true);

        studentsBySmer.entrySet().stream().forEach(entry -> {
            pw.println(entry.getKey());
            studentsBySmer.get(entry.getKey()).stream().sorted(cmp).forEach(student -> {
               pw.println(student.writeTable());
            });
        });
    }

    public void writeDistribution(OutputStream out) {
        PrintWriter pw = new PrintWriter(out, true);

        Comparator<Map.Entry<String, Map<Integer, Long>>> customComparator = (entry1, entry2) -> {
//            int tens1 = entry1.getValue().values().stream()
//                    .mapToInt(value -> (int) Math.ceil((double) value / 10))
//                    .sum();
//            int tens2 = entry2.getValue().values().stream()
//                    .mapToInt(value -> (int) Math.ceil((double) value / 10))
//                    .sum();
//            return Integer.compare(tens1, tens2);
            long grade10Count1 = entry1.getValue().getOrDefault(10, 0L);
            long grade10Count2 = entry2.getValue().getOrDefault(10, 0L);
            return Long.compare(grade10Count1, grade10Count2);
        };

        gradesFreqBySmer.entrySet().stream().sorted(customComparator.reversed()).forEach(entry -> {
            String key= entry.getKey();
             pw.println(key);
             gradesFreqBySmer.get(key).entrySet().stream().forEach(innerEntry->{
                 int grade= innerEntry.getKey();
                 long freq=innerEntry.getValue();
                 int level=(int) Math.ceil((double) freq / 10);
                 pw.printf("%2d | %s(%d)\n",grade,Print.printStars(level),freq);
             });
        });
    }
}
public class StudentRecordsTest {
    public static void main(String[] args) {
        System.out.println("=== READING RECORDS ===");
        StudentRecords studentRecords = new StudentRecords();
        int total = studentRecords.readRecords(System.in);
        System.out.printf("Total records: %d\n", total);
        System.out.println("=== WRITING TABLE ===");
        studentRecords.writeTable(System.out);
        System.out.println("=== WRITING DISTRIBUTION ===");
        studentRecords.writeDistribution(System.out);
    }
}

