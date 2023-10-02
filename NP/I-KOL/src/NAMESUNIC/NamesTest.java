package NAMESUNIC;

import java.util.*;
import java.util.stream.*;

class Names{

    Map<String,Integer> namesAndFrequency;


    public Names() {
        namesAndFrequency=new HashMap<>();
    }

    public void addName(String name) {
        namesAndFrequency.put(name,namesAndFrequency.getOrDefault(name,0)+1);
    }

    public void printN(int n) {

        List<Map.Entry<String, Integer>> res = namesAndFrequency.entrySet()
                                                                .stream()
                                                                .filter(entry -> entry.getValue() >= n)
                                                                .sorted(Map.Entry.comparingByKey())
                                                                .collect(Collectors.toList());


        for(Map.Entry<String, Integer> curr : res){
            long   getDistinct= curr.getKey().toLowerCase()
                                     .chars()
                                     .mapToObj(c -> (char) c)
                                     .distinct()
                                     .count();

            System.out.printf("%s (%d) %d",curr.getKey(),curr.getValue(),getDistinct);
        }
    }

    public String findName(int len, int index) {

        List<String> filteredNames = namesAndFrequency.entrySet()
                                            .stream()
                                            .filter(entry -> entry.getKey().length() < len)
                                            .sorted(Map.Entry.comparingByKey())
                                            .map(entry -> entry.getKey())
                                            .collect(Collectors.toList());

        int  toSearch=index % filteredNames.size();
        return filteredNames.get(toSearch);
    }
}



public class NamesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        Names names = new Names();
        for (int i = 0; i < n; ++i) {
            String name = scanner.nextLine();
            names.addName(name);
        }
        n = scanner.nextInt();
        System.out.printf("===== PRINT NAMES APPEARING AT LEAST %d TIMES =====\n", n);
        names.printN(n);
        System.out.println("===== FIND NAME =====");
//        int len = scanner.nextInt();
//        int index = scanner.nextInt();
//        System.out.println(names.findName(len, index));
        scanner.close();

    }
}

