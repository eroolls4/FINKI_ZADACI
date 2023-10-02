package JUNI_ENTRY_TASK;


import java.io.*;
import java.util.*;
import java.util.stream.*;


class InvalidSize extends Exception{
    public InvalidSize(String id){
        super(String.format("STUDENT WITH  id : %s doesnt have same size",id));
    }
}

class Student{
    String id;
    List<Character> correctAnsw;
    List<Character> answered;


    public Student(String id, List<Character> correctAnsw, List<Character> answered) {
        this.id = id;
        this.correctAnsw = correctAnsw;
        this.answered = answered;
    }

    public double getTotalPoints(){
        int correct=0;
        int wrong=0;
        for(int i=0;i<correctAnsw.size();i++){
            if(correctAnsw.get(i) == answered.get(i)){
                correct++;
            }else{
                wrong++;
            }
        }
        return (correct) - (wrong * 0.25);
    }

    public String getId() {
        return id;
    }
}



class StudentFactory{
    public  static Student createStudent(String line) throws InvalidSize {
        String [] parts=line.split(";");
        List<Character> corr=new ArrayList<>();
        List<Character> ans=new ArrayList<>();
        String id=parts[0];


        String c1 = parts[1];
        String a1 = parts[2];

        //A, B, C;A, C, C

        c1.chars().filter(asci -> (char) asci !=',')
                  .filter(asci -> !Character.isWhitespace(asci))
                  .mapToObj(ascci -> (char) ascci)
                  .forEach(ch -> corr.add((ch)));

        a1.chars().filter(asci -> (char) asci !=',')
                  .filter(asci -> !Character.isWhitespace(asci))
                  .mapToObj(ascci -> (char) ascci)
                  .forEach(ch -> ans.add((ch)));

        if(corr.size() != ans.size()){
         throw new InvalidSize(id);
        }
        return new Student(id,corr,ans);
    }
}

class QuizProcessor{

    public static Map<String, Double> processAnswers(InputStream in) {


        return   new BufferedReader(new InputStreamReader(in)).lines()
                                                 .map(line -> {
                                                     try {
                                                         return StudentFactory.createStudent(line);
                                                     } catch (InvalidSize e) {
                                                       //  System.out.println(e.getMessage());
                                                         System.out.println("A quiz must have same number of correct and selected answers");
                                                         return null;
                                                     }
                                                 })
                                                .filter(Objects::nonNull)
                                                .collect(Collectors.groupingBy(Student::getId,TreeMap::new, Collectors.summingDouble(Student::getTotalPoints)));

    }
}

public class QuizProcessorTest {
    public static void main(String[] args) {
        QuizProcessor.processAnswers(System.in).forEach((k, v) -> System.out.printf("%s -> %.2f%n", k, v));
    }
}