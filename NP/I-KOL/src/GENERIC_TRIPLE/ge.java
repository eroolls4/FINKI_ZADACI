package GENERIC_TRIPLE;


import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


class Triple<T extends Number & Comparable<T>>{
    T a;
    T b;
    T c;
    List<T> listaOfElements;

    public Triple(T a, T b, T c) {
        this.a = a;
        this.b = b;
        this.c = c;

        listaOfElements=new ArrayList<>(3);
        listaOfElements.add(a);
        listaOfElements.add(b);
        listaOfElements.add(c);
    }

    public double max() {
       return listaOfElements.stream()
                             .mapToDouble(Number::doubleValue)
                             .max()
                             .orElse(0);
    }

    public double avarage() {
      return   listaOfElements.stream()
                              .mapToDouble(Number::doubleValue)
                              .average()
                              .orElse(0);
    }

    public void sort() {
          listaOfElements=listaOfElements.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return String.format("%.2f %.2f %.2f",listaOfElements.get(0),listaOfElements.get(1),listaOfElements.get(2));
    }
}

public class ge {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();
        Triple<Integer> tInt = new Triple<Integer>(a, b, c);
        System.out.printf("%.2f\n", tInt.max());
        System.out.printf("%.2f\n", tInt.avarage());
        tInt.sort();
        System.out.println(tInt);
        float fa = scanner.nextFloat();
        float fb = scanner.nextFloat();
        float fc = scanner.nextFloat();
        Triple<Float> tFloat = new Triple<Float>(fa, fb, fc);
        System.out.printf("%.2f\n", tFloat.max());
        System.out.printf("%.2f\n", tFloat.avarage());
        tFloat.sort();
        System.out.println(tFloat);
        double da = scanner.nextDouble();
        double db = scanner.nextDouble();
        double dc = scanner.nextDouble();
        Triple<Double> tDouble = new Triple<Double>(da, db, dc);
        System.out.printf("%.2f\n", tDouble.max());
        System.out.printf("%.2f\n", tDouble.avarage());
        tDouble.sort();
        System.out.println(tDouble);
    }
}



