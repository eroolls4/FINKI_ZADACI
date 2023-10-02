package GENERIC_MINMAX;

import java.util.Scanner;
import javax.swing.text.*;
import java.io.*;
import java.io.*;
import java.util.*;
import java.util.stream.*;


class MinMax<T extends Comparable<T>>{
    T min;
    T max;

    int countingMaxs;
    int countingMins;
    int total;

    public MinMax() {
        countingMaxs=0;
        countingMins=0;
        total=0;
    }

    public void update(T element) {
        if (total == 0) { //first time coming here
            min = max = element;
        }
        ++total;


        if (min.compareTo(element) < 0) { //CHECKING FOR MIN !!!
            min = element;
            countingMins = 1;
        } else {
            if (min.compareTo(element) == 0) {
                countingMins++;
            }
        }

        if (max.compareTo(element) > 0) {  //NOW LETS SEE FOR MAX
            max = element;
            countingMaxs = 1;
        } else {
            if (max.compareTo(element) == 0) {
                countingMaxs++;
            }
        }

    }

    T max(){
    return max;
    }

    T min() {
     return min;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        T min = min();
        T max = max();
        sb.append(String.format("%s %s %d\n", min,
                                              max,
                                              total - (countingMaxs + countingMins)));
        return sb.toString();
    }
}


public class MinAndMax {
    public static void main(String[] args) throws ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        MinMax<String> strings = new MinMax<String>();
        for(int i = 0; i < n; ++i) {
            String s = scanner.next();
            strings.update(s);
        }
        System.out.println(strings);
        MinMax<Integer> ints = new MinMax<Integer>();
        for(int i = 0; i < n; ++i) {
            int x = scanner.nextInt();
            ints.update(x);
        }
        System.out.println(ints);
    }
}