package SEP_2023_ENTRY;

import java.util.*;
import java.util.stream.*;

public class just {
    public static Stream<Character> getChars(String word){
        return word.chars().mapToObj(ch -> (char) ch);
    }

    public static void main(String[] args) {


        String line = "A, D, D, A, C, C, D, D, C, A, A, D, A;D, A, C, A, B, D, D, B, A, C, D, B, C";
        String[] p = line.split(";");


        String lastYear = p[0];
        String currYear = p[1];
        String[] lastParts = lastYear.split(",");
        String[] currParts = currYear.split(",");
//
//        char[] last = Arrays.stream(lastParts).map(String::trim).collect(Collectors.joining()).toCharArray();
//        char[] curr = Arrays.stream(currParts).map(String::trim).collect(Collectors.joining()).toCharArray();
//



        List<Character> lastChars;
        List<Character> currChars;

//
//        String e = "eroll";
//        char[] x = e.toCharArray();
//        System.out.println(x);
//

          lastChars=Arrays.stream(lastParts)
                          .map(String::trim)
                          .flatMap(word -> getChars(word))
                          .collect(Collectors.toList());

       currChars=Arrays.stream(currParts)
                       .map(String::trim)
                       .flatMap(word -> getChars(word))
                       .collect(Collectors.toList());


       int tmp=0;
       for (int i=0;i<lastChars.size();i++){
           tmp +=lastChars.get(i) - currChars.get(i);
       }
//
//        int total = 0;
//        for (int i = 0; i < last.length; i++) {
//            total += last[i] - curr[i];
//        }
//        System.out.println(total);
    }
}
