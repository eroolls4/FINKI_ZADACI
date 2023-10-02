package MAPOPS_JANUARY2023;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;



interface MergeStrategy<T>{
     T merge(T left,T right);
}



class MapOps{

    public static <K extends Comparable<K>, V, Z> Map<K,V> merge(Map<K, V> mapLeft, Map<K, V> mapRight, MergeStrategy<V> mergeStrategy) {
        Map<K,V> res=new TreeMap<>(mapLeft);
        mapRight.forEach((k, v) ->{
            res.merge(k,v, (left, right) ->  mergeStrategy.merge(left, right));
        });
        return res;
    }
}



public class GenericMapTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int testCase = Integer.parseInt(sc.nextLine());

        if (testCase == 1) { //Mergeable integers
            Map<Integer, Integer> mapLeft = new HashMap<>();
            Map<Integer, Integer> mapRight = new HashMap<>();
            readIntMap(sc, mapLeft);
            readIntMap(sc, mapRight);

            //TODO Create an object of type MergeStrategy that will enable merging of two Integer objects into a new Integer object which is their sum
            MergeStrategy<Integer> mergeStrategy = (a, b) -> Integer.sum(a, b);

            printMap(MapOps.merge(mapLeft, mapRight, mergeStrategy));
        } else if (testCase == 2) { // Mergeable strings
            Map<String, String> mapLeft = new HashMap<>();
            Map<String, String> mapRight = new HashMap<>();
            readStrMap(sc, mapLeft);
            readStrMap(sc, mapRight);

            //TODO Create an object of type MergeStrategy that will enable merging of two String objects into a new String object which is their concatenation
            MergeStrategy<String> mergeStrategy = (s1,s2) -> s1+s2;

            printMap(MapOps.merge(mapLeft, mapRight, mergeStrategy));
        } else if (testCase == 3) {
            Map<Integer, Integer> mapLeft = new HashMap<>();
            Map<Integer, Integer> mapRight = new HashMap<>();
            readIntMap(sc, mapLeft);
            readIntMap(sc, mapRight);

            //TODO Create an object of type MergeStrategy that will enable merging of two Integer objects into a new Integer object which will be the max of the two objects
            MergeStrategy<Integer> mergeStrategy = (a,b) -> Integer.max(a,b);

            printMap(MapOps.merge(mapLeft, mapRight, mergeStrategy));
        } else if (testCase == 4) {
            Map<String, String> mapLeft = new HashMap<>();
            Map<String, String> mapRight = new HashMap<>();
            readStrMap(sc, mapLeft);
            readStrMap(sc, mapRight);

            //TODO Create an object of type MergeStrategy that will enable merging of two String objects into a new String object which will mask the occurrences of the second string in the first string

            MergeStrategy<String> mergeStrategy = (s1,s2) -> s1.replace(s2,IntStream.range(0,s2.length()).mapToObj(c -> "*").collect(Collectors.joining()));
            printMap(MapOps.merge(mapLeft, mapRight, mergeStrategy));
        }
    }

    private static void readIntMap(Scanner sc, Map<Integer, Integer> map) {
        int n = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < n; i++) {
            String input = sc.nextLine();
            String[] parts = input.split("\\s+");
            int k = Integer.parseInt(parts[0]);
            int v = Integer.parseInt(parts[1]);
            map.put(k, v);
        }
    }

    private static void readStrMap(Scanner sc, Map<String, String> map) {
        int n = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < n; i++) {
            String input = sc.nextLine();
            String[] parts = input.split("\\s+");
            map.put(parts[0], parts[1]);
        }
    }

    private static void printMap(Map<?, ?> map) {
        map.forEach((k, v) -> System.out.printf("%s -> %s%n", k.toString(), v.toString()));
    }
}
